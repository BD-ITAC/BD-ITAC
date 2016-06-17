package br.ita.bditac.mobile.alertas;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class ChartBase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
    }

}
