package br.ita.bditac.ws.client;


import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import br.ita.bditac.ws.model.Alerta;
import br.ita.bditac.ws.model.AlertaResources;


public class AlertaClient extends AbstractBaseService {

    private static final String SERVICE_URL = "/avisos/nearbyWarnings";

    private static long lastTimestamp = 0;

    public AlertaClient(String hostURL) {
        super(hostURL);
    }

    private List<Alerta> getAlertaByRegiaoAndTime(long timestamp, double latitude, double longitude, double raio) {

        DecimalFormat df=new DecimalFormat("#0.000000");
        SimpleDateFormat tf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.US));
        UriComponentsBuilder uriComponentsBuilder=UriComponentsBuilder.fromHttpUrl(getHostURL() + SERVICE_URL).
            queryParam("timestamp", "'" + tf.format(timestamp) + "'").
            queryParam("latitude", df.format(latitude)).
            queryParam("longitude", df.format(longitude)).
            queryParam("raio", df.format(raio));

        ResponseEntity<AlertaResources> response=getRestTemplate().getForEntity(uriComponentsBuilder.build().encode().toUri(), AlertaResources.class);

        lastTimestamp=DateTime.now().getMillis();

        if(response.getStatusCode() == HttpStatus.OK) {
            return response.getBody().unwrap();
        }
        else {
            return null;
        }

    }

    public List<Alerta> getAlertaByRegiaoRecentes(double latitude, double longitude, double raio) {

        List<Alerta> listAlertas = getAlertaByRegiaoAndTime(lastTimestamp, latitude, longitude, raio);

        lastTimestamp = DateTime.now().getMillis();

        return listAlertas;

    }

    public List<Alerta> getAlertaByRegiao(double latitude, double longitude, double raio) {

        List<Alerta> listAlertas = getAlertaByRegiaoAndTime(0, latitude, longitude, raio);

        return listAlertas;

    }

}
