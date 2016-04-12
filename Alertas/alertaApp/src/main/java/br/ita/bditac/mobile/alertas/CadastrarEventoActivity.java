package br.ita.bditac.mobile.alertas;


import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import br.ita.bditac.ws.client.EventoClient;
import br.ita.bditac.ws.model.Evento;
import br.ita.bditac.ws.model.Usuario;


public class CadastrarEventoActivity extends AppCompatActivity {

    private Context context;

    private Location currentLocation;

    private String alertasUrl;

    private EditText inputDescricao;

    private Spinner inputCategoria;

    private LocationListener locationListener;

    private class SalvarEventoTask extends AsyncTask<Evento, Void, Void> {

        private Exception exception;

        @Override
        protected Void doInBackground(Evento... eventos) {

            try {
                EventoClient eventoClient = new EventoClient(alertasUrl);

                for(Evento evento: eventos) {
                    eventoClient.addEvento(evento);
                }
            }
            catch(Exception ex) {
                CharSequence mensagem = getText(R.string.msg_alerts_service_unaivalable);
                Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();

                Log.e(this.getClass().getSimpleName(), ex.getMessage(), ex);
            }
            finally {
                return null;
            }

        }

        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);

            finish();
        }

    }

    private class EventoLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {

            Log.i(this.getClass().getSimpleName(), "Location service location change detected.");

            Context context = getApplicationContext();

            SharedPreferences preferences = null;

            if(!Debug.isDebuggerConnected()) {
                preferences = PreferenceManager.getDefaultSharedPreferences(context);
            }

            int locationPollingInterval =
                    Debug.isDebuggerConnected() ?
                            Constants.DEBUG_LOCATION_POLLING_INTERVAL :
                            preferences.getInt("alerts.service.locationPollingInterval", Constants.DEFAULT_LOCATION_POLLING_INTERVAL);

            if(currentLocation == null || !isBetterLocation(location, currentLocation, locationPollingInterval)) {
                currentLocation = location;
            }

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

            Log.w(this.getClass().getSimpleName(), "Location services status " + s + " change detected.");

        }

        @Override
        public void onProviderEnabled(String s) {

            Log.w(this.getClass().getSimpleName(), "Location services provider " + s + " enabled.");

        }

        @Override
        public void onProviderDisabled(String s) {

            Log.w(this.getClass().getSimpleName(), "Location services provider " + s + " disabled.");

        }

    }

    private boolean isSameProvider(String currentProvider, String previousProvider) {

        if (currentProvider == null) {
            return previousProvider == null;
        }

        return currentProvider.equals(previousProvider);

    }

    private boolean isBetterLocation(Location location, Location currentBestLocation, int locationPollingInterval) {

        if (currentBestLocation == null) {
            return true;
        }

        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > locationPollingInterval;
        boolean isSignificantlyOlder = timeDelta < -locationPollingInterval;
        boolean isNewer = timeDelta > 0;

        if (isSignificantlyNewer) {
            return true;
        }
        else if (isSignificantlyOlder) {
            return false;
        }

        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        boolean isFromSameProvider = isSameProvider(location.getProvider(), currentBestLocation.getProvider());

        if (isMoreAccurate) {
            return true;
        }
        else if (isNewer && !isLessAccurate) {
            return true;
        }
        else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
                return true;
            }

        return false;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_evento);

        inputDescricao = (EditText) findViewById(R.id.mensagem);

        SharedPreferences preferences = null;

        context = getApplicationContext();

        if (!Debug.isDebuggerConnected()) {
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
        }

        alertasUrl = Debug.isDebuggerConnected() ? Constants.DEBUG_URL : preferences.getString("alerts.service.url", Constants.DEFAULT_URL);

        try {
            Context context = getApplicationContext();

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

                Log.i(this.getClass().getSimpleName(), "Location service registered.");

                locationListener = new EventoLocationListener();
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }
        catch(Exception ex) {
            CharSequence mensagem = getText(R.string.msg_alerts_service_unaivalable);
            Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();

            Log.e(this.getClass().getSimpleName(), ex.getMessage(), ex);
        }

        CarregarCategoria();

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        try {
            Context context = getApplicationContext();

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

                Log.i(this.getClass().getSimpleName(), "Location service deregistered.");

                locationManager.removeUpdates(locationListener);
            }
        }
        catch(Exception ex) {
            Log.e(this.getClass().getSimpleName(), ex.getMessage(), ex);
        }

    }

    public void Limpar_Click(View view) {

        inputDescricao.setText("");
        inputCategoria.setSelection(0);

    }

    public void Salvar_Click(View view) {

        Evento evento = new Evento();

        evento.setDescricao(inputDescricao.getText().toString());
        evento.setCategoria(inputCategoria.getSelectedItemPosition());
        evento.setNome(Usuario.getNome());
        evento.setEmail(Usuario.getEmail());
        evento.setTelefone(Usuario.getTelefone());
        if(currentLocation == null) {
            CharSequence mensagem = getText(R.string.msg_location_service_unaivalable);
            Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();
        }
        else {
            evento.setLatitude(currentLocation.getLatitude());
            evento.setLongitude(currentLocation.getLongitude());

            new SalvarEventoTask().execute(evento);
        }

    }


    private void CarregarCategoria(){

        // TODO: Criar essa lista em resources e alterar quando a API estiver pronta para cadastrar esse dominio dinamicamente

        inputCategoria = (Spinner) findViewById(R.id.categoria);

        if (inputCategoria == null)
            return ;

        ArrayList<String> categoriaArrayList = new ArrayList<>();
        categoriaArrayList.add("Alagamento");
        categoriaArrayList.add("Incêndio");
        categoriaArrayList.add("Acidente Veicular");
        categoriaArrayList.add("Acidente Aéreo");
        categoriaArrayList.add("Assassinato");
        categoriaArrayList.add("Roubo");
        categoriaArrayList.add("Terremoto");
        categoriaArrayList.add("Desmoronamento");
        categoriaArrayList.add("Tiroteio");

        ArrayAdapter<String> adapter = new ArrayAdapter <String>(this, android.R.layout.simple_dropdown_item_1line, categoriaArrayList);
        inputCategoria.setAdapter(adapter);

    }

}
