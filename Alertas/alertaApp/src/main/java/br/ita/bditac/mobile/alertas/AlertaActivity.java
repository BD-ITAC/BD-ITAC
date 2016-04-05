package br.ita.bditac.mobile.alertas;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class AlertaActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ACCESS_FINE_LOCATION = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alerta);

        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.i(this.getClass().getSimpleName(), "Permission to localization services requested.");

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ACCESS_FINE_LOCATION);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CODE_ACCESS_FINE_LOCATION: {
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
