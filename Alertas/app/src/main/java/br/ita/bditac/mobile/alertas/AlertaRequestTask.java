package br.ita.bditac.mobile.alertas;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.List;

import br.ita.bditac.ws.client.AlertaClient;
import br.ita.bditac.ws.model.Alerta;

public class AlertaRequestTask extends AsyncTask<Void, Void, List<Alerta>> {

    private static final String DEFAULT_URL = "http://localhost/alerta";

    private static final float DEFAULT_RADIUS_KMS = 50f;

    private Context context;

    public AlertaRequestTask(Context context) {
        this.context = context;
    }

    @Override
    protected List<Alerta> doInBackground(Void... params) {
        try {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            String bestProvider = locationManager.getBestProvider(new Criteria(), true);

            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            };

            if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(bestProvider, 0, 0, locationListener);
                Location location = locationManager.getLastKnownLocation(bestProvider);

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(null);
                String alertasUrl = preferences.getString("alertas.service.url", DEFAULT_URL);
                Double radiusKms = new Double(preferences.getFloat("alertas.service.radiusKms", DEFAULT_RADIUS_KMS));

                AlertaClient alertaClient = new AlertaClient(alertasUrl);

                List<Alerta> alertas = alertaClient.getAlertaByRegiao(location.getLatitude(), location.getLongitude(), radiusKms);

                return alertas;
            }
        }
        catch(Exception ex) {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
        }

        Log.w(this.getClass().getName(), context.getString(R.string.msg_location_denied));

        return null;
    }

    @Override
    protected void onPostExecute(List<Alerta> alertas) {
        for(Alerta alerta : alertas) {
            Intent getAlertaIntent = new Intent(context, NotificationReceiverActivity.class);
            PendingIntent pGetAlertaIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), getAlertaIntent, 0);

            Notification notification = new Notification.Builder(context)
                    .setContentTitle(context.getText(R.string.msg_alert_received))
                    .setContentText(alerta.getDescricaoResumida())
                    .setStyle(new Notification.BigTextStyle().bigText(alerta.getDescricaoCompleta()))
                    .setSmallIcon(R.drawable.ic_action_alert)
                    .setContentIntent(pGetAlertaIntent)
                    .addAction(R.drawable.ic_stat_alert, context.getText(R.string.open), pGetAlertaIntent)
                    .build();
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

            notification.flags |= Notification.FLAG_AUTO_CANCEL;

            notificationManager.notify(NotificationIdGenerator.getNewID(), notification);
        }
    }

}
