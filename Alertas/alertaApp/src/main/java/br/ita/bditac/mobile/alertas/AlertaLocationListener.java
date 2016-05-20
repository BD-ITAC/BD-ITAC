package br.ita.bditac.mobile.alertas;


import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Debug;
import android.preference.PreferenceManager;
import android.util.Log;

public class AlertaLocationListener implements LocationListener {

    private Location currentLocation;

    private Context context;

    public AlertaLocationListener(Context context) {

        super();

        this.context = context;

    }

    @Override
    public void onLocationChanged(Location location) {

        Log.i(this.getClass().getSimpleName(), "Location service location change detected.");

        SharedPreferences preferences = null;

        if(!Debug.isDebuggerConnected()) {
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
        }

        int locationPollingInterval =
                Debug.isDebuggerConnected() ?
                        Constants.DEBUG_LOCATION_POLLING_INTERVAL :
                        preferences.getInt("alerts.service.locationPollingInterval", Constants.DEFAULT_LOCATION_POLLING_INTERVAL);

        if(currentLocation == null || !isBetterLocation(location, currentLocation, locationPollingInterval)) {
            currentLocation = location;
        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

        Log.w(this.getClass().getSimpleName(), "Location services status " + s + " change detected.");

    }

    @Override
    public void onProviderEnabled(String s) {

        Log.w(this.getClass().getSimpleName(), "Location services provider " + s + " enabled.");

    }

    @Override
    public void onProviderDisabled(String s) {

        Log.w(this.getClass().getSimpleName(), "Location services provider " + s + " disabled.");

    }

    private boolean isSameProvider(String currentProvider, String previousProvider) {

        if (currentProvider == null) {
            return previousProvider == null;
        }

        return currentProvider.equals(previousProvider);

    }

    private boolean isBetterLocation(Location location, Location currentBestLocation, int locationPollingInterval) {

        if (currentBestLocation == null) {
            return true;
        }

        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > locationPollingInterval;
        boolean isSignificantlyOlder = timeDelta < -locationPollingInterval;
        boolean isNewer = timeDelta > 0;

        if (isSignificantlyNewer) {
            return true;
        }
        else if (isSignificantlyOlder) {
            return false;
        }

        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        boolean isFromSameProvider = isSameProvider(location.getProvider(), currentBestLocation.getProvider());

        if (isMoreAccurate) {
            return true;
        }
        else if (isNewer && !isLessAccurate) {
            return true;
        }
        else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }

        return false;

    }

}
