package br.ita.bditac.mobile.alertas;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext();

        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.i(this.getClass().getSimpleName(), "Permission to localization services requested.");

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.REQUEST_CODE_ACCESS_FINE_LOCATION);
        }

    }

    // TODO: Fazer uma função que generaliza as funções start

    public void startCadastrarEvento(View view) {
        Intent itentCadastrarEvento = new Intent(this, CadastrarEventoActivity.class);
        startActivity(itentCadastrarEvento);
    }

    public void startConsultarEvento(View view) {
        Intent itentConsultarEvento = new Intent(this, ConsultaEventoActivity.class);
        startActivity(itentConsultarEvento);
    }

    public void startConsultarIndicador(View view) {
        Intent itentConsultarIndicador = new Intent(this, ConsultarIndicadoresActivity.class);
        startActivity(itentConsultarIndicador);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case Constants.REQUEST_CODE_ACCESS_FINE_LOCATION: {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(this.getClass().getSimpleName(), "Localization services permission granted.");
                }
                else {
                    Log.e(this.getClass().getSimpleName(), "Localization services permission not granted..");
                }
            }
        }

    }

}
