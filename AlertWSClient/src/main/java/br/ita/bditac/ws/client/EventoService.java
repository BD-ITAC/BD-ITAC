package br.ita.bditac.ws.client;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ita.bditac.ws.model.Evento;
import br.ita.bditac.ws.model.EventoResponse;

public class EventoService extends AbstractBaseService {
    
    private static final String SERVICE_URL = "/evento";
    
    public EventoService(String hostURL) {
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

        ResponseEntity<EventoResponse> eventoResponseEntity =  restTemplate.postForEntity(getHostURL() + SERVICE_URL, new HttpEntity<Evento>(evento), EventoResponse.class);
        
        Evento eventoRetorno = eventoResponseEntity.getBody().getEvento();
        
        return eventoRetorno;
        
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
        
        ResponseEntity<EventoResponse> eventoResponseEntity = restTemplate.getForEntity(getHostURL() + SERVICE_URL + "/{id}", EventoResponse.class, params);
        
        Evento evento = eventoResponseEntity.getBody().getEvento();
        
        return evento;
        
    }

}
