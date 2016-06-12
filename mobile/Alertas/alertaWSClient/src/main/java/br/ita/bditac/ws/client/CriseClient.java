package br.ita.bditac.ws.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import br.ita.bditac.ws.model.Crise;
import br.ita.bditac.ws.model.MessageResource;

public class CriseClient extends AbstractBaseService {

    private static final String SERVICE_URL = "/crisiss";

    public CriseClient(String hostURL) {
        super(hostURL);
    }

    public void addCrise(Crise crise) {

        HttpEntity envio = new HttpEntity(crise, getRequestHeader());

        ResponseEntity<MessageResource> criseResponseEntity =  getRestTemplate().postForEntity(getHostURL() + SERVICE_URL, envio, MessageResource.class);

    }

}