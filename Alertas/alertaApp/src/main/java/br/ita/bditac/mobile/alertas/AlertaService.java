package br.ita.bditac.mobile.alertas;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class AlertaService extends IntentService {

    private AlarmReceiver alarmReceiver = new AlarmReceiver();

    public AlertaService() {

        super("br.ita.bditac.mobile.alertas.AlertaService");

        Log.i(this.getClass().getSimpleName(), "Alerta service created.");

    }

    @Override
    public void onHandleIntent(Intent intent) {

        alarmReceiver.setAlarm(this);
        sendBroadcast(new Intent(alarmReceiver.getClass().getName()));

        Log.i(this.getClass().getSimpleName(), "Alerta service alarm flagged.");

    }

}
