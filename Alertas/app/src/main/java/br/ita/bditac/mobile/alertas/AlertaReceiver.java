package br.ita.bditac.mobile.alertas;

import android.Manifest;
import android.app.AlarmManager;
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
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.util.List;

import br.ita.bditac.ws.client.AlertaClient;
import br.ita.bditac.ws.model.Alerta;

public class AlertaReceiver extends WakefulBroadcastReceiver {

    private static final int DEFAULT_TIME_IN_SECONDS = 600;

    private static final int DEBUG_TIME_IN_SECONDS = 30;

    private static final String DEFAULT_URL = "http://localhost:8080/alerta";

    private static final float DEFAULT_RADIUS_KMS = 50f;

    private static final int DEFAULT_LOCATION_POLLING_INTERVAL = 1000 * 60 * 2;

    private Context context;

    private String alertasUrl;

    private Double radiusKms;

    private int locationPollingInterval;

    private Location currentLocation;

    public AlertaReceiver(Context context) {

        this.context = context;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        this.context = context;

        alertasUrl = preferences.getString("alerts.service.url", DEFAULT_URL);

        radiusKms = new Double(preferences.getFloat("alerts.service.radiusKms", DEFAULT_RADIUS_KMS));

        locationPollingInterval = preferences.getInt("alerts.servce.locationPollingInterval", DEFAULT_LOCATION_POLLING_INTERVAL);

        currentLocation = null;

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

            Log.i(this.getClass().getName(), "Location service location change detected.");

            if(currentLocation == null || !isBetterLocation(location, currentLocation)) {
                currentLocation = location;
            }

            AlertaClient alertaClient = new AlertaClient(alertasUrl);

            List<Alerta> alertas = alertaClient.getAlertaByRegiao(location.getLatitude(), location.getLongitude(), radiusKms);

            if(alertas == null) {
                Log.i(this.getClass().getName(), "Alerts not found for now.");
            }
            else {
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

                    Log.w(this.getClass().getName(), "Notification posted.");
                }
            }

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

            Log.w(this.getClass().getName(), "Location services status " + s + " change detected.");

        }

        @Override
        public void onProviderEnabled(String s) {

            Log.w(this.getClass().getName(), "Location services provider " + s + " enabled.");

        }

        @Override
        public void onProviderDisabled(String s) {

            Log.w(this.getClass().getName(), "Location services provider " + s + " disabled.");

        }

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i(this.getClass().getName(), "Alert request triggered.");

        try {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//            Criteria criteria = new Criteria();
//            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
//            criteria.setAltitudeRequired(false);
//            criteria.setBearingRequired(false);
//            criteria.setCostAllowed(true);
//            String bestProvider = locationManager.getBestProvider(criteria, true);
//            if(bestProvider == null) {
//                Log.e(this.getClass().getName(), "Location service providers not found.");
//            }
//            else {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.i(this.getClass().getName(), "Location service registered.");

                LocationListener locationListener = new AlertaLocationListener();
//                    locationManager.requestLocationUpdates(bestProvider, 0, 0, locationListener);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }

            Log.i(this.getClass().getName(), "Background task finished.");
//            }
        }
        catch(Exception ex) {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
        }

        Log.w(this.getClass().getName(), "Location services service unavailable.");

    }

    public void setAlarm(Context context) {

        Intent intent = new Intent(context, AlertaReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(null);
        int timeInSeconds =
                android.os.Debug.isDebuggerConnected() ?
                        DEBUG_TIME_IN_SECONDS :
                        preferences.getInt("alerts.service.timeInSeconds", DEFAULT_TIME_IN_SECONDS);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), timeInSeconds, pendingIntent);

        Log.i(this.getClass().getName(), "Alarm set request triggered.");

    }

    public void cancelAlarm(Context context) {

        Intent intent = new Intent(context, AlertaReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);

        Log.i(this.getClass().getName(), "Alarm cancel request triggered.");

    }

}
