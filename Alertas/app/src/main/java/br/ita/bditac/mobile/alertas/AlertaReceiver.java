package br.ita.bditac.mobile.alertas;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Debug;
import android.preference.PreferenceManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public class AlertaReceiver extends WakefulBroadcastReceiver {

    private static final int DEFAULT_TIME = 600000;

    private static final int DEBUG_TIME = 30000;

    private int alarmTimer;

    public AlertaReceiver() {

        Log.i(this.getClass().getSimpleName(), "Alert receiver initialized.");

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i(this.getClass().getSimpleName(), "Alert receiver request triggered.");

        Intent alertaPollingService = new Intent(context, AlertaPollingService.class);

        startWakefulService(context, alertaPollingService);

    }

    public void setAlarm(Context context) {

        SharedPreferences preferences = null;

        if(!android.os.Debug.isDebuggerConnected()) {
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
        }

        alarmTimer =
                Debug.isDebuggerConnected() ?
                        DEBUG_TIME:
                        preferences.getInt("alerts.service.alarmTimer", DEFAULT_TIME);

        Intent intent = new Intent(context, AlertaReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), alarmTimer, pendingIntent);

        Log.i(this.getClass().getSimpleName(), "Alarm set request triggered.");

    }

    public void cancelAlarm(Context context) {

        Intent intent = new Intent(context, AlertaReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);

        Log.i(this.getClass().getSimpleName(), "Alarm cancel request triggered.");

    }

}
