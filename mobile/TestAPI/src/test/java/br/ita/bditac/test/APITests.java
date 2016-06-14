package br.ita.bditac.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import br.ita.bditac.model.Categoria;
import br.ita.bditac.model.Crise;
import br.ita.bditac.model.Indicador;
import br.ita.bditac.ws.support.AlertaResources;
import br.ita.bditac.ws.support.AvisoResources;
import br.ita.bditac.ws.support.CategoriaResources;
import br.ita.bditac.ws.support.CriseResource;
import br.ita.bditac.ws.support.IndicadorResources;
import br.ita.bditac.ws.support.MessageResource;

/**
 *
 * = Testes unitários do serviço de Alertas para o BD-ITAC.
 *
 */
@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class APITests {
    
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
                getFoto());

        ResponseEntity<MessageResource> criseResponseEntity =  getRestTemplate().postForEntity(criseURI, new HttpEntity<Crise>(criseRequest), MessageResource.class);

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
    public void test105GetAvisos() throws Exception {
        String alertaUrl = getBaseUrl() + "/rest/alerts";

        ResponseEntity<AvisoResources> resources = null;
        try {
        	resources = getRestTemplate().getForEntity(alertaUrl, AvisoResources.class);
        }
        catch(Exception ex) {
        	System.out.println(ex.getMessage());
        }
        
        assertThat(resources.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void test107GetAlertasByCoords() throws Exception {
        String alertaUrl = getBaseUrl() + "/rest/avisos/nearbyWarnings";

        DecimalFormat df=new DecimalFormat("#0.000000");
        SimpleDateFormat tf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.US));
        UriComponentsBuilder uriComponentsBuilder=UriComponentsBuilder.fromHttpUrl(alertaUrl).
                queryParam("timestamp", "'" + tf.format(0) + "'").
                queryParam("latitude", df.format(-23)).
                queryParam("longitude", df.format(-45)).
                queryParam("raio", df.format(10));
        ResponseEntity<AlertaResources> resources = null;
        try {
        	resources = getRestTemplate().getForEntity(uriComponentsBuilder.build().encode().toUri(), AlertaResources.class);
        }
        catch(Exception ex) {
        	System.out.println(ex.getMessage());
        }
        
        assertThat(resources.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Ignore
    public void test108GetAlertaByCoordsInexistente() throws Exception {
        String alertaUrl = getBaseUrl() + "/rest/avisos/nearbyWarnings";

        DecimalFormat df=new DecimalFormat("#0.000000");
        SimpleDateFormat tf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.US));
        UriComponentsBuilder uriComponentsBuilder=UriComponentsBuilder.fromHttpUrl(alertaUrl).
                queryParam("timestamp", "'" + tf.format(0) + "'").
                queryParam("latitude", df.format(0.6)).
                queryParam("longitude", df.format(0.6)).
                queryParam("raio", df.format(1));
        ResponseEntity<AlertaResources> resources = getRestTemplate().getForEntity(uriComponentsBuilder.build().encode().toUri(), AlertaResources.class);

        assertThat(resources.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
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
	   
	   assertThat(indicadores.get(1).getDescricao()).isEqualTo("Finalizado Não Confirmado");
   }
   
}
