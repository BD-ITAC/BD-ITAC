package br.ita.bditac.ws.client;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import br.ita.bditac.ws.model.Indicadores;
import br.ita.bditac.ws.model.IndicadoresResource;


public class IndicadoresClient extends AbstractBaseService {

    private static final String SERVICE_URL = "/indicadores";

    public IndicadoresClient(String hostURL) {
        super(hostURL);
    }

    public Indicadores getIndicadores() {

        HttpEntity envio = new HttpEntity(getResponseHeader());

        ResponseEntity<IndicadoresResource> response = getRestTemplate().exchange(getHostURL() + SERVICE_URL, HttpMethod.GET, envio, IndicadoresResource.class);

        return response.getBody().getContent();

    }

}
