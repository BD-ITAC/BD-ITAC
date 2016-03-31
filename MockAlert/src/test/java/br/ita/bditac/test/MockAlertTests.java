package br.ita.bditac.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.junit.Rule;
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
import org.springframework.restdocs.RestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
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
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.ita.bditac.app.Application;
import br.ita.bditac.model.Alerta;
import br.ita.bditac.model.Evento;
import br.ita.bditac.support.AlertaResource;
import br.ita.bditac.support.AlertaResources;
import br.ita.bditac.support.EventoResource;

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
    
    @Rule
    public RestDocumentation restDocumentation = new RestDocumentation("target/generated-docs");
    
    protected MockMvc mvc;

    protected String getBaseUrl() {
        return "http://localhost:" + port;
    }

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(documentationConfiguration(this.restDocumentation)).build();
    }

    @Test
    public void _000BootstrapsWebApp() {
        assertNotNull(mvc);
    }
    
    @Test 
    public void test101PostEvento() throws Exception {
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
            ResponseEntity<EventoResource> eventoResponseEntity =  restTemplate.postForEntity(eventoURI, new HttpEntity<Evento>(eventoRequest), EventoResource.class);
            
            assertThat(eventoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            
            Evento eventoResponse = eventoResponseEntity.getBody().getContent();
            
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
    public void test102GetEvento() throws Exception {
        String eventoURL = getBaseUrl() + "/evento/{id}";
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaTypes.HAL_JSON));
        converter.setObjectMapper(mapper);
        
        RestTemplate restTemplate = new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));
        
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("id", 1);
        
        ResponseEntity<EventoResource> eventoResponseEntity = restTemplate.getForEntity(eventoURL, EventoResource.class, params);
        
        assertThat(eventoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        Evento eventoResponse = eventoResponseEntity.getBody().getContent();
        
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
    public void test103GetEventoInexistente() throws Exception {
        String eventoURL = getBaseUrl() + "/evento/{id}";
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaTypes.HAL_JSON));
        converter.setObjectMapper(mapper);
        
        RestTemplate restTemplate = new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));
        
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("id", 1);
        
        ResponseEntity<EventoResource> eventoResponseEntity = restTemplate.getForEntity(eventoURL, EventoResource.class, params);
        
        assertThat(eventoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        Evento eventoResponse = eventoResponseEntity.getBody().getContent();
        
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
    public void test104PostAlerta() throws URISyntaxException {
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
            ResponseEntity<AlertaResource> alertaResponseEntity = restTemplate.postForEntity(alertaURI, new HttpEntity<Alerta>(alertaRequest), AlertaResource.class);
            
            assertThat(alertaResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

            Alerta alertasResponse = alertaResponseEntity.getBody().getContent();
            
            assertEquals("Resposta(descricaoResumida)'" + alertasResponse.getDescricaoResumida() + "' do POST diferente do que foi enviado: '" + alertaRequest.getDescricaoResumida() + "'!", alertasResponse.getDescricaoResumida(), alertaRequest.getDescricaoResumida());
            assertEquals("Resposta(descricaoCompleta)'" + alertasResponse.getDescricaoCompleta() + "' do POST diferente do que foi enviado'" + alertaRequest.getDescricaoCompleta() + "'!", alertasResponse.getDescricaoCompleta(), alertaRequest.getDescricaoCompleta());
            assertEquals("Resposta(fatorRiscoHumano)'" + alertasResponse.getFatorRiscoHumano() + "' do POST diferente do que foi enviado'" + alertaRequest.getFatorRiscoHumano() + "'!", alertasResponse.getFatorRiscoHumano(), alertaRequest.getFatorRiscoHumano());
            assertEquals("Resposta(fatorRiscoMaterial)'" + alertasResponse.getFatorRiscoMaterial() + "' do POST diferente do que foi enviado'" + alertaRequest.getFatorRiscoMaterial() + "'!", alertasResponse.getFatorRiscoMaterial(), alertaRequest.getFatorRiscoMaterial());
            assertEquals("Resposta(categoriaAlerta)'" + alertasResponse.getCategoriaAlerta() + "' do POST diferente do que foi enviado'" + alertaRequest.getCategoriaAlerta() + "'!", alertasResponse.getCategoriaAlerta(), alertaRequest.getCategoriaAlerta());
            assertEquals("Resposta(origemLatitude)'" + alertasResponse.getOrigemLatitude() + "' do POST diferente do que foi enviado'" + alertaRequest.getOrigemLatitude() + "'!", alertasResponse.getOrigemLatitude(), alertaRequest.getOrigemLatitude(), DELTA);
            assertEquals("Resposta(origemLongitude)'" + alertasResponse.getOrigemLongitude() + "' do POST diferente do que foi enviado'" + alertaRequest.getOrigemLongitude() + "'!", alertasResponse.getOrigemLongitude(), alertaRequest.getOrigemLongitude(), DELTA);
            assertEquals("Resposta(origemRaioKms)'" + alertasResponse.getOrigemRaioKms() + "' do POST diferente do que foi enviado'" + alertaRequest.getOrigemRaioKms() + "'!", alertasResponse.getOrigemRaioKms(), alertaRequest.getOrigemRaioKms(), DELTA);
            assertEquals("Resposta(destinoLatitude)'" + alertasResponse.getDestinoLatitude() + "' do POST diferente do que foi enviado'" + alertaRequest.getDestinoLatitude() + "'!", alertasResponse.getDestinoLatitude(), alertaRequest.getDestinoLatitude(), DELTA);
            assertEquals("Resposta(destinoLongitude)'" + alertasResponse.getDestinoLongitude() + "' do POST diferente do que foi enviado'" + alertaRequest.getDestinoLongitude() + "'!", alertasResponse.getDestinoLongitude(), alertaRequest.getDestinoLongitude(), DELTA);
            assertEquals("Resposta(destinoRaio)'" + alertasResponse.getDestinoRaioKms() + "' do POST diferente do que foi enviado'" + alertaRequest.getDestinoRaioKms() + "'!", alertasResponse.getDestinoRaioKms(), alertaRequest.getDestinoRaioKms(), DELTA);
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
    public void test105GetAlertaById() throws URISyntaxException {
        String alertaURL = getBaseUrl() + "/alerta/{id}";
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaTypes.HAL_JSON));
        converter.setObjectMapper(mapper);
        
        RestTemplate restTemplate = new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));
        
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("id", 1);
        
        ResponseEntity<AlertaResource> alertaResponseEntity = restTemplate.getForEntity(alertaURL, AlertaResource.class, params);
        
        assertThat(alertaResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        Alerta alertaResponse = alertaResponseEntity.getBody().getContent();
        
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
    
    @Test
    public void test106GetAlertasByCoords() throws Exception {
        String alertaURL = getBaseUrl() + "/alerta/latitude/{latitude}/longitude/{longitude}/raio/{raio}";
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaTypes.HAL_JSON));
        converter.setObjectMapper(mapper);
        
        RestTemplate restTemplate = new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));
        
        Map<String, Double> params = new HashMap<String, Double>();
        params.put("latitude", 0.5D);
        params.put("longitude", 0.5D);
        params.put("raio", 1D);
        
        List<Alerta> alertas = restTemplate.getForObject(alertaURL, AlertaResources.class, params).unwrap();

        assertEquals("Resposta(descricaoResumida)'" + alertas.get(0).getDescricaoResumida() + "' do POST diferente do esperado!", alertas.get(0).getDescricaoResumida(), "Alerta de deslizamento");
    }
    
    @Test
    public void test107GetAlertaByCoordsInexistente() throws Exception {
        String alertaURL = getBaseUrl() + "/alerta/latitude/{latitude}/longitude/{longitude}/raio/{raio}";
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaTypes.HAL_JSON));
        converter.setObjectMapper(mapper);
        
        RestTemplate restTemplate = new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));
        
        Map<String, Double> params = new HashMap<String, Double>();
        params.put("latitude", 0.6D);
        params.put("longitude", 0.6D);
        params.put("raio", 1D);
        
        List<Alerta> alertas = restTemplate.getForObject(alertaURL, AlertaResources.class, params).unwrap();

        assertThat(alertas.size() == 0);
    }
    
    @Test
    public void test901PostEvento() throws Exception {
        List<String> endereco = new ArrayList<String>();
        endereco.add("rua das Casas");
        endereco.add("numero das Portas");
        Evento evento = new Evento(
            "Evento de teste",
            0,
            "João da Horta",
            "joao.horta@gmail.com",
            "(12) 95678-4321",
            endereco);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String eventoJson = writer.writeValueAsString(evento);
        
        this.mvc.perform(post(getBaseUrl() + "/evento")
            .contentType(MediaTypes.HAL_JSON_VALUE)
            .content(eventoJson))
            .andExpect(status().isCreated())
            .andDo(document("evento/post",
                requestFields(
                    fieldWithPath("descricao").type(JsonFieldType.STRING).description("Descrição do evento"),
                    fieldWithPath("categoria").type(JsonFieldType.NULL).description("Categoria do evento"),
                    fieldWithPath("nome").type(JsonFieldType.STRING).description("Nome do informante do evento"),
                    fieldWithPath("email").type(JsonFieldType.STRING).description("Email do informante do evento"),
                    fieldWithPath("telefone").type(JsonFieldType.STRING).description("Telefone do informante do evento"),
                    fieldWithPath("endereco").type(JsonFieldType.ARRAY).description("Uma ou mais linhas com o endereço ou localização aproximada do evento"))));
    }
    
    @Test
    public void test902GetEvento() throws Exception {
        this.mvc.perform(get(getBaseUrl() + "/evento/{id}", 1))
            .andExpect(status().isOk())
            .andDo(document("evento/locations", 
                pathParameters(parameterWithName("id").description("Identificação do evento"))));
        this.mvc.perform(get(getBaseUrl() + "/evento/1")
            .accept(MediaTypes.HAL_JSON_VALUE))
            .andExpect(status().isOk())
            .andDo(document("evento/get", 
                responseFields(
                    fieldWithPath("descricao").type(JsonFieldType.STRING).description("Descrição do evento"),
                    fieldWithPath("categoria").type(JsonFieldType.NULL).description("Categoria do evento"),
                    fieldWithPath("nome").type(JsonFieldType.STRING).description("Nome do informante do evento"),
                    fieldWithPath("email").type(JsonFieldType.STRING).description("Email do informante do evento"),
                    fieldWithPath("telefone").type(JsonFieldType.STRING).description("Telefone do informante do evento"),
                    fieldWithPath("endereco").type(JsonFieldType.ARRAY).description("Uma ou mais linhas com o endereço ou localização aproximada do evento"),
                    fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("URI do link para o evento")),
                links(
                    linkWithRel("self").description("Link para o evento"))));
    }
    
    @Test
    public void test904PostAlerta() throws Exception {
        List<String> endereco = new ArrayList<String>();
        endereco.add("rua das Casas");
        endereco.add("numero das Portas");
        Alerta alerta = new Alerta(
            "Alerta de teste",
            "Teste de alerta para verificar a funcionalidade do sistema",
            5,
            5,
            0,
            50.0D,
            50.0D,
            1.0D,
            50.0D,
            50.0D,
            1.0D,
            endereco);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String alertaJson = writer.writeValueAsString(alerta);
        
        this.mvc.perform(post(getBaseUrl() + "/alerta")
            .contentType(MediaTypes.HAL_JSON_VALUE)
            .content(alertaJson))
            .andExpect(status().isCreated())
            .andDo(document("alerta/post", 
                requestFields(
                    fieldWithPath("descricaoResumida").type(JsonFieldType.STRING).description("Breve descrição do alerta"),
                    fieldWithPath("descricaoCompleta").type(JsonFieldType.STRING).description("Descrição detalhada do alerta"),
                    fieldWithPath("fatorRiscoHumano").type(JsonFieldType.STRING).description("Fator de risco para a vida humana"),
                    fieldWithPath("fatorRiscoMaterial").type(JsonFieldType.STRING).description("Fator de risco para instalações e equipamentos"),
                    fieldWithPath("categoriaAlerta").type(JsonFieldType.STRING).description("Indica o tipo de alerta"),
                    fieldWithPath("origemLatitude").type(JsonFieldType.ARRAY).description("Latitude do ponto de origem do alerta"),
                    fieldWithPath("origemLongitude").type(JsonFieldType.ARRAY).description("Longitude do ponto de origem do alerta"),
                    fieldWithPath("origemRaioKms").type(JsonFieldType.ARRAY).description("Área de abrangência do alerta em Kms"),
                    fieldWithPath("destinoLatitude").type(JsonFieldType.ARRAY).description("Latitude do destino presumido do evento (se houver, senão replica a da origem)"),
                    fieldWithPath("destinoLongitude").type(JsonFieldType.ARRAY).description("Longitude do destino presumido do evento (se houver, senão replica a da origem)"),
                    fieldWithPath("destinoRaioKms").type(JsonFieldType.ARRAY).description("Área de abrangência em Kms (se houver, senão replica a da a origem)"),
                    fieldWithPath("endereco").type(JsonFieldType.ARRAY).description("Uma ou mais linhas com o endereço ou localização aproximada do ponto de origem do alerta"))));
    }
    
    @Test
    public void test905GetAlertaById() throws Exception {
        this.mvc.perform(get(getBaseUrl() + "/alerta/{id}", 1))
            .andExpect(status().isOk())
            .andDo(document("alerta/locations", 
                pathParameters(parameterWithName("id").description("Identificação do alerta"))));
        this.mvc.perform(get(getBaseUrl() + "/alerta/1")
            .accept(MediaTypes.HAL_JSON_VALUE))
            .andExpect(status().isOk())
            .andDo(document("alerta/get", 
                responseFields(
                    fieldWithPath("descricaoResumida").type(JsonFieldType.STRING).description("Breve descrição do alerta"),
                    fieldWithPath("descricaoCompleta").type(JsonFieldType.STRING).description("Descrição detalhada do alerta"),
                    fieldWithPath("fatorRiscoHumano").type(JsonFieldType.STRING).description("Fator de risco para a vida humana"),
                    fieldWithPath("fatorRiscoMaterial").type(JsonFieldType.STRING).description("Fator de risco para instalações e equipamentos"),
                    fieldWithPath("categoriaAlerta").type(JsonFieldType.STRING).description("Indica o tipo de alerta"),
                    fieldWithPath("origemLatitude").type(JsonFieldType.ARRAY).description("Latitude do ponto de origem do alerta"),
                    fieldWithPath("origemLongitude").type(JsonFieldType.ARRAY).description("Longitude do ponto de origem do alerta"),
                    fieldWithPath("origemRaioKms").type(JsonFieldType.ARRAY).description("Área de abrangência do alerta em Kms"),
                    fieldWithPath("destinoLatitude").type(JsonFieldType.ARRAY).description("Latitude do destino presumido do evento (se houver, senão replica a da origem)"),
                    fieldWithPath("destinoLongitude").type(JsonFieldType.ARRAY).description("Longitude do destino presumido do evento (se houver, senão replica a da origem)"),
                    fieldWithPath("destinoRaioKms").type(JsonFieldType.ARRAY).description("Área de abrangência em Kms (se houver, senão replica a da a origem)"),
                    fieldWithPath("endereco").type(JsonFieldType.ARRAY).description("Uma ou mais linhas com o endereço ou localização aproximada do ponto de origem do alerta"),
                    fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("URI do link para o alerta")),
                links(
                    linkWithRel("self").description("Link para o evento"))));
    }
   
}
