package br.ita.bditac.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.ita.bditac.model.Crise;
import br.ita.bditac.ws.support.MessageResource;


/**
*
* = Testes unitários do serviço de Alertas para o BD-ITAC.
*
*/
@RunWith(Parameterized.class)
public class APIPostTestIntegracao {
    
    private static byte[] _foto = null;
    
    private static byte[] getFoto() {
    	
    	try {
	    	if(_foto == null) {
		        _foto = Files.readAllBytes(Paths.get(Thread.currentThread().getContextClassLoader().getResource("foto1.png").getPath()));
	    	}
    	}
    	catch(IOException ioex) {
    		System.out.println(ioex.getMessage());
    	}

		return _foto;
    	
    }

	@Parameter(value = 0) public Crise crise;
	
	@Parameters public static Collection<Crise> data() {
		Crise[] data = new Crise[] {
			new Crise(
		        	"Alagamento no centro de Baurú",
					1,
					"Usuario de Testes",
					"usuario.testes@gmail.com",
					"(12)94321-1234",
					-22.318265,
					-49.068886,
					getFoto())
			};
		
		return Arrays.asList(data);
	}

	class ClientErrorHandler implements ResponseErrorHandler {

        public void handleError(ClientHttpResponse response) throws IOException {
        	
        }

        public boolean hasError(ClientHttpResponse response) throws IOException {
            if ((response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) || (response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR)) {
                return true;
            }

            return false;
        }

    }

    private RestTemplate getRestTemplate() {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.registerModules(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaTypes.HAL_JSON));
        converter.setObjectMapper(mapper);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(Collections.<HttpMessageConverter<?>> singletonList(converter));
        restTemplate.setErrorHandler(new ClientErrorHandler());

        return restTemplate;

    }

    protected String getBaseUrl() {
    	return "http://200.144.14.28:80";
    }
    
    @Test
    public void postCriseGerarAlerta() throws Exception {
        URI criseURI = new URI(getBaseUrl() + "/rest/avisos");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON, MediaTypes.HAL_JSON));
        httpHeaders.add(HttpHeaders.ACCEPT_ENCODING, "gzip");
        @SuppressWarnings("unchecked")
		Map<String,Object> map = new ObjectMapper().convertValue(crise, Map.class);
        HttpEntity<Map<String, Object>> post = new HttpEntity<>(map, httpHeaders);

		ResponseEntity<MessageResource> criseResponseEntity = getRestTemplate().postForEntity(criseURI, post, MessageResource.class);

        assertThat(criseResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
   
}
