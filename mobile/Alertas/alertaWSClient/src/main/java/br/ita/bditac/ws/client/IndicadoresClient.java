package br.ita.bditac.ws.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import br.ita.bditac.ws.model.Indicador;
import br.ita.bditac.ws.model.IndicadorResources;


public class IndicadoresClient extends AbstractBaseService {

    private static final String SERVICE_URL = "/indicadores";

    private static final String COORDS_PARM = "/latitude/{latitude}/longitude/{longitude}/raio/{raio}";

    public IndicadoresClient(String hostURL) {
        super(hostURL);
    }

    public List<Indicador> getIndicadores(double latitude, double longitude, double raio) {

        Map<String, String> params = new LinkedHashMap<String, String>();
        DecimalFormat df = new DecimalFormat("#0.000000");
        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.US));
        params.put("latitude", df.format(latitude));
        params.put("longitude", df.format(longitude));
        params.put("raio", df.format(raio));

        HttpEntity envio = new HttpEntity(getResponseHeader());

        ResponseEntity<IndicadorResources> response = getRestTemplate().exchange(getHostURL() + SERVICE_URL + COORDS_PARM, HttpMethod.GET, envio, IndicadorResources.class, params);

        if(response.getStatusCode() == HttpStatus.OK) {
            return response.getBody().unwrap();
        }
        else {
            return null;
        }

    }

}
