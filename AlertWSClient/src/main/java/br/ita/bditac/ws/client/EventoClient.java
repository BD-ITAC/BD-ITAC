package br.ita.bditac.ws.client;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ita.bditac.ws.model.Evento;
import br.ita.bditac.ws.model.EventoResource;

public class EventoClient extends AbstractBaseService {
    
    private static final String SERVICE_URL = "/evento";
    
    public EventoClient(String hostURL) {
        super(hostURL);
    }
    
    public Evento addEvento(Evento evento) {
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaTypes.HAL_JSON));
        converter.setObjectMapper(mapper);
      
        RestTemplate restTemplate = new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));

        ResponseEntity<EventoResource> eventoResponseEntity =  restTemplate.postForEntity(getHostURL() + SERVICE_URL, new HttpEntity<Evento>(evento), EventoResource.class);
        
        if(eventoResponseEntity.getStatusCode() == HttpStatus.CREATED) {
            return eventoResponseEntity.getBody().getContent();
        }
        else {
            return null;
        }
        
    }
    
    public Evento getEventoById(int id) {
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaTypes.HAL_JSON));
        converter.setObjectMapper(mapper);
        
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("id", id);
        
        RestTemplate restTemplate = new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));
        restTemplate.setErrorHandler(new ClientErrorHandler());
        
        try {
            ResponseEntity<EventoResource> eventoResponseEntity = restTemplate.getForEntity(getHostURL() + SERVICE_URL + "/{id}", EventoResource.class, params);
            
            if(eventoResponseEntity.getStatusCode() == HttpStatus.OK) {
                return eventoResponseEntity.getBody().getContent();
            }
        }
        catch(Exception ex) {
            
        }
        
        return null;
        
    }

}
