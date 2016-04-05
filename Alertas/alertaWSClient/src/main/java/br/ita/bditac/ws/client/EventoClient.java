package br.ita.bditac.ws.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import br.ita.bditac.ws.model.Evento;

public class EventoClient extends AbstractBaseService {
    
    private static final String SERVICE_URL = "/evento";
    
    public EventoClient(String hostURL) {

        super(hostURL);

    }
    
    public Evento addEvento(Evento evento) {
        
        ResponseEntity<Evento> eventoResponseEntity =  getRestTemplate().postForEntity(getHostURL() + SERVICE_URL, new HttpEntity<Evento>(evento), Evento.class);
        
        if(eventoResponseEntity.getStatusCode() == HttpStatus.CREATED) {
            return eventoResponseEntity.getBody();
        }
        else {
            return null;
        }
        
    }
    
    public Evento getEventoById(int id) {
        
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("id", id);
        
        try {
            ResponseEntity<Evento> eventoResponseEntity = getRestTemplate().getForEntity(getHostURL() + SERVICE_URL + "/{id}", Evento.class, params);
            
            if(eventoResponseEntity.getStatusCode() == HttpStatus.OK) {
                return eventoResponseEntity.getBody();
            }
        }
        catch(Exception ex) {
            
        }
        
        return null;
        
    }

}
