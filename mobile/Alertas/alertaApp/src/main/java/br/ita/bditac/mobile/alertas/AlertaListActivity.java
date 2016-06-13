package br.ita.bditac.mobile.alertas;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.ita.bditac.ws.client.AlertaClient;
import br.ita.bditac.ws.model.Alerta;


public class AlertaListActivity extends AppCompatActivity {

    private Context context;

    private Location currentLocation;

    private String alertasUrl;

    private double radiusKms;

    private LocationListener locationListener;

    public static List<Alerta> ALERTAS_LIST= new ArrayList<Alerta>();

    private class ConsultarAlertasTask extends AsyncTask<Void, Void, Void> {

        private Exception exception;

        private AppCompatActivity activity;

        protected ConsultarAlertasTask(AppCompatActivity activity) {

            super();

            this.activity = activity;

        }

        protected Void doInBackground(Void... params) {

            try {
                if(currentLocation == null) {
                    CharSequence mensagem = getText(R.string.msg_location_service_unaivalable);
                    Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();
                }
                else {
                    AlertaClient alertaClient = new AlertaClient(alertasUrl);

                    ALERTAS_LIST = alertaClient.getAlertaByRegiao(currentLocation.getLatitude(), currentLocation.getLongitude(), radiusKms);
                }
            }
            catch(Exception ex) {
                CharSequence mensagem = getText(R.string.msg_alerts_service_unaivalable);
                Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();

                Log.e(this.getClass().getSimpleName(), ex.getMessage(), ex);
            }
            finally {
                return null;
            }

        }

        @Override
        protected void onPostExecute(Void params) {

            super.onPostExecute(params);

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

                Log.i(this.getClass().getSimpleName(), "Alerta location service deregistered.");

                locationManager.removeUpdates(locationListener);
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerta_list);

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        // Show the Up button in the action bar.
        ActionBar actionBar=getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        View recyclerView=findViewById(R.id.alerta_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        SharedPreferences preferences = null;

        this.context = getApplicationContext();

        if (!Debug.isDebuggerConnected()) {
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
        }

        alertasUrl = Debug.isDebuggerConnected() ? Constants.DEBUG_URL : preferences.getString("alerts.service.url", Constants.DEFAULT_URL);

        radiusKms =
                Debug.isDebuggerConnected() ?
                        Constants.DEBUG_RADIUS_KMS :
                        new Double(preferences.getString("alerts.service.radiusKms", Constants.DEFAULT_RADIUS_KMS)).doubleValue();

        try {
            Context context = getApplicationContext();

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

                Log.i(this.getClass().getSimpleName(), "Alerta location service registered.");

                locationListener = new AlertaLocationListener(context);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }
        catch(Exception ex) {
            CharSequence mensagem = getText(R.string.msg_alerts_service_unaivalable);
            Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();

            Log.e(this.getClass().getSimpleName(), ex.getMessage(), ex);
        }

        new ConsultarAlertasTask(this).execute();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(ALERTAS_LIST));
    }

    public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Alerta> mValues;

        public SimpleItemRecyclerViewAdapter(List<Alerta> items) {
            mValues=items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.alerta_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem=mValues.get(position);
            holder.mIdView.setText(new Integer(mValues.get(position).getId()).toString());
            holder.mContentView.setText(mValues.get(position).getDescricaoCompleta());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Context context=v.getContext();
                Intent intent=new Intent(context, AlertaDetailActivity.class);
                intent.putExtra(AlertaDetailFragment.ARG_ITEM_ID, new Integer(holder.mItem.getId()).toString());

                context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public Alerta mItem;

            public ViewHolder(View view) {
                super(view);
                mView=view;
                mIdView=(TextView) view.findViewById(R.id.id);
                mContentView=(TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
