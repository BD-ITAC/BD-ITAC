package br.ita.bditac.ws.client;


import org.joda.time.DateTime;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import br.ita.bditac.ws.model.Alerta;
import br.ita.bditac.ws.model.AlertaResources;


public class AlertaClient extends AbstractBaseService {

    private static final String SERVICE_URL = "/alerta";

    private static final String COORDS_PARM = "/timestamp/{timestamp}/latitude/{latitude}/longitude/{longitude}/raio/{raio}";

    private static long lastTimestamp = 0;

    public AlertaClient(String hostURL) {
        super(hostURL);
    }

    public boolean hasAlerta(double latitude, double longitude, double raio) {

        Map<String, String> params = new HashMap<String, String>();
        DecimalFormat df = new DecimalFormat("#0.000000");
        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.US));
        params.put("timestamp", Long.toString(lastTimestamp));
        params.put("latitude", df.format(latitude));
        params.put("longitude", df.format(longitude));
        params.put("raio", df.format(raio));

        HttpEntity envio = new HttpEntity(getResponseHeader());

        ResponseEntity<Void> response = getRestTemplate().exchange(getHostURL() + SERVICE_URL + COORDS_PARM, HttpMethod.HEAD, envio, Void.class, params);

        if(response.getStatusCode() == HttpStatus.OK) {
            return true;
        }
        else {
            return false;
        }

    }

    public List<Alerta> getAlertaByRegiaoRecentes(double latitude, double longitude, double raio) {

        Map<String, String> params = new HashMap<String, String>();
        DecimalFormat df = new DecimalFormat("#0.000000");
        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.US));
        params.put("timestamp", Long.toString(lastTimestamp));
        params.put("latitude", df.format(latitude));
        params.put("longitude", df.format(longitude));
        params.put("raio", df.format(raio));

        HttpEntity envio = new HttpEntity(getResponseHeader());

        ResponseEntity<AlertaResources> response = getRestTemplate().exchange(getHostURL() + SERVICE_URL + COORDS_PARM, HttpMethod.GET, envio, AlertaResources.class, params);

        lastTimestamp = DateTime.now().getMillis();

        if(response.getStatusCode() == HttpStatus.OK) {
            return response.getBody().unwrap();
        }
        else {
            return null;
        }

    }

    public List<Alerta> getAlertaByRegiao(double latitude, double longitude, double raio) {

        Map<String, String> params = new HashMap<String, String>();
        DecimalFormat df = new DecimalFormat("#0.000000");
        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.US));
        params.put("timestamp", "0");
        params.put("latitude", df.format(latitude));
        params.put("longitude", df.format(longitude));
        params.put("raio", df.format(raio));

        HttpEntity envio = new HttpEntity(getResponseHeader());

        ResponseEntity<AlertaResources> response = getRestTemplate().exchange(getHostURL() + SERVICE_URL + COORDS_PARM, HttpMethod.GET, envio, AlertaResources.class, params);

        lastTimestamp = DateTime.now().getMillis();

        if(response.getStatusCode() == HttpStatus.OK) {
            return response.getBody().unwrap();
        }
        else {
            return null;
        }

    }

}
