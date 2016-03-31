package br.ita.bditac.ws.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.ita.bditac.ws.model.Alerta;
import br.ita.bditac.ws.model.AlertaResource;
import br.ita.bditac.ws.model.AlertaResources;

public class AlertaClient extends AbstractBaseService {

    private static final String SERVICE_URL = "/alerta";
    
    public AlertaClient(String hostURL) {
        super(hostURL);
    }

    public Alerta addAlerta(Alerta alerta) {
        
        ResponseEntity<AlertaResource> alertaResponseEntity = getRestTemplate().postForEntity(getHostURL() + SERVICE_URL, new HttpEntity<Alerta>(alerta), AlertaResource.class);
        
        Alerta alertaRetorno = alertaResponseEntity.getBody().getContent();
        
        return alertaRetorno;
    }

    public Alerta getAlertaById(int id) {
        
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("id", id);
        
        try {
            ResponseEntity<AlertaResource> alertaResponseEntity = getRestTemplate().getForEntity(getHostURL() + SERVICE_URL + "/{id}", AlertaResource.class, params);

            if(alertaResponseEntity.getStatusCode() == HttpStatus.OK) {
                return alertaResponseEntity.getBody().getContent();
            }
        }
        catch(Exception ex) {
            
        }

        return null;

    }

    public List<Alerta> getAlertaByRegiao(double latitude, double longitude, double raio) {
        
        Map<String, Double> params = new HashMap<String, Double>();
        params.put("latitude", latitude);
        params.put("longitude", latitude);
        params.put("raio", latitude);
        
        List<Alerta> alertas = getRestTemplate().getForObject(getHostURL() + SERVICE_URL + "/latitude/{latitude}/longitude/{longitude}/raio/{raio}", AlertaResources.class, params).unwrap();
        
        return alertas;
        
    }

}
