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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.ita.bditac.ws.client.AlertaClient;
import br.ita.bditac.ws.model.Alerta;


public class ConsultaEventoActivity extends ListActivity {

    private Context context;

    private Location currentLocation;

    private String alertasUrl;

    private double radiusKms;

    private LocationListener locationListener;

    private class AlertaArrayAdapter extends ArrayAdapter<Alerta> {

        private final Context context;

        private final Alerta[] alertas;

        public AlertaArrayAdapter(Context context, int resourceId, Alerta[] alertas) {
            super(context, R.layout.list_alertas);

            this.context = context;
            this.alertas = alertas;
        }

        @Override
        public int getCount() {

            if(alertas == null) {
                return 0;
            }
            else {
                return alertas.length;
            }

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_alertas, parent, false);

            TextView descricaoResumida = (TextView) rowView.findViewById(R.id.descricaoResumida);
            descricaoResumida.setText(alertas[position].getDescricaoResumida());

            TextView descricaoCompleta = (TextView) rowView.findViewById(R.id.descricaoCompleta);
            descricaoCompleta.setText(alertas[position].getDescricaoCompleta());

            return rowView;
        }

    }

    private class ConsultarAlertasTask extends AsyncTask<Void, Void, Alerta[]> {

        private Exception exception;

        private ListActivity activity;

        protected ConsultarAlertasTask(ListActivity activity) {

            super();

            this.activity = activity;

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

            AlertaArrayAdapter adapter = new AlertaArrayAdapter(activity, R.layout.list_alertas, alertas);
            activity.setListAdapter(adapter);
            adapter.notifyDataSetChanged();

        }
    }

    private class AlertaLocationListener implements LocationListener {

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

                Log.i(this.getClass().getSimpleName(), "Location service registered.");

                locationListener = new AlertaLocationListener();
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
