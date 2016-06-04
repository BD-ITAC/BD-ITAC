package br.ita.bditac.mobile.alertas;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Debug;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;

import br.ita.bditac.ws.client.IndicadoresClient;
import br.ita.bditac.ws.model.Indicadores;


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
