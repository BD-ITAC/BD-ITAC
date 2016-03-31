package br.ita.bditac.ws.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ita.bditac.ws.model.Alerta;
import br.ita.bditac.ws.model.AlertaResource;
import br.ita.bditac.ws.model.AlertaResources;

public class AlertaClient extends AbstractBaseService {

    private static final String SERVICE_URL = "/alerta";
    
    public AlertaClient(String hostURL) {
        super(hostURL);
    }

    public Alerta addAlerta(Alerta alerta) {
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaTypes.HAL_JSON));
        converter.setObjectMapper(mapper);
      
        RestTemplate restTemplate = new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));

        ResponseEntity<AlertaResource> alertaResponseEntity = restTemplate.postForEntity(getHostURL() + SERVICE_URL, new HttpEntity<Alerta>(alerta), AlertaResource.class);
        
        Alerta alertaRetorno = alertaResponseEntity.getBody().getContent();
        
        return alertaRetorno;
    }

    public Alerta getAlertaById(int id) {
        
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
            ResponseEntity<AlertaResource> alertaResponseEntity = restTemplate.getForEntity(getHostURL() + SERVICE_URL + "/{id}", AlertaResource.class, params);

            if(alertaResponseEntity.getStatusCode() == HttpStatus.OK) {
                return alertaResponseEntity.getBody().getContent();
            }
        }
        catch(Exception ex) {
            
        }

        return null;

    }

    public List<Alerta> getAlertaByRegiao(double latitude, double longitude, double raio) {
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaTypes.HAL_JSON));
        converter.setObjectMapper(mapper);
        
        Map<String, Double> params = new HashMap<String, Double>();
        params.put("latitude", latitude);
        params.put("longitude", latitude);
        params.put("raio", latitude);
        
        RestTemplate restTemplate = new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));
        
        List<Alerta> alertas = restTemplate.getForObject(getHostURL() + SERVICE_URL + "/latitude/{latitude}/longitude/{longitude}/raio/{raio}", AlertaResources.class, params).unwrap();
        
        return alertas;
        
    }

}
