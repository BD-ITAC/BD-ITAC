package br.ita.bditac.ws.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import br.ita.bditac.ws.model.Crise;
import br.ita.bditac.ws.model.CriseResource;

public class CriseClient extends AbstractBaseService {

    private static final String SERVICE_URL = "/crise";

    private static final String ID_PARM = "/{id}";

    public CriseClient(String hostURL) {
        super(hostURL);
    }

    public Crise addCrise(Crise crise) {

        HttpEntity envio = new HttpEntity(crise, getRequestHeader());

        ResponseEntity<CriseResource> criseResponseEntity =  getRestTemplate().postForEntity(getHostURL() + SERVICE_URL, envio, CriseResource.class);

        if(criseResponseEntity.getStatusCode() == HttpStatus.CREATED) {
            return criseResponseEntity.getBody().getContent();
        }
        else {
            return null;
        }

    }

    public Crise getCriseById(int id) {

        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("id", id);

        try {
            HttpEntity envio = new HttpEntity(getResponseHeader());

            ResponseEntity<CriseResource> criseResponseEntity = getRestTemplate().exchange(getHostURL() + SERVICE_URL + ID_PARM, HttpMethod.GET, envio, CriseResource.class, params);

            if(criseResponseEntity.getStatusCode() == HttpStatus.OK) {
                return criseResponseEntity.getBody().getContent();
            }
        }
        catch(Exception ex) {

        }

        return null;

    }

}