package br.ita.bditac.mobile.alertas;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public class AutoStartReceiver extends WakefulBroadcastReceiver {

    public AutoStartReceiver() {

        Log.i(this.getClass().getSimpleName(), "Auto start receiver constructed.");

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent alertaServiceIntent = new Intent(context, AlertaService.class);
        context.startService(alertaServiceIntent);

        Log.i(this.getClass().getSimpleName(), "Service registered.");

    }

}
