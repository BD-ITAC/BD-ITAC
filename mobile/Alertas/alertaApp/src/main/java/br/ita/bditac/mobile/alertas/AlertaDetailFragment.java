package br.ita.bditac.mobile.alertas;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import br.ita.bditac.ws.model.Alerta;


public class AlertaDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID="item_id";

    private Alerta mItem;

    public AlertaDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            Map<String, Alerta> alertaMap = new HashMap<>();
            for(Alerta alerta : AlertaListActivity.ALERTAS_LIST) alertaMap.put(new Integer(alerta.getId()).toString(), alerta);
            String argItem = getArguments().getString(ARG_ITEM_ID);
            mItem = alertaMap.get(argItem);

            Activity activity=this.getActivity();
            CollapsingToolbarLayout appBarLayout=(CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if(appBarLayout != null) {
                appBarLayout.setTitle(mItem.getDescricaoResumida());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.alerta_detail, container, false);

        // Show the dummy content as text in a TextView.
        if(mItem != null) {
            ((TextView) rootView.findViewById(R.id.alerta_detail)).setText(mItem.getDescricaoCompleta());
        }

        return rootView;
    }
}
