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

import br.ita.bditac.ws.client.CriseClient;
import br.ita.bditac.ws.model.Crise;
import br.ita.bditac.ws.model.Usuario;


public class CadastrarCriseActivity extends AppCompatActivity {

    private Context context;

    private Location currentLocation;

    private String alertasUrl;

    private EditText inputDescricao;

    private Spinner inputCategoria;

    private LocationListener locationListener;

    protected boolean saving = false;

    private class SalvarCriseTask extends AsyncTask<Crise, Void, Void> {

        private boolean saved = false;

        @Override
        protected Void doInBackground(Crise... crises) {

            try {
                CriseClient criseClient = new CriseClient(alertasUrl);

                for(Crise crise : crises) {
                    criseClient.addCrise(crise);
                }

                saved = true;
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

            try {
                Context context = getApplicationContext();

                if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

                    Log.i(this.getClass().getSimpleName(), "Location service deregistered.");

                    locationManager.removeUpdates(locationListener);
                }

                if(saved) {
                    Toast.makeText(context, getText(R.string.msg_crisis_saved), Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(context, getText(R.string.msg_alerts_service_unaivalable), Toast.LENGTH_LONG).show();
                }

                saved = false;
                saving = false;
            }
            catch(Exception ex) {
                Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();

                Log.e(this.getClass().getSimpleName(), ex.getMessage(), ex);
            }

            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_crise);

        inputDescricao = (EditText) findViewById(R.id.mensagem);

        saving = false;

        CarregarCategoria();

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }

    public void Limpar_Click(View view) {

        inputDescricao.setText("");
        inputCategoria.setSelection(0);

    }

    public void Salvar_Click(View view) {

        if(saving) {
            Toast.makeText(context, getText(R.string.msg_crisis_being_saved), Toast.LENGTH_LONG).show();
        }
        else {
            SharedPreferences preferences=null;

            context=getApplicationContext();

            if(!Debug.isDebuggerConnected()) {
                preferences=PreferenceManager.getDefaultSharedPreferences(context);
            }

            alertasUrl=Debug.isDebuggerConnected() ? Constants.DEBUG_URL : preferences.getString("alerts.service.url", Constants.DEFAULT_URL);

            try {
                Context context=getApplicationContext();

                if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    LocationManager locationManager=(LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

                    Log.i(this.getClass().getSimpleName(), "Location service registered.");

                    locationListener=new AlertaLocationListener(context);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                    currentLocation=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
            }
            catch(Exception ex) {
                CharSequence mensagem=getText(R.string.msg_alerts_service_unaivalable);
                Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();

                Log.e(this.getClass().getSimpleName(), ex.getMessage(), ex);
            }

            if(currentLocation == null) {
                Toast.makeText(context, getText(R.string.msg_location_service_unaivalable), Toast.LENGTH_LONG).show();

                Log.i(this.getClass().getSimpleName(), getText(R.string.msg_location_service_unaivalable).toString());
            }
            else {
            Crise crise=new Crise(
                    inputDescricao.getText().toString(),
                    inputCategoria.getSelectedItemPosition(),
                    Usuario.getNome(),
                    Usuario.getEmail(),
                    Usuario.getTelefone(),
                    currentLocation.getLatitude(),
                    currentLocation.getLongitude(),
                    // TODO Adicionar fotografia
                    "");

                new SalvarCriseTask().execute(crise);

                Toast.makeText(context, getText(R.string.msg_crisis_being_saved), Toast.LENGTH_LONG).show();

                saving = true;
            }
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
