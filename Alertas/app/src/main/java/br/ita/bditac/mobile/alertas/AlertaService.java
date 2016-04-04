package br.ita.bditac.mobile.alertas;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AlertaService extends Service {

    private AlertaReceiver alertaReceiver = new AlertaReceiver();

    public AlertaService() {

        Log.i(this.getClass().getSimpleName(), "Service constructed.");

    }

    @Override
    public void onCreate() {

        super.onCreate();

        Log.i(this.getClass().getSimpleName(), "Service started.");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        alertaReceiver.setAlarm(this);
        sendBroadcast(new Intent(alertaReceiver.getClass().getName()));

        Log.i(this.getClass().getSimpleName(), "Service flagged.");

        return START_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;

    }

}
