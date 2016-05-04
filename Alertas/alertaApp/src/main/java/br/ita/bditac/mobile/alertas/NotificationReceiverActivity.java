package br.ita.bditac.mobile.alertas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public class NotificationReceiverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_receiver);

        double latitude = getIntent().getDoubleExtra(Constants.EXTRA_NOTIFICATION_LATITUDE, Constants.DEFAULT_NOTIFICATION_LATITUDE);
        double longitude = getIntent().getDoubleExtra(Constants.EXTRA_NOTIFICATION_LONGITUDE, Constants.DEFAULT_NOTIFICATION_LONGITUDE);
        String alerta = getIntent().getStringExtra(Constants.EXTRA_NOTIFICATION_ALERTA);
        String descricao = getIntent().getStringExtra(Constants.EXTRA_NOTIFICATION_DESCRICAO);

        MapView map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        IMapController mapController = map.getController();
        mapController.setZoom(9);

        GeoPoint alertaPoint = new GeoPoint(latitude, longitude);
        mapController.setCenter(alertaPoint);

        Marker marker = new Marker(map);
        marker.setPosition(alertaPoint);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setIcon(getResources().getDrawable(R.drawable.ic_action_alert));
        marker.setTitle(alerta);
        marker.setSubDescription(descricao);
        map.getOverlays().add(marker);

        map.invalidate();

        Log.w(this.getClass().getSimpleName(), "Alerta notification activity triggered.");

    }

}
