package br.ita.bditac.test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import org.joda.time.DateTime;
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
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import br.ita.bditac.app.Application;
import br.ita.bditac.model.IOTFPayload;
import br.ita.bditac.model.Sensor;
import br.ita.bditac.model.SensorDAO;
import br.ita.bditac.model.SensorTipo;
import br.ita.bditac.ws.support.MessageResource;


/**
 *
 * = Testes unitários do serviço de envio/recebimento de dados de sensores do BD-ITAC.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@IntegrationTest("server.port:0")
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MockSensorTests {

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
    
    @Value("${local.server.port}")
    private int port;

    @Autowired
    private WebApplicationContext context;

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
     * = TS02-US10
     * 
     * == Asserção:
     *
     * Testa a inclusão de um sensor e envia uma mensagem
     *
     * == Dados:
     *
     * O tipo de sensor de acordo com a tabela @see br.bditac.mqtt.model.SensorTipo.
     *
     * === Estrutura de dados
     *
     * [source,java]
     * --
     *  SensorTipo tipo;
     * --
     *
     * == Execução:
     *
     * Uma chamada ao serviço de dados de Sensores.
     *
     * ==Resultado esperado:
     *
     * Identificador do sensor adicionado.
     *
     * === Estrutura de dados
     *
     * UUID uuid;
     * 
     */
    @Test
    public void test101CriaSensor() throws Exception {
    	
    	String uuid = SensorDAO.adicionarSensor(SensorTipo.AceleracaoVibracao);
    	
    	Sensor sensor = SensorDAO.obterSensor(uuid);
    	
    	assertThat(sensor).isNotNull();
    	assertThat(sensor.getTipo()).isEqualTo(SensorTipo.AceleracaoVibracao.ordinal());
    	
    	String payload = "Mensagem de diagnóstico";
		Message<byte[]> message = MessageBuilder.withPayload(payload.getBytes()).setHeader(MqttHeaders.TOPIC, "br.ita.bditac/diagnosticos").build();
		
		MessageChannel messageChannel = context.getBean("mqttOutboundChannel", MessageChannel.class);
		
		assertThat(messageChannel.send(message));

    }

    
    /**
     *
     * = TS02-US10
     * 
     * == Asserção:
     *
     * Testa a inclusão de um sensor e envia uma mensagem para o tópico
     *
     * == Dados:
     *
     * O tipo de sensor de acordo com a tabela @see br.bditac.mqtt.model.SensorTipo.
     *
     * === Estrutura de dados
     *
     * [source,java]
     * --
     *  SensorTipo tipo;
     * --
     *
     * == Execução:
     *
     * Uma chamada ao serviço de dados de Sensores.
     *
     * ==Resultado esperado:
     *
     * Identificador do sensor adicionado.
     *
     * === Estrutura de dados
     *
     * UUID uuid;
     * 
     */
    @Test
    public void test102CriaSensor() throws Exception {
    	
    	String uuid = SensorDAO.adicionarSensor(SensorTipo.AceleracaoVibracao);
    	
    	Sensor sensor = SensorDAO.obterSensor(uuid);
    	
    	assertThat(sensor).isNotNull();
    	assertThat(sensor.getTipo()).isEqualTo(SensorTipo.AceleracaoVibracao.ordinal());
    	
    	String payload = "Mensagem de diagnóstico";
		Message<byte[]> message = MessageBuilder.withPayload(payload.getBytes()).setHeader(MqttHeaders.TOPIC, "br.ita.bditac/diagnosticos").build();
		
		MessageChannel messageChannel = context.getBean("mqttOutboundChannel", MessageChannel.class);
		
		assertThat(messageChannel.send(message));

    }

    
    /**
     *
     * = TS02-US10
     * 
     * == Asserção:
     *
     * Testa a inclusão de um sensor por meio do serviço ReST
     *
     * == Dados:
     *
     * O tipo de sensor de acordo com a tabela @see br.bditac.mqtt.model.SensorTipo.
     *
     * === Estrutura de dados
     *
     * [source,json]
     * --
     * 	{
     * 		"descricao": STRING
     *  }
     * --
     *
     * == Execução:
     *
     * Uma chamada ao serviço de dados de Sensores.
     *
     * ==Resultado esperado:
     *
     * Identificador do sensor adicionado.
     *
     * === Estrutura de dados
     *
     * UUID uuid;
     * 
     */
    @Test
    public void test104CriaSensor() throws Exception {
    	
    	URI sensorURI = new URI(getBaseUrl() + "/sensor");
    	
    	Sensor sensorRequest = new Sensor(SensorTipo.Fluxo);
    	ResponseEntity<MessageResource> sensorResponseEntity = getRestTemplate().postForEntity(sensorURI, new HttpEntity<Sensor>(sensorRequest), MessageResource.class);
    	assertThat(sensorResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    	
    }


    /**
     *
     * = TS02-US10
     * 
     * == Asserção:
     *
     * Testa a inclusão de um tópico no broker de mensagens via serviço ReST e envia uma mensagem para um sensor utilizando o tópico criado.
     *
     * == Dados:
     *
     * Uma estrutura de dados JSON contendo os atributos do tópico.
     *
     * === Estrutura de dados @see br.bditac.mqtt.model.TopicoTipo.
     *
     * [source,json]
     * --
     * {
     * 		"tipo": NUMBER
     * }
     * --
     *
     * == Execução:
     *
     * Uma chamada ao serviço de Sensores.
     *
     * ==Resultado esperado:
     *
     * Um número inteiro positivo do tópico adicionado.
     *
     * === Estrutura de dados
     *
     * [source,java]
     * --
     * int novoTopico;
     * --
     *
     */
    @Test
    public void test105CriaTopico() throws Exception {
    	
    	URI sensorURI = new URI(getBaseUrl() + "/sensor");
    	ObjectMapper mapper = new ObjectMapper();
    	
    	Sensor sensorRequest = new Sensor(SensorTipo.HumidadeVapor, "br.ita.bditac/diagnosticos");
    	@SuppressWarnings("unused")
		String sensorJson = mapper.writeValueAsString(sensorRequest);
    	ResponseEntity<MessageResource> sensorResponseEntity = getRestTemplate().postForEntity(sensorURI, new HttpEntity<Sensor>(sensorRequest), MessageResource.class);
    	assertThat(sensorResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    	Random random = new Random(DateTime.now().getMillis());
    	IOTFPayload humidade = new IOTFPayload(
    			SensorTipo.HumidadeVapor.ordinal(),
    			random.nextDouble(),
    			random.nextDouble(),
    			random.nextDouble(),
    			random.nextDouble());
    	String humidadeJson = mapper.writeValueAsString(humidade);
		Message<byte[]> message = MessageBuilder.withPayload(humidadeJson.getBytes()).setHeader(MqttHeaders.TOPIC, "br.ita.bditac/diagnosticos").build();
		MessageChannel messageChannel = context.getBean("mqttOutboundChannel", MessageChannel.class);
		assertThat(messageChannel.send(message));
		
    }
    
    
    
}
