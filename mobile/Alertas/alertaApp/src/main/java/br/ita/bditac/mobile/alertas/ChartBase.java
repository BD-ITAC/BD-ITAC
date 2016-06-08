package br.ita.bditac.mobile.alertas;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


public class ChartBase extends FragmentActivity {

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
