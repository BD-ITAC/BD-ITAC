package br.ita.bditac.mobile.alertas;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class AlertaActivity extends AppCompatActivity {

    public final int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerta);
    }

    public void createNotification(View view) {

        Intent getAlertaIntent = new Intent(this, NotificationReceiverActivity.class);
        PendingIntent pGetAlertaIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), getAlertaIntent, 0);

        Notification notification = new Notification.Builder(this)
                .setContentTitle(getText(R.string.msg_alert_received))
                // os textos devem ser obtidos do webservice!
                // uma descrição resumida
                .setContentText("Alerta de invasão alienígena")
                // uma descrição completa
                .setStyle(new Notification.BigTextStyle().bigText("Vários objetos voadores de diversas configurações foram observados em todo o mundo disparando aleatoriamente contra edifícios, veículos terrestres, marítmos e aéreos e até mesmo pessoas"))
                .setSmallIcon(R.drawable.ic_action_alert)
                .setContentIntent(pGetAlertaIntent)
                .addAction(R.drawable.ic_stat_alert, getText(R.string.open), pGetAlertaIntent)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(NOTIFICATION_ID, notification);

    }

}
