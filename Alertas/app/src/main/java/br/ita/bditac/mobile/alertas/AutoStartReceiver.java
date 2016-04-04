package br.ita.bditac.mobile.alertas;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public class AutoStartReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent startServiceIntent = new Intent(context, AlertaService.class);
        context.startService(startServiceIntent);

        Log.i(this.getClass().getName(), "Service registered.");

    }

}
