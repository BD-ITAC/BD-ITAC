package br.ita.bditac.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.ita.bditac.app.Application;
import br.ita.bditac.model.Alerta;
import br.ita.bditac.model.Evento;
import br.ita.bditac.model.Indicadores;
import br.ita.bditac.support.AlertaResource;
import br.ita.bditac.support.AlertaResources;
import br.ita.bditac.support.EventoResource;
import br.ita.bditac.support.IndicadoresResource;

/**
 *
 * = Testes unitários do serviço de Alertas para o BD-ITAC.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@IntegrationTest("server.port:0")
@WebAppConfiguration
@ActiveProfiles("hateoas")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MockAlertTests {

    private static final double DELTA = 1e-6;

    private static final String IND_CADASTRADO = "Cadastrados";

    private static final String IND_FINALIZADOS = "Finalizados";

    private static final String IND_ABERTOS = "Em andamento";

    public class ClientErrorHandler implements ResponseErrorHandler {

        public void handleError(ClientHttpResponse response) throws IOException {

        }

        public boolean hasError(ClientHttpResponse response) throws IOException {
            if ((response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) || (response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR)) {
                return true;
            }

            return false;
        }

    }

    public RestTemplate getRestTemplate() {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaTypes.HAL_JSON));
        converter.setObjectMapper(mapper);

        RestTemplate restTemplate = new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));
        restTemplate.setErrorHandler(new ClientErrorHandler());

        return restTemplate;

    }

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

    /**
     *
     * = TS02-US09
     * 
     * == Asserção:
     *
     * Testa a inclusão de um evento de crise usando o seriço de Alertas.
     *
     * == Dados:
     *
     * Uma estrutura de dados contendo o evento.
     *
     * === Estrutura de dados
     *
     * [source,java]
     * --
     *  Evento eventoRequest = new Evento(
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
     *  Evento eventoRequest = new Evento(
     *          "Deslizamento na na favela do Paraiso",
     *          0,
     *          "Ze das Couves",
     *          "zedascouves@gmail.com",
     *          "(12) 99876-1234",
     *          40.0,
     *          50.0);
     * --
     *
     */
    @Test
    public void test101PostEventoGerarAlerta() throws Exception {
        URI eventoURI = new URI(getBaseUrl() + "/evento");

        Evento eventoRequest = new Evento(
                "Deslizamento na na favela do Paraiso",
                0,
                "Ze das Couves",
                "zedascouves@gmail.com",
                "(12) 99876-1234",
                40.0,
                50.0);

        ResponseEntity<EventoResource> eventoResponseEntity =  getRestTemplate().postForEntity(eventoURI, new HttpEntity<Evento>(eventoRequest), EventoResource.class);

        assertThat(eventoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Evento eventoResponse = eventoResponseEntity.getBody().getContent();

        assertEquals("Resposta(descricao):'" + eventoResponse.getDescricao() + "' do POST diferente do que foi enviado: '" + eventoRequest.getDescricao() + "'!", eventoResponse.getDescricao(), eventoRequest.getDescricao());
        assertEquals("Resposta(categoria) do POST diferente do que foi enviado!", eventoResponse.getCategoria(), eventoRequest.getCategoria());
        assertEquals("Resposta(nome) do POST diferente do que foi enviado!", eventoResponse.getNome(), eventoRequest.getNome());
        assertEquals("Resposta(email) do POST diferente do que foi enviado!", eventoResponse.getEmail(), eventoRequest.getEmail());
        assertEquals("Resposta(telefone) do POST diferente do que foi enviado!", eventoResponse.getTelefone(), eventoRequest.getTelefone());        
    }

    /**
     *
     * = TS02-US09
     * 
     * == Asserção:
     *
     * Testa a obtenção de um evento de crise do seriço de Alertas:
     *
     * == Dados:
     *
     * Identificação do evento na requisição.
     *
     * == Execução:
     *
     * Uma chamada ao serviço de Alertas.
     *
     * == Resultado esperado:
     *
     * Uma estrutura de dados com os dados do evento.
     *
     * === Estrutura de dados
     *
     * [source,java]
     * --
     *  Evento eventoRequest = new Evento(
     *          "Deslizamento na na favela do Paraiso",
     *          0,
     *          "Ze das Couves",
     *          "zedascouves@gmail.com",
     *          "(12) 99876-1234",
     *          40.0,
     *          50.0);
     * --
     *
     */
    @Test
    public void test102GetEvento() throws Exception {
        String eventoURL = getBaseUrl() + "/evento/{id}";

        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("id", 1);

        ResponseEntity<EventoResource> eventoResponseEntity = getRestTemplate().getForEntity(eventoURL, EventoResource.class, params);

        assertThat(eventoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        Evento eventoResponse = eventoResponseEntity.getBody().getContent();

        Evento eventoRequest = new Evento(
                "Deslizamento na na favela do Paraiso",
                0,
                "Ze das Couves",
                "zedascouves@gmail.com",
                "(12) 99876-1234",
                40.0,
                50.0);

        assertEquals("Resposta(descricao):'" + eventoResponse.getDescricao() + "' do POST diferente do que foi enviado: '" + eventoRequest.getDescricao() + "'!", eventoResponse.getDescricao(), eventoRequest.getDescricao());
        assertEquals("Resposta(categoria) do POST diferente do que foi enviado!", eventoResponse.getCategoria(), eventoRequest.getCategoria());
        assertEquals("Resposta(nome) do POST diferente do que foi enviado!", eventoResponse.getNome(), eventoRequest.getNome());
        assertEquals("Resposta(email) do POST diferente do que foi enviado!", eventoResponse.getEmail(), eventoRequest.getEmail());
        assertEquals("Resposta(telefone) do POST diferente do que foi enviado!", eventoResponse.getTelefone(), eventoRequest.getTelefone());
        assertEquals("Resposta(latitude) do POST diferente do que foi enviado!", eventoResponse.getLatitude(), eventoRequest.getLatitude(), DELTA);
        assertEquals("Resposta(longitude) do POST diferente do que foi enviado!", eventoResponse.getLongitude(), eventoRequest.getLongitude(), DELTA);
    }

    /**
     *
     * = TS02-US09
     * 
     * == Asserção:
     *
     * Testa a inclusão de um evento de crise usando o seriço de Alertas:
     *
     * == Dados:
     *
     * Identificação do evento na requisição.
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
    public void test103GetEventoInexistente() throws Exception {
        String eventoURL = getBaseUrl() + "/evento/{id}";

        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("id", 100);

        ResponseEntity<EventoResource> eventoResponseEntity = getRestTemplate().getForEntity(eventoURL, EventoResource.class, params);

        assertThat(eventoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     *
     * = TS02-US09
     * 
     * == Asserção:
     *
     * Testa a inclusão de um alerta utilizando o serviço de Alertas.
     *
     * == Dados:
     *
     * Uma estrutura de dados com os dados do alerta.
     *
     * === Estrutura de Dados
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
     * == Execução:
     *
     * Uma chamada ao serviço de de Alertas
     *
     * == Resultado esperado:
     *
     * Uma estrutura de dados com a mesma informação da estrutura enviada.
     *
     * === Estrutura de Dados
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
    public void test104PostAlerta() throws URISyntaxException {
        URI alertaURI = new URI(getBaseUrl() + "/alerta");

        Alerta alertaRequest = new Alerta(
                "Alerta de deslizamento",
                "Perigo de deslizamento na altura do Km 20 da rodovia Tamoios, pista Sao Jose dos Campos/Litoral",
                5,
                5,
                0,
                40.0,
                50.0,
                1.0);

        ResponseEntity<AlertaResource> alertaResponseEntity = getRestTemplate().postForEntity(alertaURI, new HttpEntity<Alerta>(alertaRequest), AlertaResource.class);

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
    }

    /**
     *
     * = TS02-US09
     * 
     * == Asserção:
     *
     * Testa a obtenção de um alerta de crise do seriço de Alertas:
     *
     * == Dados:
     *
     * Identificação do alerta na requisição.
     *
     * == Execução:
     *
     * Uma chamada ao serviço de Alertas.
     *
     * == Resultado esperado:
     *
     * Uma estrutura de dados com os dados do alerta.
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
    public void test105GetAlerta() throws URISyntaxException {
        String alertaURL = getBaseUrl() + "/alerta";

        ResponseEntity<AlertaResource> alertaResponseEntity = getRestTemplate().getForEntity(alertaURL, AlertaResource.class);

        assertThat(alertaResponseEntity.getStatusCode() == HttpStatus.OK);
    }

    /**
     *
     * = TS02-US09
     * 
     * == Asserção:
     *
     * Testa a obtenção de um determinado alerta identificado pelo *id* do seriço de Alertas:
     *
     * == Dados:
     *
     * Identificação do alerta na requisição.
     *
     * == Execução:
     *
     * Uma chamada ao serviço de Alertas.
     *
     * == Resultado esperado:
     *
     * Uma estrutura de dados com os dados do alerta.
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
    public void test106GetAlertaById() throws URISyntaxException {
        String alertaURL = getBaseUrl() + "/alerta/{id}";

        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("id", 1);

        ResponseEntity<AlertaResource> alertaResponseEntity = getRestTemplate().getForEntity(alertaURL, AlertaResource.class, params);

        assertThat(alertaResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        Alerta alertaResponse = alertaResponseEntity.getBody().getContent();

        Alerta alertaRequest = new Alerta(
                "Alagamento",
                "Deslizamento na na favela do Paraiso",
                5,
                5,
                0,
                40.0,
                50.0,
                10.0);

        assertEquals("Resposta(descricaoResumida)'" + alertaResponse.getDescricaoResumida() + "' do POST diferente do que foi enviado: '" + alertaRequest.getDescricaoResumida() + "'!", alertaResponse.getDescricaoResumida(), alertaRequest.getDescricaoResumida());
        assertEquals("Resposta(descricaoCompleta)'" + alertaResponse.getDescricaoCompleta() + "' do POST diferente do que foi enviado'" + alertaRequest.getDescricaoCompleta() + "'!", alertaResponse.getDescricaoCompleta(), alertaRequest.getDescricaoCompleta());
        assertEquals("Resposta(fatorRiscoHumano)'" + alertaResponse.getFatorRiscoHumano() + "' do POST diferente do que foi enviado'" + alertaRequest.getFatorRiscoHumano() + "'!", alertaResponse.getFatorRiscoHumano(), alertaRequest.getFatorRiscoHumano());
        assertEquals("Resposta(fatorRiscoMaterial)'" + alertaResponse.getFatorRiscoMaterial() + "' do POST diferente do que foi enviado'" + alertaRequest.getFatorRiscoMaterial() + "'!", alertaResponse.getFatorRiscoMaterial(), alertaRequest.getFatorRiscoMaterial());
        assertEquals("Resposta(categoriaAlerta)'" + alertaResponse.getCategoriaAlerta() + "' do POST diferente do que foi enviado'" + alertaRequest.getCategoriaAlerta() + "'!", alertaResponse.getCategoriaAlerta(), alertaRequest.getCategoriaAlerta());
        assertEquals("Resposta(origemLatitude)'" + alertaResponse.getOrigemLatitude() + "' do POST diferente do que foi enviado'" + alertaRequest.getOrigemLatitude() + "'!", alertaResponse.getOrigemLatitude(), alertaRequest.getOrigemLatitude(), DELTA);
        assertEquals("Resposta(origemLongitude)'" + alertaResponse.getOrigemLongitude() + "' do POST diferente do que foi enviado'" + alertaRequest.getOrigemLongitude() + "'!", alertaResponse.getOrigemLongitude(), alertaRequest.getOrigemLongitude(), DELTA);
        assertEquals("Resposta(origemRaioKms)'" + alertaResponse.getOrigemRaioKms() + "' do POST diferente do que foi enviado'" + alertaRequest.getOrigemRaioKms() + "'!", alertaResponse.getOrigemRaioKms(), alertaRequest.getOrigemRaioKms(), DELTA);
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
        String alertaURL = getBaseUrl() + "/alerta/latitude/{latitude}/longitude/{longitude}/raio/{raio}";

        Map<String, Double> params = new HashMap<String, Double>();
        params.put("latitude", 40.0);
        params.put("longitude", 50.0);
        params.put("raio", 1D);

        List<Alerta> alertas = getRestTemplate().getForObject(alertaURL, AlertaResources.class, params).unwrap();

        assertEquals("Resposta(descricaoResumida)'" + alertas.get(0).getDescricaoResumida() + "' do POST diferente do esperado!", alertas.get(0).getDescricaoResumida(), "Alagamento");
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
        String alertaURL = getBaseUrl() + "/alerta/latitude/{latitude}/longitude/{longitude}/raio/{raio}";

        Map<String, Double> params = new HashMap<String, Double>();
        params.put("latitude", 0.6D);
        params.put("longitude", 0.6D);
        params.put("raio", 1D);

        ResponseEntity<AlertaResources> resources = getRestTemplate().getForEntity(alertaURL, AlertaResources.class, params);
        assertThat(resources.getStatusCode() == HttpStatus.NOT_FOUND);
    }


    /**
     *
     * = TS02-US09
     * 
     * == Asserção:
     *
     * Testa a inclusão de um grupo de indicadores usando o seriço de Alertas:
     *
     * == Dados:
     *
     * N/A.
     *
     * == Execução:
     *
     * Uma chamada ao serviço de Alertas.
     *
     * == Resultado esperado:
     *
     * Uma estrutura de dados com os dados dos indicadores.
     *
     * === Estrutura de dados
     *
     * [source,java]
     * --
     *	Indicadores indicadoresRequest = new Indicadores();
     *	indicadoresRequest.addIndicador("Cadastrados", 31);
     *	indicadoresRequest.addIndicador("Finalizados", 19);
     *	indicadoresRequest.addIndicador("Em andamento", 6);
     * --
     *
     */
    @Test
    public void test109PostIndicadores() throws Exception {
    	String indicadoresURL = getBaseUrl() + "/indicadores/";

    	Indicadores indicadoresRequest = new Indicadores();
    	indicadoresRequest.addIndicador("Cadastrados", 31);
    	indicadoresRequest.addIndicador("Finalizados", 19);
    	indicadoresRequest.addIndicador("Em andamento", 6);

        ResponseEntity<IndicadoresResource> indicadoresResponseEntity =  getRestTemplate().postForEntity(indicadoresURL, new HttpEntity<Indicadores>(indicadoresRequest), IndicadoresResource.class);

        assertThat(indicadoresResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Indicadores indicadoresResponse = indicadoresResponseEntity.getBody().getContent();

        assertEquals("Resposta(itens):'" + indicadoresResponse.getIndicadores().size() + "' do POST diferente do que foi enviado: '" + indicadoresRequest.getIndicadores().size() + "'!", indicadoresRequest.getIndicadores().size(), indicadoresRequest.getIndicadores().size());
        assertEquals("Resposta(valor do indicador'" + IND_CADASTRADO + "'):'" + indicadoresResponse.getIndicador(IND_CADASTRADO) + "' do POST diferente do que foi enviado: '" + indicadoresRequest.getIndicador(IND_CADASTRADO) + "'!", indicadoresRequest.getIndicador(IND_CADASTRADO), indicadoresRequest.getIndicador(IND_CADASTRADO));
        assertEquals("Resposta(valor do indicador'" + IND_FINALIZADOS + "'):'" + indicadoresResponse.getIndicador(IND_FINALIZADOS) + "' do POST diferente do que foi enviado: '" + indicadoresRequest.getIndicador(IND_FINALIZADOS) + "'!", indicadoresRequest.getIndicador(IND_FINALIZADOS), indicadoresRequest.getIndicador(IND_FINALIZADOS));
        assertEquals("Resposta(valor do indicador'" + IND_ABERTOS + "'):'" + indicadoresResponse.getIndicador(IND_ABERTOS) + "' do POST diferente do que foi enviado: '" + indicadoresRequest.getIndicador(IND_ABERTOS) + "'!", indicadoresRequest.getIndicador(IND_ABERTOS), indicadoresRequest.getIndicador(IND_ABERTOS));
    }
    /**
     *
     * = TS02-US09
     * 
     * == Asserção:
     *
     * Testa a obtenção dos indicadores de Alertas:
     *
     * == Dados:
     *
     * Não se aplica.
     *
     * == Execução:
     *
     * Uma chamada ao serviço de Alertas.
     *
     * == Resultado esperado:
     *
     * Uma estrutura de dados com os dados dos indicadores.
     *
     * === Estrutura de dados
     *
     * [source,java]
     * --
     *	Indicadores indicadoresRequest = new Indicadores();
     *	indicadoresRequest.addIndicador("Cadastrados", 31);
     *	indicadoresRequest.addIndicador("Finalizados", 19);
     *	indicadoresRequest.addIndicador("Em andamento", 6);
     * --
     *
     */
    @Test
    public void test110GetIndicadores() throws URISyntaxException {
        String indicadoresURL = getBaseUrl() + "/indicadores";

        ResponseEntity<IndicadoresResource> indicadoresResponseEntity = getRestTemplate().getForEntity(indicadoresURL, IndicadoresResource.class);

        assertThat(indicadoresResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        Indicadores indicadoresResponse = indicadoresResponseEntity.getBody().getContent();

    	Indicadores indicadoresRequest = new Indicadores();
    	indicadoresRequest.addIndicador("Cadastrados", 31);
    	indicadoresRequest.addIndicador("Finalizados", 19);
    	indicadoresRequest.addIndicador("Em andamento", 6);

        assertEquals("Resposta(itens):'" + indicadoresResponse.getIndicadores().size() + "' do POST diferente do que foi enviado: '" + indicadoresRequest.getIndicadores().size() + "'!", indicadoresRequest.getIndicadores().size(), indicadoresRequest.getIndicadores().size());
        assertEquals("Resposta(valor do indicador'" + IND_CADASTRADO + "'):'" + indicadoresResponse.getIndicador(IND_CADASTRADO) + "' do POST diferente do que foi enviado: '" + indicadoresRequest.getIndicador(IND_CADASTRADO) + "'!", indicadoresRequest.getIndicador(IND_CADASTRADO), indicadoresRequest.getIndicador(IND_CADASTRADO));
        assertEquals("Resposta(valor do indicador'" + IND_FINALIZADOS + "'):'" + indicadoresResponse.getIndicador(IND_FINALIZADOS) + "' do POST diferente do que foi enviado: '" + indicadoresRequest.getIndicador(IND_FINALIZADOS) + "'!", indicadoresRequest.getIndicador(IND_FINALIZADOS), indicadoresRequest.getIndicador(IND_FINALIZADOS));
        assertEquals("Resposta(valor do indicador'" + IND_ABERTOS + "'):'" + indicadoresResponse.getIndicador(IND_ABERTOS) + "' do POST diferente do que foi enviado: '" + indicadoresRequest.getIndicador(IND_ABERTOS) + "'!", indicadoresRequest.getIndicador(IND_ABERTOS), indicadoresRequest.getIndicador(IND_ABERTOS));
    }
    
    /**
     *
     * = TS02-US09
     * 
     * == Asserção:
     *
     * Testa a inclusão de um evento de crise usando o seriço de Alertas.
     * Verificar se foi gerado um alerta com base nos dados do evento recém inserido.
     * Observar que em casos reais, existe uma demora significativa entre o cadastramento
     * do evento e a geração do alerta de crise.
     *
     * == Dados:
     *
     * Uma estrutura de dados contendo o evento.
     *
     * === Estrutura de dados
     *
     * [source,java]
     * --
     *  Evento eventoRequest = new Evento(
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
     *  Evento eventoRequest = new Evento(
     *          "Deslizamento na na favela do Paraiso",
     *          0,
     *          "Ze das Couves",
     *          "zedascouves@gmail.com",
     *          "(12) 99876-1234",
     *          40.0,
     *          50.0);
     * --
     *
     */
   @Test
   public void test111PostEventoGerarAlerta() throws Exception {
       URI eventoURI = new URI(getBaseUrl() + "/evento");

       Evento eventoRequest = new Evento(
               "Deslizamento na na favela do Paraiso",
               0,
               "Ze das Couves",
               "zedascouves@gmail.com",
               "(12) 99876-1234",
               40.0,
               50.0);

       ResponseEntity<EventoResource> eventoResponseEntity =  getRestTemplate().postForEntity(eventoURI, new HttpEntity<Evento>(eventoRequest), EventoResource.class);

       assertThat(eventoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

       Evento eventoResponse = eventoResponseEntity.getBody().getContent();

       assertEquals("Resposta(descricao):'" + eventoResponse.getDescricao() + "' do POST diferente do que foi enviado: '" + eventoRequest.getDescricao() + "'!", eventoResponse.getDescricao(), eventoRequest.getDescricao());
       assertEquals("Resposta(categoria) do POST diferente do que foi enviado!", eventoResponse.getCategoria(), eventoRequest.getCategoria());
       assertEquals("Resposta(nome) do POST diferente do que foi enviado!", eventoResponse.getNome(), eventoRequest.getNome());
       assertEquals("Resposta(email) do POST diferente do que foi enviado!", eventoResponse.getEmail(), eventoRequest.getEmail());
       assertEquals("Resposta(telefone) do POST diferente do que foi enviado!", eventoResponse.getTelefone(), eventoRequest.getTelefone());
       
       String alertaURL = getBaseUrl() + "/alerta/latitude/{latitude}/longitude/{longitude}/raio/{raio}";

       Map<String, Double> params = new HashMap<String, Double>();
       params.put("latitude", 40.0);
       params.put("longitude", 50.0);
       params.put("raio", 1D);

       List<Alerta> alertas = getRestTemplate().getForObject(alertaURL, AlertaResources.class, params).unwrap();

       assertEquals("Resposta(latitude)'" + alertas.get(0).getOrigemLatitude() + "' do POST diferente do esperado!", alertas.get(0).getOrigemLatitude(), eventoRequest.getLatitude(), DELTA);
       assertEquals("Resposta(latitude)'" + alertas.get(0).getOrigemLongitude() + "' do POST diferente do esperado!", alertas.get(0).getOrigemLongitude(), eventoRequest.getLongitude(), DELTA);
   }
   
    @Test
    public void test901PostEvento() throws Exception {
        Evento evento = new Evento(
            "Evento de teste",
            0,
            "João da Horta",
            "joao.horta@gmail.com",
            "(12) 95678-4321",
            40.0,
            50.0);
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
                    fieldWithPath("latitude").type(JsonFieldType.NUMBER).description("Latitude da localização do informante durante o post do evento"),
                    fieldWithPath("longitude").type(JsonFieldType.STRING).description("Longitude da localização do informante durante o post do evento"))));
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
                    fieldWithPath("latitude").type(JsonFieldType.STRING).description("Latitude da localização do informante durante o post do evento"),
                    fieldWithPath("longitude").type(JsonFieldType.STRING).description("Longitude da localização do informante durante o post do evento"),
                    fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("URI do link para o evento")),
                links(
                    linkWithRel("self").description("Link para o evento"))));
    }

    @Test
    public void test904PostAlerta() throws Exception {
        Alerta alerta = new Alerta(
            "Alerta de teste",
            "Teste de alerta para verificar a funcionalidade do sistema",
            5,
            5,
            0,
            40.0D,
            50.0D,
            1.0D);
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
                    fieldWithPath("origemRaioKms").type(JsonFieldType.ARRAY).description("Área de abrangência do alerta em Kms"))));
    }

    @Test
    public void test906GetAlertaById() throws Exception {
        this.mvc.perform(get(getBaseUrl() + "/alerta/{id}", 1))
            .andExpect(status().isOk())
            .andDo(document("alerta/locationsId",
                pathParameters(parameterWithName("id").description("Identificação do alerta"))));
        this.mvc.perform(get(getBaseUrl() + "/alerta/1")
            .accept(MediaTypes.HAL_JSON_VALUE))
            .andExpect(status().isOk())
            .andDo(document("alerta/getId",
                responseFields(
                    fieldWithPath("descricaoResumida").type(JsonFieldType.STRING).description("Breve descrição do alerta"),
                    fieldWithPath("descricaoCompleta").type(JsonFieldType.STRING).description("Descrição detalhada do alerta"),
                    fieldWithPath("fatorRiscoHumano").type(JsonFieldType.STRING).description("Fator de risco para a vida humana"),
                    fieldWithPath("fatorRiscoMaterial").type(JsonFieldType.STRING).description("Fator de risco para instalações e equipamentos"),
                    fieldWithPath("categoriaAlerta").type(JsonFieldType.STRING).description("Indica o tipo de alerta"),
                    fieldWithPath("origemLatitude").type(JsonFieldType.ARRAY).description("Latitude do ponto de origem do alerta"),
                    fieldWithPath("origemLongitude").type(JsonFieldType.ARRAY).description("Longitude do ponto de origem do alerta"),
                    fieldWithPath("origemRaioKms").type(JsonFieldType.ARRAY).description("Área de abrangência do alerta em Kms"),
                    fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("URI do link para o alerta")),
                links(
                    linkWithRel("self").description("Link para o evento"))));
    }

    @Test
    public void test907GetAlertaByCoords() throws Exception {
        this.mvc.perform(get(getBaseUrl() + "/alerta/latitude/{latitude}/longitude/{longitude}/raio/{raio}", 40.0, 50.0, 1.0D))
            .andExpect(status().isOk())
            .andDo(document("alerta/locationsCoords",
                pathParameters(
                    parameterWithName("latitude").description("Latitude do ponto de origem do alerta"),
                    parameterWithName("longitude").description("Longitude do ponto de origem do alerta"),
                    parameterWithName("raio").description("Área de abrangência do alerta em Kms"))));
        this.mvc.perform(get(getBaseUrl() + "alerta/latitude/40.0/longitude/50.0/raio/1.0")
            .accept(MediaTypes.HAL_JSON_VALUE))
            .andExpect(status().isOk())
            .andDo(document("alerta/getCoords",
                responseFields(
                    fieldWithPath("_embedded.alertaList[].descricaoResumida").type(JsonFieldType.STRING).description("Breve descrição do alerta"),
                    fieldWithPath("_embedded.alertaList[].descricaoCompleta").type(JsonFieldType.STRING).description("Descrição detalhada do alerta"),
                    fieldWithPath("_embedded.alertaList[].fatorRiscoHumano").type(JsonFieldType.STRING).description("Fator de risco para a vida humana"),
                    fieldWithPath("_embedded.alertaList[].fatorRiscoMaterial").type(JsonFieldType.STRING).description("Fator de risco para instalações e equipamentos"),
                    fieldWithPath("_embedded.alertaList[].categoriaAlerta").type(JsonFieldType.STRING).description("Indica o tipo de alerta"),
                    fieldWithPath("_embedded.alertaList[].origemLatitude").type(JsonFieldType.ARRAY).description("Latitude do ponto de origem do alerta"),
                    fieldWithPath("_embedded.alertaList[].origemLongitude").type(JsonFieldType.ARRAY).description("Longitude do ponto de origem do alerta"),
                    fieldWithPath("_embedded.alertaList[].origemRaioKms").type(JsonFieldType.ARRAY).description("Área de abrangência do alerta em Kms"),
                    fieldWithPath("_embedded.alertaList[]._links.self.href").type(JsonFieldType.STRING).description("URI do link para o alerta"))));
    }

}
