package br.ita.bditac.ws.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ita.bditac.ws.model.Alerta;
import br.ita.bditac.ws.model.AlertaResource;
import br.ita.bditac.ws.model.AlertaResources;

public class AlertaClient extends AbstractBaseService {

    private static final String SERVICE_URL = "/alerta";

    public AlertaClient(String hostURL) {
        super(hostURL);
    }

    public Alerta addAlerta(Alerta alerta) {

        HttpEntity<Alerta> alertaEnvio = new HttpEntity<Alerta>(alerta, getRequestHeader());

        ResponseEntity<AlertaResource> alertaResponseEntity = getRestTemplate().postForEntity(getHostURL() + SERVICE_URL, alertaEnvio, AlertaResource.class);

        Alerta alertaRetorno = alertaResponseEntity.getBody().getContent();

        return alertaRetorno;
    }

    public Alerta getAlertaById(int id) {

        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("id", id);

        try {
            HttpEntity envio = new HttpEntity(getResponseHeader());

            ResponseEntity<AlertaResource> alertaResponseEntity = getRestTemplate().exchange(getHostURL() + SERVICE_URL + "/{id}", HttpMethod.GET, envio, AlertaResource.class, params);

            if(alertaResponseEntity.getStatusCode() == HttpStatus.OK) {
                return alertaResponseEntity.getBody().getContent();
            }
        }
        catch(Exception ex) {

        }

        return null;

    }

    public boolean hasAlerta(double latitude, double longitude, double raio) {

        Map<String, Double> params = new HashMap<String, Double>();
        params.put("latitude", latitude);
        params.put("longitude", longitude);
        params.put("raio", raio);

        HttpEntity envio = new HttpEntity(getResponseHeader());

        ResponseEntity<Void> response = getRestTemplate().exchange(getHostURL() + SERVICE_URL + "/latitude/{latitude}/longitude/{longitude}/raio/{raio}", HttpMethod.HEAD, envio, Void.class, params);

        if(response.getStatusCode() == HttpStatus.OK) {
            return true;
        }
        else {
            return false;
        }

    }

    public List<Alerta> getAlertaByRegiao(double latitude, double longitude, double raio) {

        Map<String, Double> params = new HashMap<String, Double>();
        params.put("latitude", latitude);
        params.put("longitude", longitude);
        params.put("raio", raio);

        HttpEntity envio = new HttpEntity(getResponseHeader());

        ResponseEntity<AlertaResources> response = getRestTemplate().exchange(getHostURL() + SERVICE_URL + "/latitude/{latitude}/longitude/{longitude}/raio/{raio}", HttpMethod.GET, envio, AlertaResources.class, params);

        List<Alerta> alertas = response.getBody().unwrap();

        return alertas;

    }

}