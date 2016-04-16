package br.ita.bditac.ws.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import br.ita.bditac.ws.model.Evento;
import br.ita.bditac.ws.model.EventoResource;

public class EventoClient extends AbstractBaseService {

    private static final String SERVICE_URL = "/evento";

    private static final String ID_PARM = "/{id}";

    public EventoClient(String hostURL) {
        super(hostURL);
    }

    public Evento addEvento(Evento evento) {

        HttpEntity envio = new HttpEntity(evento, getRequestHeader());

        ResponseEntity<EventoResource> eventoResponseEntity =  getRestTemplate().postForEntity(getHostURL() + SERVICE_URL, envio, EventoResource.class);

        if(eventoResponseEntity.getStatusCode() == HttpStatus.CREATED) {
            return eventoResponseEntity.getBody().getContent();
        }
        else {
            return null;
        }

    }

    public Evento getEventoById(int id) {

        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("id", id);

        try {
            HttpEntity envio = new HttpEntity(getResponseHeader());

            ResponseEntity<EventoResource> eventoResponseEntity = getRestTemplate().exchange(getHostURL() + SERVICE_URL + ID_PARM, HttpMethod.GET, envio, EventoResource.class, params);

            if(eventoResponseEntity.getStatusCode() == HttpStatus.OK) {
                return eventoResponseEntity.getBody().getContent();
            }
        }
        catch(Exception ex) {

        }

        return null;

    }

}