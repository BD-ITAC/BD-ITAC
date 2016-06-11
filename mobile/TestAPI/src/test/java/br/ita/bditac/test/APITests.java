package br.ita.bditac.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
import br.ita.bditac.model.Crise;
import br.ita.bditac.ws.support.AlertaResource;
import br.ita.bditac.ws.support.AlertaResources;
import br.ita.bditac.ws.support.AlternateMessageResource;
import br.ita.bditac.ws.support.CriseResource;

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
        converter.setSupportedMediaTypes(Arrays.asList(MediaTypes.HAL_JSON));
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

    /**
     *
     * = TS02-US09
     * 
     * == Asserção:
     *
     * Testa a inclusão de um crise de crise usando o seriço de Alertas.
     *
     * == Dados:
     *
     * Uma estrutura de dados contendo o crise.
     *
     * === Estrutura de dados
     *
     * [source,java]
     * --
     *  Crise criseRequest = new Crise(
     *          "Deslizamento na na favela do Paraiso",
     *          0,
     *          "Ze das Couves",
     *          "zedascouves@gmail.com",
     *          "(12) 99876-1234");
     * --
     *
     * == Execução:
     *
     * Uma chamada ao serviço de Alertas.
     *
     * ==Resultado esperado:
     *
     * Uma estrutura de dados com a mesma informação da estrutura enviada.
     *
     * === Estrutura de dados
     *
     * [source,java]
     * --
     *  Crise criseRequest = new Crise(
     *          "Deslizamento na na favela do Paraiso",
     *          0,
     *          "Ze das Couves",
     *          "zedascouves@gmail.com",
     *          "(12) 99876-1234",
     *          40.0,
     *          50.0
     *          foto);
     * --
     *
     */
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

    /**
     *
     * = TS02-US09
     * 
     * == Asserção:
     *
     * Testa a inclusão de um crise de crise usando o seriço de Alertas:
     *
     * == Dados:
     *
     * Identificação da crise na requisição.
     *
     * == Execução:
     *
     * Uma chamada ao serviço de Alertas.
     *
     * == Resultado esperado:
     *
     * O código de estado da chamada deve ser o código HTTP 404 (NOT FOUND).
     *
     */
    @Test
    public void test103GetCriseInexistente() throws Exception {
        String criseURL = getBaseUrl() + "/rest/avisos/{id}";

        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("id", 100);

        ResponseEntity<CriseResource> criseResponseEntity = getRestTemplate().getForEntity(criseURL, CriseResource.class, params);

        assertThat(criseResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     *
     * = TS02-US09
     * 
     * == Asserção:
     *
     * Testa a obtenção de alertas dentro de uma área identificada por um ponto geográfico e um raio no seriço de Alertas:
     *
     * == Dados:
     *
     * Identificação da área de pesquisa desejada.
     *
     * === Dados informados:
     *
     * [source,java]
     * --
     *  Map<String, Double> params = new HashMap<String, Double>();
     *  params.put("latitude", 40.0);
     *  params.put("longitude", 50.0);
     *  params.put("raio", 1D);
     * --
     *
     * == Execução:
     *
     * Uma chamada ao serviço de Alertas.
     *
     * == Resultado esperado:
     *
     * Uma lista de dados com os dados dos alertas na área informada.
     *
     * === Estrutura de dados
     *
     * [source,java]
     * --
     *  Alerta alertaRequest = new Alerta(
     *          "Alerta de deslizamento",
     *          "Perigo de deslizamento na altura do Km 20 da rodovia Tamoios, pista Sao Jose dos Campos/Litoral",
     *          5,
     *          5,
     *          0,
     *          40.0,
     *          50.0,
     *          1.0);
     * --
     *
     */
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

    /**
     *
     * = TS02-US09
     * 
     * == Asserção:
     *
     * Testa a obtenção de alertas dentro de uma área identificada por um ponto geográfico e um raio no seriço de Alertas onde não há alertas:
     *
     * == Dados:
     *
     * Identificação da área de pesquisa desejada.
     *
     * === Dados informados:
     *
     * [source,java]
     * --
     *  Map<String, Double> params = new HashMap<String, Double>();
     *  params.put("latitude", 0.6D);
     *  params.put("longitude", 0.6D);
     *  params.put("raio", 1D);
     * --
     *
     * == Execução:
     *
     * Uma chamada ao serviço de Alertas.
     *
     * == Resultado esperado:
     *
     * Uma lista *vazia* de dados com os dados dos alertas na área informada.
     *
     */
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
    
    /**
     *
     * = TS02-US09
     * 
     * == Asserção:
     *
     * Testa a inclusão de um crise de crise usando o seriço de Alertas.
     * Verificar se foi gerado um alerta com base nos dados da crise recém inserido.
     * Observar que em casos reais, existe uma demora significativa entre o cadastramento
     * da crise e a geração do alerta de crise.
     *
     * == Dados:
     *
     * Uma estrutura de dados contendo o crise.
     *
     * === Estrutura de dados
     *
     * [source,java]
     * --
     *  Crise criseRequest = new Crise(
     *          "Deslizamento na na favela do Paraiso",
     *          0,
     *          "Ze das Couves",
     *          "zedascouves@gmail.com",
     *          "(12) 99876-1234");
     * --
     *
     * == Execução:
     *
     * Uma chamada ao serviço de Alertas.
     *
     * ==Resultado esperado:
     *
     * Uma estrutura de dados com a mesma informação da estrutura enviada.
     *
     * === Estrutura de dados
     *
     * [source,java]
     * --
     *  Crise criseRequest = new Crise(
     *          "Deslizamento na na favela do Paraiso",
     *          0,
     *          "Ze das Couves",
     *          "zedascouves@gmail.com",
     *          "(12) 99876-1234",
     *          40.0,
     *          50.0,
     *          foto);
     * --
     *
     */
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

}
