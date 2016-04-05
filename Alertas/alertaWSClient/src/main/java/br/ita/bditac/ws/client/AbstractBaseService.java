package br.ita.bditac.ws.client;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public abstract class AbstractBaseService {
    
    private String hostURL;
    
    private RestTemplate restTemplate;

    public AbstractBaseService(String hostURL) {
        this.hostURL = hostURL;
        
        restTemplate = new RestTemplate(true);
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.setErrorHandler(new ClientErrorHandler());
    }
    
    public final String getHostURL() {
        return hostURL;
    }
    
    public final RestTemplate getRestTemplate() {
        return restTemplate;
    }
    
}
