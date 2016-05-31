package br.ita.bditac.mobile.alertas;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public class AutoStartReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i(this.getClass().getSimpleName(), "Alerta auto-start service registered.");

        Intent alertaServiceIntent = new Intent(context, AlertaService.class);
        startWakefulService(context, alertaServiceIntent);

    }

}
