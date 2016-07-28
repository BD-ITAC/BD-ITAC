package br.ita.bditac.ws.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public abstract class AbstractBaseService {
    
    private String hostURL;
    
    private RestTemplate restTemplate;

    private HttpHeaders responseHeader;

    private HttpHeaders requestHeader;

    public AbstractBaseService(String hostURL) {
        this.hostURL = hostURL;
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaTypes.HAL_JSON));
        converter.setObjectMapper(mapper);

        restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(converter);
        restTemplate.setErrorHandler(new ClientErrorHandler());

        responseHeader = new HttpHeaders();
        responseHeader.setAccept(Arrays.asList(MediaType.APPLICATION_JSON, MediaTypes.HAL_JSON));

        requestHeader = new HttpHeaders();
        requestHeader.setAccept(Arrays.asList(MediaType.APPLICATION_JSON, MediaTypes.HAL_JSON));
        requestHeader.setContentType(MediaTypes.HAL_JSON);

    }
    
    public final String getHostURL() {
        return hostURL;
    }
    
    public final RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public final HttpHeaders getResponseHeader() {
        return responseHeader;
    }

    public final HttpHeaders getRequestHeader() {
        return requestHeader;
    }
    
}
