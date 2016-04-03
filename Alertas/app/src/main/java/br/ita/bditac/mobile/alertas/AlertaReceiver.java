package br.ita.bditac.mobile.alertas;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AlertaReceiver extends BroadcastReceiver {

    private static final int DEFAULT_TIME_IN_SECONDS = 600;

    private static final int DEBUG_TIME_IN_SECONDS = 30;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startServiceIntent = new Intent(context, AlertaService.class);
        context.startService(startServiceIntent);
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
    }

    public void cancelAlarm(Context context) {
        Intent intent = new Intent(context, AlertaReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

}
