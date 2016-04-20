package br.ita.bditac.mobile.alertas;

import android.Manifest;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class AlertaPollingService extends IntentService {

    private Location currentLocation;

    public AlertaPollingService() {

        super("AlertaPollingService");

        currentLocation = null;

    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.i(this.getClass().getSimpleName(), "Alert poll request triggered.");

        try {
            Context context = getApplicationContext();

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

                Log.i(this.getClass().getSimpleName(), "Location service registered.");

                LocationListener locationListener = new AlertaLocationListener(context);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                AlertaGetter alertaGetter = new AlertaGetter(context);

                alertaGetter.fetchAlertas(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));

                locationManager.removeUpdates(locationListener);
            }
        }
        catch(Exception ex) {
            Log.e(this.getClass().getSimpleName(), ex.getMessage(), ex);
        }

    }

}
