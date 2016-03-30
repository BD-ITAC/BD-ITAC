package br.ita.bditac.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ita.bditac.app.Application;
import br.ita.bditac.model.Alerta;
import br.ita.bditac.model.AlertaResponse;
import br.ita.bditac.model.Evento;
import br.ita.bditac.model.EventoResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@IntegrationTest("server.port:0")
@WebAppConfiguration
@ActiveProfiles("hateoas")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MockAlertTests {

    private static final double DELTA = 1e-6;
    
    @Value("${local.server.port}")
    private int port;

    @Autowired
    WebApplicationContext context;

    protected MockMvc mvc;

    protected String getBaseUrl() {
        return "http://localhost:" + port;
    }

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void test00BootstrapsWebApp() {
        assertNotNull(mvc);
    }
    
    @Test 
    public void test01PostEvento() throws Exception {
        URI eventoURI = new URI(getBaseUrl() + "/evento");
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaTypes.HAL_JSON));
        converter.setObjectMapper(mapper);
      
        RestTemplate restTemplate = new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));
        
        List<String> endereco = new ArrayList<String>();
        endereco.add("rua das Casas");
        endereco.add("numero das Portas");
        Evento eventoRequest = new Evento(
                "Deslizamento na na favela do Paraiso",
                0,
                "Ze das Couves",
                "zedascouves@gmail.com",
                "(12) 99876-1234",
                endereco);
        try {
            HttpEntity<Evento> eventoRequestEntity = new HttpEntity<Evento>(eventoRequest);
            ResponseEntity<EventoResponse> eventoResponseEntity =  restTemplate.postForEntity(eventoURI, eventoRequestEntity, EventoResponse.class);
            
            assertThat(eventoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            
            Evento eventoResponse = eventoResponseEntity.getBody().getEvento();
            
            assertEquals("Resposta(descricao):'" + eventoResponse.getDescricao() + "' do POST diferente do que foi enviado: '" + eventoRequest.getDescricao() + "'!", eventoResponse.getDescricao(), eventoRequest.getDescricao());
            assertEquals("Resposta(categoria) do POST diferente do que foi enviado!", eventoResponse.getCategoria(), eventoRequest.getCategoria());
            assertEquals("Resposta(nome) do POST diferente do que foi enviado!", eventoResponse.getNome(), eventoRequest.getNome());
            assertEquals("Resposta(email) do POST diferente do que foi enviado!", eventoResponse.getEmail(), eventoRequest.getEmail());
            assertEquals("Resposta(telefone) do POST diferente do que foi enviado!", eventoResponse.getTelefone(), eventoRequest.getTelefone());
            int linha = 0;
            for(String linhaEndereco : eventoRequest.getEndereco()) {
                assertEquals("Resposta(endereco) do POST diferente do que foi enviado!", linhaEndereco, eventoRequest.getEndereco().get(linha++));
            }
        }
        catch(ResourceAccessException raex) {
            fail("Servico web '" + getBaseUrl() + "' não esta sendo executado!");
        }
    }
    
    @Test
    public void test02GetEvento() throws Exception {
        String eventoURL = getBaseUrl() + "/evento/{id}";
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaTypes.HAL_JSON));
        converter.setObjectMapper(mapper);
        
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("id", 1);
        
        RestTemplate restTemplate = new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));
        
        ResponseEntity<EventoResponse> eventoResponseEntity = restTemplate.getForEntity(eventoURL, EventoResponse.class, params);
        
        assertThat(eventoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        Evento eventoResponse = eventoResponseEntity.getBody().getEvento();
        
        List<String> endereco = new ArrayList<String>();
        endereco.add("rua das Casas");
        endereco.add("numero das Portas");
        Evento eventoRequest = new Evento(
                "Deslizamento na na favela do Paraiso",
                0,
                "Ze das Couves",
                "zedascouves@gmail.com",
                "(12) 99876-1234",
                endereco);

        assertEquals("Resposta(descricao):'" + eventoResponse.getDescricao() + "' do POST diferente do que foi enviado: '" + eventoRequest.getDescricao() + "'!", eventoResponse.getDescricao(), eventoRequest.getDescricao());
        assertEquals("Resposta(categoria) do POST diferente do que foi enviado!", eventoResponse.getCategoria(), eventoRequest.getCategoria());
        assertEquals("Resposta(nome) do POST diferente do que foi enviado!", eventoResponse.getNome(), eventoRequest.getNome());
        assertEquals("Resposta(email) do POST diferente do que foi enviado!", eventoResponse.getEmail(), eventoRequest.getEmail());
        assertEquals("Resposta(telefone) do POST diferente do que foi enviado!", eventoResponse.getTelefone(), eventoRequest.getTelefone());
        int linha = 0;
        for(String linhaEndereco : eventoRequest.getEndereco()) {
            assertEquals("Resposta(endereco) do POST diferente do que foi enviado!", linhaEndereco, eventoRequest.getEndereco().get(linha++));
        }
    }
    
    @Test
    public void test03POSTAlerta() throws URISyntaxException {
        URI alertaURI = new URI(getBaseUrl() + "/alerta");
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaTypes.HAL_JSON));
        converter.setObjectMapper(mapper);
        
        RestTemplate restTemplate = new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));
        
        List<String> endereco = new ArrayList<String>();
        endereco.add("rua das Casas");
        endereco.add("numero das Portas");
        Alerta alertaRequest = new Alerta(
                "Alerta de deslizamento",
                "Perigo de deslizamento na altura do Km 20 da rodovia Tamoios, pista Sao Jose dos Campos/Litoral",
                5,
                5,
                0,
                0.50,
                0.50,
                1.0,
                0.50,
                0.50,
                1.0,
                endereco);
        try {
            HttpEntity<Alerta> alertaRequestEntity = new HttpEntity<Alerta>(alertaRequest);
            ResponseEntity<AlertaResponse> alertaResponseEntity = restTemplate.postForEntity(alertaURI, alertaRequestEntity, AlertaResponse.class);
            
            assertThat(alertaResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

            Alerta alertaResponse = alertaResponseEntity.getBody().getAlerta();
            
            assertEquals("Resposta(descricaoResumida)'" + alertaResponse.getDescricaoResumida() + "' do POST diferente do que foi enviado: '" + alertaRequest.getDescricaoResumida() + "'!", alertaResponse.getDescricaoResumida(), alertaRequest.getDescricaoResumida());
            assertEquals("Resposta(descricaoCompleta)'" + alertaResponse.getDescricaoCompleta() + "' do POST diferente do que foi enviado'" + alertaRequest.getDescricaoCompleta() + "'!", alertaResponse.getDescricaoCompleta(), alertaRequest.getDescricaoCompleta());
            assertEquals("Resposta(fatorRiscoHumano)'" + alertaResponse.getFatorRiscoHumano() + "' do POST diferente do que foi enviado'" + alertaRequest.getFatorRiscoHumano() + "'!", alertaResponse.getFatorRiscoHumano(), alertaRequest.getFatorRiscoHumano());
            assertEquals("Resposta(fatorRiscoMaterial)'" + alertaResponse.getFatorRiscoMaterial() + "' do POST diferente do que foi enviado'" + alertaRequest.getFatorRiscoMaterial() + "'!", alertaResponse.getFatorRiscoMaterial(), alertaRequest.getFatorRiscoMaterial());
            assertEquals("Resposta(categoriaAlerta)'" + alertaResponse.getCategoriaAlerta() + "' do POST diferente do que foi enviado'" + alertaRequest.getCategoriaAlerta() + "'!", alertaResponse.getCategoriaAlerta(), alertaRequest.getCategoriaAlerta());
            assertEquals("Resposta(origemLatitude)'" + alertaResponse.getOrigemLatitude() + "' do POST diferente do que foi enviado'" + alertaRequest.getOrigemLatitude() + "'!", alertaResponse.getOrigemLatitude(), alertaRequest.getOrigemLatitude(), DELTA);
            assertEquals("Resposta(origemLongitude)'" + alertaResponse.getOrigemLongitude() + "' do POST diferente do que foi enviado'" + alertaRequest.getOrigemLongitude() + "'!", alertaResponse.getOrigemLongitude(), alertaRequest.getOrigemLongitude(), DELTA);
            assertEquals("Resposta(origemRaioKms)'" + alertaResponse.getOrigemRaioKms() + "' do POST diferente do que foi enviado'" + alertaRequest.getOrigemRaioKms() + "'!", alertaResponse.getOrigemRaioKms(), alertaRequest.getOrigemRaioKms(), DELTA);
            assertEquals("Resposta(destinoLatitude)'" + alertaResponse.getDestinoLatitude() + "' do POST diferente do que foi enviado'" + alertaRequest.getDestinoLatitude() + "'!", alertaResponse.getDestinoLatitude(), alertaRequest.getDestinoLatitude(), DELTA);
            assertEquals("Resposta(destinoLongitude)'" + alertaResponse.getDestinoLongitude() + "' do POST diferente do que foi enviado'" + alertaRequest.getDestinoLongitude() + "'!", alertaResponse.getDestinoLongitude(), alertaRequest.getDestinoLongitude(), DELTA);
            assertEquals("Resposta(destinoRaio)'" + alertaResponse.getDestinoRaioKms() + "' do POST diferente do que foi enviado'" + alertaRequest.getDestinoRaioKms() + "'!", alertaResponse.getDestinoRaioKms(), alertaRequest.getDestinoRaioKms(), DELTA);
            int linha = 0;
            for(String linhaEndereco : alertaRequest.getEndereco()) {
                assertEquals("Resposta(endereco)'" + " do POST diferente do que foi enviado'" + "'!", linhaEndereco, alertaRequest.getEndereco().get(linha++));
            }
        }
        catch(ResourceAccessException raex) {
            fail("Servico web '" + getBaseUrl() + "' não esta sendo executado!");
        }
    }
    
    @Test
    public void test04GETEvento() throws URISyntaxException {
        String alertaURL = getBaseUrl() + "/alerta/{id}";
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaTypes.HAL_JSON));
        converter.setObjectMapper(mapper);
        
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("id", 1);
        
        RestTemplate restTemplate = new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));
        
        ResponseEntity<AlertaResponse> alertaResponseEntity = restTemplate.getForEntity(alertaURL, AlertaResponse.class, params);
        
        assertThat(alertaResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        Alerta alertaResponse = alertaResponseEntity.getBody().getAlerta();
        
        List<String> endereco = new ArrayList<String>();
        endereco.add("rua das Casas");
        endereco.add("numero das Portas");
        Alerta alertaRequest = new Alerta(
                "Alerta de deslizamento",
                "Perigo de deslizamento na altura do Km 20 da rodovia Tamoios, pista Sao Jose dos Campos/Litoral",
                5,
                5,
                0,
                0.50,
                0.50,
                1.0,
                0.50,
                0.50,
                1.0,
                endereco);
        
        assertEquals("Resposta(descricaoResumida)'" + alertaResponse.getDescricaoResumida() + "' do POST diferente do que foi enviado: '" + alertaRequest.getDescricaoResumida() + "'!", alertaResponse.getDescricaoResumida(), alertaRequest.getDescricaoResumida());
        assertEquals("Resposta(descricaoCompleta)'" + alertaResponse.getDescricaoCompleta() + "' do POST diferente do que foi enviado'" + alertaRequest.getDescricaoCompleta() + "'!", alertaResponse.getDescricaoCompleta(), alertaRequest.getDescricaoCompleta());
        assertEquals("Resposta(fatorRiscoHumano)'" + alertaResponse.getFatorRiscoHumano() + "' do POST diferente do que foi enviado'" + alertaRequest.getFatorRiscoHumano() + "'!", alertaResponse.getFatorRiscoHumano(), alertaRequest.getFatorRiscoHumano());
        assertEquals("Resposta(fatorRiscoMaterial)'" + alertaResponse.getFatorRiscoMaterial() + "' do POST diferente do que foi enviado'" + alertaRequest.getFatorRiscoMaterial() + "'!", alertaResponse.getFatorRiscoMaterial(), alertaRequest.getFatorRiscoMaterial());
        assertEquals("Resposta(categoriaAlerta)'" + alertaResponse.getCategoriaAlerta() + "' do POST diferente do que foi enviado'" + alertaRequest.getCategoriaAlerta() + "'!", alertaResponse.getCategoriaAlerta(), alertaRequest.getCategoriaAlerta());
        assertEquals("Resposta(origemLatitude)'" + alertaResponse.getOrigemLatitude() + "' do POST diferente do que foi enviado'" + alertaRequest.getOrigemLatitude() + "'!", alertaResponse.getOrigemLatitude(), alertaRequest.getOrigemLatitude(), DELTA);
        assertEquals("Resposta(origemLongitude)'" + alertaResponse.getOrigemLongitude() + "' do POST diferente do que foi enviado'" + alertaRequest.getOrigemLongitude() + "'!", alertaResponse.getOrigemLongitude(), alertaRequest.getOrigemLongitude(), DELTA);
        assertEquals("Resposta(origemRaioKms)'" + alertaResponse.getOrigemRaioKms() + "' do POST diferente do que foi enviado'" + alertaRequest.getOrigemRaioKms() + "'!", alertaResponse.getOrigemRaioKms(), alertaRequest.getOrigemRaioKms(), DELTA);
        assertEquals("Resposta(destinoLatitude)'" + alertaResponse.getDestinoLatitude() + "' do POST diferente do que foi enviado'" + alertaRequest.getDestinoLatitude() + "'!", alertaResponse.getDestinoLatitude(), alertaRequest.getDestinoLatitude(), DELTA);
        assertEquals("Resposta(destinoLongitude)'" + alertaResponse.getDestinoLongitude() + "' do POST diferente do que foi enviado'" + alertaRequest.getDestinoLongitude() + "'!", alertaResponse.getDestinoLongitude(), alertaRequest.getDestinoLongitude(), DELTA);
        assertEquals("Resposta(destinoRaio)'" + alertaResponse.getDestinoRaioKms() + "' do POST diferente do que foi enviado'" + alertaRequest.getDestinoRaioKms() + "'!", alertaResponse.getDestinoRaioKms(), alertaRequest.getDestinoRaioKms(), DELTA);
        int linha = 0;
        for(String linhaEndereco : alertaRequest.getEndereco()) {
            assertEquals("Resposta(endereco)'" + " do POST diferente do que foi enviado'" + "'!", linhaEndereco, alertaRequest.getEndereco().get(linha++));
        }
    }
   
}
