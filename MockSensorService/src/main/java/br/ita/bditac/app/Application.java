package br.ita.bditac.app;


import java.util.UUID;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.support.Transformers;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.ita.bditac.model.IOTFPayload;
import br.ita.bditac.mqtt.support.IOTFPayloadMessageHandler;
import br.ita.bditac.mqtt.support.MQTTConstants;


@SpringBootApplication
@IntegrationComponentScan(basePackages = "br.ita.bditac")
@ComponentScan(basePackages = "br.ita.bditac")
@EnableScheduling
public class Application {

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {

      DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
      factory.setServerURIs(MQTTConstants.MQTT_DEFAULT_HOST, MQTTConstants.MQTT_BACKUP_HOST);

      return factory;

    }

	@Bean
	public IntegrationFlow mqttInFlow() {
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return IntegrationFlows.from(inbound())
				.transform(Transformers.fromJson(IOTFPayload.class, new Jackson2JsonObjectMapper(mapper)))
				.handle(handler())
				.get();

	}

    @Bean
    public MessageProducerSupport inbound() {

    	MqttPahoMessageDrivenChannelAdapter adapter =
    			new MqttPahoMessageDrivenChannelAdapter(UUID.randomUUID().toString(), mqttClientFactory());
    	adapter.addTopic(MQTTConstants.MQTT_GENERAL_TOPIC, MQTTConstants.MQTTQoS.MQTT_QOS_AT_MOST_ONCE);
    	adapter.setCompletionTimeout(MQTTConstants.MQTT_CHANNEL_ADAPTER_COMPLETION_TIMEOUT);
    	adapter.setConverter(new DefaultPahoMessageConverter());
    	adapter.setOutputChannel(channel());

    	return adapter;

    }

    @Bean
    public MessageHandler handler() {

    	return new IOTFPayloadMessageHandler();

    }

    @Bean
    public MessageChannel channel() {

    	return new DirectChannel();

    }

    public static void main(String[] args) {

    	new SpringApplicationBuilder(Application.class)
    		.web(false)
    		.run(args);

    }

}
