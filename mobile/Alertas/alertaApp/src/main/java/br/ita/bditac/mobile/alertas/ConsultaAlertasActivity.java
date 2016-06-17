package br.ita.bditac.mobile.alertas;


import android.Manifest;
import android.app.ListActivity;
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
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import br.ita.bditac.ws.client.AlertaClient;
import br.ita.bditac.ws.model.Alerta;


public class ConsultaAlertasActivity extends ListActivity {

    private Context context;

    private Location currentLocation;

    private String alertasUrl;

    private double radiusKms;

    private LocationListener locationListener;

    private class ConsultarAlertasTask extends AsyncTask<Void, Void, Alerta[]> {

        protected ConsultarAlertasTask(ListActivity activity) {

            super();

        }

        protected Alerta[] doInBackground(Void... params) {

            Alerta[] alertasArray = null;

            try {
                if(currentLocation == null) {
                    CharSequence mensagem = getText(R.string.msg_location_service_unaivalable);
                    Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();
                }
                else {
                    AlertaClient alertaClient = new AlertaClient(alertasUrl);

                    List<Alerta> alertas = alertaClient.getAlertaByRegiao(currentLocation.getLatitude(), currentLocation.getLongitude(), radiusKms);
                    alertasArray = alertas.toArray(new Alerta[alertas.size()]);
                }
            }
            catch(Exception ex) {
                CharSequence mensagem = getText(R.string.msg_alerts_service_unaivalable);
                Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();

                Log.e(this.getClass().getSimpleName(), ex.getMessage(), ex);
            }
            finally {
                return alertasArray;
            }

        }

        @Override
        protected void onPostExecute(Alerta[] alertas) {

            super.onPostExecute(alertas);

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

                Log.i(this.getClass().getSimpleName(), "Alerta location service deregistered.");

                locationManager.removeUpdates(locationListener);
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = null;

        this.context = getApplicationContext();

        if (!Debug.isDebuggerConnected()) {
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
        }

        alertasUrl = Debug.isDebuggerConnected() ? Constants.DEBUG_URL : preferences.getString("alerts.service.url", Constants.DEFAULT_URL);

        radiusKms =
                Debug.isDebuggerConnected() ?
                        Constants.DEBUG_RADIUS_KMS :
                        new Double(preferences.getString("alerts.service.radiusKms", Constants.DEFAULT_RADIUS_KMS)).doubleValue();

        try {
            Context context = getApplicationContext();

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

                Log.i(this.getClass().getSimpleName(), "Alerta location service registered.");

                locationListener = new AlertaLocationListener(context);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }
        catch(Exception ex) {
            CharSequence mensagem = getText(R.string.msg_alerts_service_unaivalable);
            Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();

            Log.e(this.getClass().getSimpleName(), ex.getMessage(), ex);
        }

        new ConsultarAlertasTask(this).execute();

    }

}
