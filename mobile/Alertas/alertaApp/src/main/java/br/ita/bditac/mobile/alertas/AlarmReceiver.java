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

public class AlarmReceiver extends WakefulBroadcastReceiver {

    private int alarmTimer;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i(this.getClass().getSimpleName(), "Alerta alarm request triggered.");

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
                        Constants.DEBUG_TIMER :
                        preferences.getInt("alerts.service.alarmTimer", Constants.DEFAULT_TIMER);

        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), alarmTimer, pendingIntent);

        Log.i(this.getClass().getSimpleName(), "Alerta alarm set to trigger at " + alarmTimer + "ms.");

    }

}
