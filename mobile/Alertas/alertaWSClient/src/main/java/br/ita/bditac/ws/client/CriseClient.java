package br.ita.bditac.ws.client;

import android.util.Log;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import br.ita.bditac.ws.model.Crise;
import br.ita.bditac.ws.model.MessageResource;


public class CriseClient {

    private static final String SERVICE_URL = "/avisos";

    private String hostURL;

    private RestTemplate restTemplate;

    class ClientErrorHandler implements ResponseErrorHandler {

        public void handleError(ClientHttpResponse response) throws IOException {
            Log.e(this.getClass().getSimpleName(), String.format("Response STATUS: %d - '%s'", response.getRawStatusCode(), response.getStatusText()));
            Log.e(this.getClass().getSimpleName(), String.format("Response HEADERS: '%s'", response.getHeaders()));
        }

        public boolean hasError(ClientHttpResponse response) throws IOException {
            if ((response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) || (response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR)) {
                return true;
            }

            return false;
        }

    }

    public CriseClient(String hostURL) {

        this.hostURL = hostURL;

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModules(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaTypes.HAL_JSON));
        converter.setObjectMapper(mapper);

        restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(Collections.<HttpMessageConverter<?>> singletonList(converter));
        restTemplate.setErrorHandler(new ClientErrorHandler());

    }

    public boolean addCrise(Crise crise) {

        Map<String,Object> map = new ObjectMapper().convertValue(crise, Map.class);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON, MediaTypes.HAL_JSON));

        HttpEntity<Map<String, Object>> post = new HttpEntity<>(map, httpHeaders);

        ResponseEntity<MessageResource> response = restTemplate.postForEntity(hostURL + SERVICE_URL, post, MessageResource.class);

        return response.getStatusCode().equals(HttpStatus.CREATED);

    }

}