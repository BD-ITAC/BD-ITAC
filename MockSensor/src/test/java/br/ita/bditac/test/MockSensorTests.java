package br.ita.bditac.test;


import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.UUID;

import org.junit.FixMethodOrder;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.context.WebApplicationContext;

import br.ita.bditac.mqtt.app.MqttApplication;
import br.ita.bditac.mqtt.model.Sensor;
import br.ita.bditac.mqtt.model.SensorDAO;
import br.ita.bditac.mqtt.model.Tipo;
import br.ita.bditac.mqtt.model.Topico;

/**
 *
 * = Testes unitários do serviço de envio/recebimento de dados de sensores do BD-ITAC.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MqttApplication.class)
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
    
    @Value("${local.server.port}")
    private int port;

    @Autowired
    WebApplicationContext context;

    protected String getBaseUrl() {
        return "http://localhost:" + port;
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
     * O tipo de sensor de acordo com a tabela @see br.bditac.mqtt.model.Tipo.
     *
     * === Estrutura de dados
     *
     * [source,java]
     * --
     *  Tipo tipo;
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
    	
    	SensorDAO service = new SensorDAO();
    	
    	UUID uuid = service.adicionaSensor(Tipo.AceleracaoVibracao);
    	
    	Sensor sensor = service.obterSensor(uuid);
    	
    	assertThat(sensor).isNotNull();
    	assertThat(sensor.getTipo()).isEqualTo(Tipo.AceleracaoVibracao);
    	
    	String payload = "Mensagem de diagnóstico";
		Message<byte[]> message = MessageBuilder.withPayload(payload.getBytes()).setHeader(MqttHeaders.TOPIC, Topico.Diagnosticos.name()).build();
		
		MessageChannel messageChannel = context.getBean("channel", MessageChannel.class);
		
		assertThat(messageChannel.send(message));

    }

    /**
     *
     * = TS02-US10
     * 
     * == Asserção:
     *
     * Testa a inclusão de um sensor e envia uma mensagem para o tópico errado
     *
     * == Dados:
     *
     * O tipo de sensor de acordo com a tabela @see br.bditac.mqtt.model.Tipo.
     *
     * === Estrutura de dados
     *
     * [source,java]
     * --
     *  Tipo tipo;
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
    	
    	SensorDAO service = new SensorDAO();
    	
    	UUID uuid = service.adicionaSensor(Tipo.AceleracaoVibracao);
    	
    	Sensor sensor = service.obterSensor(uuid);
    	
    	assertThat(sensor).isNotNull();
    	assertThat(sensor.getTipo()).isEqualTo(Tipo.AceleracaoVibracao);
    	
    	String payload = "Mensagem de diagnóstico";
		Message<byte[]> message = MessageBuilder.withPayload(payload.getBytes()).setHeader(MqttHeaders.TOPIC, Topico.NivelAgua.name()).build();
		
		MessageChannel messageChannel = context.getBean("channel", MessageChannel.class);
		
		assertThat(messageChannel.send(message)).isFalse();

    }

}
