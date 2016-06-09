package br.ita.bditac.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Configuration;
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
import com.fasterxml.jackson.datatype.joda.JodaModule;

import br.ita.bditac.app.Application;
import br.ita.bditac.model.Alerta;
import br.ita.bditac.model.Crise;
import br.ita.bditac.ws.support.AlertaResource;
import br.ita.bditac.ws.support.AlertaResources;
import br.ita.bditac.ws.support.CriseResource;
import br.ita.bditac.ws.support.MessageResource;

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
    
    private static String _foto = null;

    @Configuration
    @EnableConfigurationProperties
    @ConfigurationProperties(prefix="info.build")
    class MockSettings {
    	
    	private String version;
		
		public String getVersion() {
			return version;
		}
    	
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
    public void test000BootstrapsWebApp() {
        assertThat(mvc).isNotNull();
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
    public void test101PostCriseGerarAlerta() throws Exception {
        URI criseURI = new URI(getBaseUrl() + "/crise");
        
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
        String criseURL = getBaseUrl() + "/crise/{id}";

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
                0,
                40.0,
                50.0,
                1.0);

        ResponseEntity<MessageResource> alertaResponseEntity = getRestTemplate().postForEntity(alertaURI, new HttpEntity<Alerta>(alertaRequest), MessageResource.class);

        assertThat(alertaResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
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
        String alertaURL = getBaseUrl() + "/alerta/timestamp/{timestamp}/latitude/{latitude}/longitude/{longitude}/raio/{raio}";

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
        String alertaURL = getBaseUrl() + "/alerta/timestamp/{timestamp}/latitude/{latitude}/longitude/{longitude}/raio/{raio}";

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
       URI criseURI = new URI(getBaseUrl() + "/crise");

       Crise criseRequest = new Crise(
               "Deslizamento na na favela do Paraiso",
               1,
               "Ze das Couves",
               "zedascouves@gmail.com",
               "(12) 99876-1234",
               40.0,
               50.0,
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
    public void test901PostCrise() throws Exception {
        Crise crise = new Crise(
            "Crise de teste",
            1,
            "João da Horta",
            "joao.horta@gmail.com",
            "(12) 95678-4321",
            40.0,
            50.0,
            getFoto());
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String criseJson = writer.writeValueAsString(crise);

        this.mvc.perform(post(getBaseUrl() + "/crise")
            .contentType(MediaTypes.HAL_JSON_VALUE)
            .content(criseJson))
            .andExpect(status().isCreated())
            .andDo(document("crise/post",
            	preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("Identificação da mensagem"),
                    fieldWithPath("type").type(JsonFieldType.STRING).description("Tipo da mensagem (INFO, WARNING, ERROR)"),
                    fieldWithPath("status").type(JsonFieldType.STRING).description("Código de estado do sistema"),
                    fieldWithPath("description").type(JsonFieldType.STRING).description("Descrição da mensagem"),
                    fieldWithPath("info").type(JsonFieldType.STRING).description("Informações adicionais"))));
    }

    @Test
    public void test904GetAlerta() throws Exception {
        this.mvc.perform(get(getBaseUrl() + "/alerta/id/{id}", 2))
        .andExpect(status().isOk())
        .andDo(document("alerta/id",
            pathParameters(
            	parameterWithName("id").description("Identificador do alerta no sistema"))));
    this.mvc.perform(get(getBaseUrl() + "alerta/id/2")
        .accept(MediaTypes.HAL_JSON_VALUE))
        .andExpect(status().isOk())
        .andDo(document("alerta/id",
        	preprocessResponse(prettyPrint()),
            responseFields(
                fieldWithPath("id").type(JsonFieldType.NUMBER).description("Identificação do alerta no sistema"),
                fieldWithPath("descricaoResumida").type(JsonFieldType.STRING).description("Breve descrição do alerta"),
                fieldWithPath("descricaoCompleta").type(JsonFieldType.STRING).description("Descrição detalhada do alerta"),
                fieldWithPath("categoriaAlerta").type(JsonFieldType.STRING).description("Indica o tipo de alerta"),
                fieldWithPath("origemLatitude").type(JsonFieldType.NUMBER).description("Latitude do ponto de origem do alerta"),
                fieldWithPath("origemLongitude").type(JsonFieldType.NUMBER).description("Longitude do ponto de origem do alerta"),
                fieldWithPath("origemRaioKms").type(JsonFieldType.NUMBER).description("Área de abrangência do alerta em Kms"),
    			fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("URI do link para o alerta"))));
    }

    @Test
    public void test907GetAlertaByCoords() throws Exception {
        this.mvc.perform(get(getBaseUrl() + "/alerta/timestamp/{timestamp}/latitude/{latitude}/longitude/{longitude}/raio/{raio}", 0, 40.0, 50.0, 1.0D))
            .andExpect(status().isOk())
            .andDo(document("alerta/locationsCoords",
                pathParameters(
                	parameterWithName("timestamp").description("Timestamp desde a última consulta"),
                    parameterWithName("latitude").description("Latitude do ponto de origem do alerta"),
                    parameterWithName("longitude").description("Longitude do ponto de origem do alerta"),
                    parameterWithName("raio").description("Área de abrangência do alerta em Kms"))));
        this.mvc.perform(get(getBaseUrl() + "alerta/timestamp/0/latitude/40.0/longitude/50.0/raio/1.0")
            .accept(MediaTypes.HAL_JSON_VALUE))
            .andExpect(status().isOk())
            .andDo(document("alerta/getCoords",
            	preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("_embedded.alertaList[].id").type(JsonFieldType.NUMBER).description("Identificação do alerta no sistema"),
                    fieldWithPath("_embedded.alertaList[].descricaoResumida").type(JsonFieldType.STRING).description("Breve descrição do alerta"),
                    fieldWithPath("_embedded.alertaList[].descricaoCompleta").type(JsonFieldType.STRING).description("Descrição detalhada do alerta"),
                    fieldWithPath("_embedded.alertaList[].categoriaAlerta").type(JsonFieldType.STRING).description("Indica o tipo de alerta"),
                    fieldWithPath("_embedded.alertaList[].origemLatitude").type(JsonFieldType.NUMBER).description("Latitude do ponto de origem do alerta"),
                    fieldWithPath("_embedded.alertaList[].origemLongitude").type(JsonFieldType.NUMBER).description("Longitude do ponto de origem do alerta"),
                    fieldWithPath("_embedded.alertaList[].origemRaioKms").type(JsonFieldType.NUMBER).description("Área de abrangência do alerta em Kms"),
                    fieldWithPath("_embedded.alertaList[]._links.self.href").type(JsonFieldType.STRING).description("URI do link para o alerta"))));
    }
    
    @Test
    public void test910GetIndicadores() throws Exception {
    	this.mvc.perform(get(getBaseUrl() + "/indicadores/latitude/{latitude}/longitude/{longitude}/raio/{raio}", 40.0, 50.0, 1.0D))
    		.andExpect(status().isOk())
    		.andDo(document("indicadores",
                    pathParameters(
                            parameterWithName("latitude").description("Latitude do ponto de origem do alerta"),
                            parameterWithName("longitude").description("Longitude do ponto de origem do alerta"),
                            parameterWithName("raio").description("Área de abrangência do alerta em Kms"))));
        this.mvc.perform(get(getBaseUrl() + "indicadores/latitude/40.0/longitude/50.0/raio/1.0")
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(document("indicadores",
                	preprocessResponse(prettyPrint()),
                    responseFields(
                        fieldWithPath("_embedded.indicadorList[].id").type(JsonFieldType.STRING).description("Identificador do indicador"),
                        fieldWithPath("_embedded.indicadorList[].descricao").type(JsonFieldType.STRING).description("Descrição do indicador"),
                        fieldWithPath("_embedded.indicadorList[].valor").type(JsonFieldType.STRING).description("Valor do indicador"))));
    }
    
    @Test
    public void test920GetCategorias() throws Exception {
    	this.mvc.perform(get(getBaseUrl() + "/categorias"))
    		.andExpect(status().isOk())
    		.andDo(document("categorias",
                    pathParameters()));
        this.mvc.perform(get(getBaseUrl() + "categorias")
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(document("categorias",
                	preprocessResponse(prettyPrint()),
                    responseFields(
                        fieldWithPath("_embedded.categoriaList[].id").type(JsonFieldType.STRING).description("Identificador da categoria"),
                        fieldWithPath("_embedded.categoriaList[].descricao").type(JsonFieldType.STRING).description("Descrição da categoria"))));
    }

}
