package br.ita.bditac.mobile.alertas;

import android.Manifest;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Debug;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.List;

import br.ita.bditac.ws.client.AlertaClient;
import br.ita.bditac.ws.model.Alerta;

public class AlertaPollingService extends IntentService {

    // TODO Definir a localização padrão do serviço BD-ITAC em produção
    private static final String DEFAULT_URL = "http://localhost:8080/alerta";

    private static final String DEBUG_URL = "http://localhost:8080/alerta";

    private static final String DEFAULT_RADIUS_KMS = "10";

    private static final double DEBUG_RADIUS_KMS = 1;

    private static final int DEFAULT_LOCATION_POLLING_INTERVAL = 120000;

    private static final int DEBUG_LOCATION_POLLING_INTERVAL = 15000;

    private String alertasUrl;

    private Double radiusKms;

    private int locationPollingInterval;

    private Location currentLocation;

    public AlertaPollingService() {

        super("AlertaPollingService");

        Debug.waitForDebugger();

        SharedPreferences preferences = null;

        if(!Debug.isDebuggerConnected()) {
            preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        }

        alertasUrl =
                Debug.isDebuggerConnected() ?
                        DEBUG_URL :
                        preferences.getString("alerts.service.url", DEFAULT_URL);

        radiusKms =
                Debug.isDebuggerConnected() ?
                        DEBUG_RADIUS_KMS :
                        new Double(preferences.getString("alerts.service.radiusKms", DEFAULT_RADIUS_KMS)).doubleValue();

        locationPollingInterval =
                Debug.isDebuggerConnected() ?
                        DEBUG_LOCATION_POLLING_INTERVAL :
                        preferences.getInt("alerts.servce.locationPollingInterval",DEFAULT_LOCATION_POLLING_INTERVAL);

        currentLocation = null;

        Log.i(this.getClass().getSimpleName(), "Receiver initialized.");

    }

    private boolean isSameProvider(String currentProvider, String previousProvider) {

        if (currentProvider == null) {
            return previousProvider == null;
        }

        return currentProvider.equals(previousProvider);

    }

    protected boolean isBetterLocation(Location location, Location currentBestLocation) {

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

    private class AlertaLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {

            Log.i(this.getClass().getSimpleName(), "Location service location change detected.");

            if(currentLocation == null || !isBetterLocation(location, currentLocation)) {
                currentLocation = location;
            }

            AlertaClient alertaClient = new AlertaClient(alertasUrl);

            List<Alerta> alertas = alertaClient.getAlertaByRegiao(location.getLatitude(), location.getLongitude(), radiusKms);

            if(alertas == null) {
                Log.i(this.getClass().getSimpleName(), "Alerts not found for now.");
            }
            else {
                Context context = getApplicationContext();

                for (Alerta alerta : alertas) {
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

                    Log.w(this.getClass().getSimpleName(), "Notification posted.");
                }
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

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.i(this.getClass().getSimpleName(), "Alert poll request triggered.");

        try {
            Context context = getApplicationContext();

            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//            Criteria criteria = new Criteria();
//            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
//            criteria.setAltitudeRequired(false);
//            criteria.setBearingRequired(false);
//            criteria.setCostAllowed(true);
//            String bestProvider = locationManager.getBestProvider(criteria, true);
//            if(bestProvider == null) {
//                Log.e(this.getClass().getSimpleName(), "Location service providers not found.");
//            }
//            else {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.i(this.getClass().getSimpleName(), "Location service registered.");

                LocationListener locationListener = new AlertaLocationListener();
//                    locationManager.requestLocationUpdates(bestProvider, 0, 0, locationListener);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }

            Log.i(this.getClass().getSimpleName(), "Background task finished.");
//            }
        }
        catch(Exception ex) {
            Log.e(this.getClass().getSimpleName(), ex.getMessage(), ex);
        }

        Log.w(this.getClass().getSimpleName(), "Location services service unavailable.");

    }

}
