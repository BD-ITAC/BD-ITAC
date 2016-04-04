package br.ita.bditac.mobile.alertas;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AlertaService extends Service {

    AlertaReceiver alertaReceiver = new AlertaReceiver();

    @Override
    public void onCreate() {

        super.onCreate();

        Log.i(this.getClass().getName(), "Service started.");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        alertaReceiver.setAlarm(this);

        Log.i(this.getClass().getName(), "Service flagged.");

        return START_STICKY;

    }

    @Override
    public void onStart(Intent intent, int startId) {

        alertaReceiver.setAlarm(this);

        Log.i(this.getClass().getName(), "Service flagged.");

    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;

    }

}
