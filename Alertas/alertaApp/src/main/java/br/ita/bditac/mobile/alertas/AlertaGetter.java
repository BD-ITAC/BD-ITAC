package br.ita.bditac.mobile.alertas;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Debug;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.ita.bditac.ws.client.AlertaClient;
import br.ita.bditac.ws.model.Alerta;

public class AlertaGetter {

    private static List<Integer> notifications = new ArrayList<Integer>();

    private String alertasUrl;

    private double radiusKms;

    private Context context = null;

    public AlertaGetter(Context context) {

        this.context = context;

        SharedPreferences preferences = null;

        if(!Debug.isDebuggerConnected()) {
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
        }

        alertasUrl =
                Debug.isDebuggerConnected() ?
                        Constants.DEBUG_URL :
                        preferences.getString("alerts.service.url", Constants.DEFAULT_URL);

        radiusKms =
                Debug.isDebuggerConnected() ?
                        Constants.DEBUG_RADIUS_KMS :
                        new Double(preferences.getString("alerts.service.radiusKms", Constants.DEFAULT_RADIUS_KMS)).doubleValue();

    }

    public void fetchAlertas(Location location) {

        AlertaClient alertaClient = new AlertaClient(alertasUrl);

        if(location == null) {
            Log.i(this.getClass().getSimpleName(), "Location not informed.");
        }
        else {
            Log.i(this.getClass().getSimpleName(), "Fetching alerts.");

            List<Alerta> alertas = alertaClient.getAlertaByRegiaoRecentes(location.getLatitude(), location.getLongitude(), radiusKms);

            if (alertas == null) {
                Log.i(this.getClass().getSimpleName(), "Alerts not found for now.");
            }
            else {
                for (Alerta alerta : alertas) {
                    Intent alertaIntent = new Intent(context, NotificationReceiverActivity.class);
                    alertaIntent.putExtra(Constants.EXTRA_NOTIFICATION_LATITUDE, location.getLatitude());
                    alertaIntent.putExtra(Constants.EXTRA_NOTIFICATION_LONGITUDE, location.getLongitude());
                    alertaIntent.putExtra(Constants.EXTRA_NOTIFICATION_ALERTA, alerta.getDescricaoResumida());
                    alertaIntent.putExtra(Constants.EXTRA_NOTIFICATION_DESCRICAO, alerta.getDescricaoCompleta());
                    PendingIntent pendingAlertaIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), alertaIntent, 0);

                    Notification notification = new Notification.Builder(context)
                            .setContentTitle(alerta.getDescricaoResumida())
                            .setContentText(alerta.getDescricaoCompleta())
                            .setStyle(new Notification.BigTextStyle().bigText(alerta.getDescricaoCompleta()))
                            .setSmallIcon(R.drawable.ic_action_alert)
                            .setContentIntent(pendingAlertaIntent)
                            .addAction(R.drawable.ic_stat_alert, context.getText(R.string.open), pendingAlertaIntent)
                            .build();
                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

                    if (notifications.size() > 0) {
                        Log.w(this.getClass().getSimpleName(), "Removing old notifications.");

                        for (Integer notificationId : notifications) {
                            notificationManager.cancel(notificationId);
                        }
                    }

                    notification.flags |= Notification.FLAG_AUTO_CANCEL;

                    int notificationId = NotificationIdGenerator.getNewID();
                    notifications.add(notificationId);

                    notificationManager.notify(notificationId, notification);

                    Log.w(this.getClass().getSimpleName(), "Notification posted.");
                }
            }
        }

    }

}
