package br.ita.bditac.mobile.alertas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class NotificationReceiverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_receiver);

        Log.w(this.getClass().getSimpleName(), "Notification activity triggered.");

    }

}
