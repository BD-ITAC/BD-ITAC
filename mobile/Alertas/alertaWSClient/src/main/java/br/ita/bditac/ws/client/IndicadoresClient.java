package br.ita.bditac.ws.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import br.ita.bditac.ws.model.Indicador;
import br.ita.bditac.ws.model.IndicadorResources;


public class IndicadoresClient extends AbstractBaseService {

    private static final String SERVICE_URL = "/avisos/indicators";

    public IndicadoresClient(String hostURL) {
        super(hostURL);
    }

    public List<Indicador> getIndicadores() {

        HttpEntity envio=new HttpEntity(getResponseHeader());

        ResponseEntity<IndicadorResources> response = getRestTemplate().exchange(getHostURL() + SERVICE_URL, HttpMethod.GET, envio, IndicadorResources.class);

        if(response.getStatusCode() == HttpStatus.OK) {
            return response.getBody().unwrap();
        }
        else {
            return null;
        }

    }

}
