package br.ita.bditac.mobile.alertas;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutoStartReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startServiceIntent = new Intent(context, AlertaService.class);
        context.startService(startServiceIntent);
    }

}
