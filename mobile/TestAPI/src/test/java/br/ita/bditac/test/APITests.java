package br.ita.bditac.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import br.ita.bditac.model.Alerta;
import br.ita.bditac.model.Categoria;
import br.ita.bditac.model.Crise;
import br.ita.bditac.model.Indicador;
import br.ita.bditac.ws.support.AlertaResources;
import br.ita.bditac.ws.support.AlternateMessageResource;
import br.ita.bditac.ws.support.CategoriaResources;
import br.ita.bditac.ws.support.CriseResource;
import br.ita.bditac.ws.support.IndicadorResources;

/**
 *
 * = Testes unitários do serviço de Alertas para o BD-ITAC.
 *
 */
@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class APITests {

    private static final double DELTA = 1e-6;
    
    private static String _foto = null;
    
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

    RestTemplate getRestTemplate() {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.registerModules(new Jackson2HalModule(), new JodaModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaTypes.HAL_JSON, MediaType.APPLICATION_JSON_UTF8, MediaType.TEXT_HTML));
        converter.setObjectMapper(mapper);

        RestTemplate restTemplate = new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));
        restTemplate.setErrorHandler(new ClientErrorHandler());

        return restTemplate;

    }
    
    String getFoto() throws IOException {
    	
    	if(_foto == null) {
	        byte[] binaryFile = Files.readAllBytes(Paths.get(Thread.currentThread().getContextClassLoader().getResource("foto.png").getPath()));
	        _foto = new String (binaryFile, StandardCharsets.UTF_8); 
    	}
	
		return _foto;
    	
    }

    @Autowired
    WebApplicationContext context;

    @Rule
    public RestDocumentation restDocumentation = new RestDocumentation("target/generated-docs");


    protected String getBaseUrl() {
        return "http://bditac.ddns.net";
    }
    
    @Test
    @Ignore
    public void test101PostCriseGerarAlerta() throws Exception {
        URI criseURI = new URI(getBaseUrl() + "/rest/avisos");
        
        Crise criseRequest = new Crise(
                "Deslizamento na na favela do Paraiso",
                1,
                "Ze das Couves",
                "zedascouves@gmail.com",
                "(12) 99876-1234",
                40.0,
                50.0,
                0,
                getFoto());

        ResponseEntity<AlternateMessageResource> criseResponseEntity =  getRestTemplate().postForEntity(criseURI, new HttpEntity<Crise>(criseRequest), AlternateMessageResource.class);

        assertThat(criseResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void test103GetCriseInexistente() throws Exception {
        String criseURL = getBaseUrl() + "/rest/avisos/{id}";

        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("id", 100);

        ResponseEntity<CriseResource> criseResponseEntity = getRestTemplate().getForEntity(criseURL, CriseResource.class, params);

        assertThat(criseResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void test107GetAlertasByCoords() throws Exception {
        String alertaURL = getBaseUrl() + "/rest/avisos/nearbycrisis?{timestamp}/latitude/{latitude}/longitude/{longitude}/raio/{raio}";

        Map<String, String> params = new HashMap<String, String>();
        params.put("timestamp", "0");
        params.put("latitude", "40.0");
        params.put("longitude", "50.0");
        params.put("raio", "1.0");

        List<Alerta> alertas = getRestTemplate().getForObject(alertaURL, AlertaResources.class, params).unwrap();

        assertThat(alertas.get(1).getDescricaoResumida()).isEqualTo("Alerta de deslizamento");
    }

    @Test
    public void test108GetAlertaByCoordsInexistente() throws Exception {
        String alertaURL = getBaseUrl() + "/rest/avisos/nearbycrisis?{timestamp}/latitude/{latitude}/longitude/{longitude}/raio/{raio}";

        Map<String, String> params = new HashMap<String, String>();
        params.put("timestamp", "0");
        params.put("latitude", "0.6");
        params.put("longitude", "0.6");
        params.put("raio", "1.0");

        ResponseEntity<AlertaResources> resources = getRestTemplate().getForEntity(alertaURL, AlertaResources.class, params);
        assertThat(resources.getStatusCode() == HttpStatus.NOT_FOUND);
    }

   @Test
   public void test111PostCriseGerarAlerta() throws Exception {
       URI criseURI = new URI(getBaseUrl() + "/rest/avisos");

       Crise criseRequest = new Crise(
               "Deslizamento na na favela do Paraiso",
               1,
               "Ze das Couves",
               "zedascouves@gmail.com",
               "(12) 99876-1234",
               40.0,
               50.0,
               0,
               getFoto());

       ResponseEntity<CriseResource> criseResponseEntity =  getRestTemplate().postForEntity(criseURI, new HttpEntity<Crise>(criseRequest), CriseResource.class);

       assertThat(criseResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
       
       String alertaURL = getBaseUrl() + "/alerta/timestamp/{timestamp}/latitude/{latitude}/longitude/{longitude}/raio/{raio}";

       Map<String, String> params = new HashMap<String, String>();
       params.put("timestamp", "0");
       params.put("latitude", "40.0");
       params.put("longitude", "50.0");
       params.put("raio", "1.0");

       List<Alerta> alertas = getRestTemplate().getForObject(alertaURL, AlertaResources.class, params).unwrap();

       assertThat(alertas.get(0).getOrigemLatitude()).isEqualTo(criseRequest.getLatitude(), offset(DELTA));
       assertThat(alertas.get(0).getOrigemLongitude()).isEqualTo(criseRequest.getLongitude(), offset(DELTA));
   }
   
   @Test
   public void test115GetCategorias() throws Exception {
	   URI categoriaURI = new URI(getBaseUrl() + "/rest/categories");
	   
	   List<Categoria> categorias = getRestTemplate().getForObject(categoriaURI, CategoriaResources.class).unwrap();
	   
	   assertThat(categorias.get(0).getDescription()).isEqualTo("Acidente de transporte aéreo");
   }

   @Test
   public void test117GetIndicadores() throws Exception {
	   URI indicadoresURI = new URI(getBaseUrl() + "/rest/avisos/indicators");
	   
	   List<Indicador> indicadores = getRestTemplate().getForObject(indicadoresURI, IndicadorResources.class).unwrap();
	   
	   assertThat(indicadores.get(1).getDescricao()).isEqualTo("Pendente");
   }
   
}
