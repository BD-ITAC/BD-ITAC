package br.ita.bditac.mobile.alertas;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AlertaService extends Service {

    AlertaReceiver alertaReceiver = new AlertaReceiver();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        alertaReceiver.setAlarm(this);

        return START_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        alertaReceiver.setAlarm(this);
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

}
