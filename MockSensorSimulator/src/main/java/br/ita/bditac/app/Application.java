package br.ita.bditac.app;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.scheduling.annotation.EnableScheduling;

import br.ita.bditac.model.Credencial;
import br.ita.bditac.mqtt.support.MQTTConstants;


@SpringBootApplication
@IntegrationComponentScan(basePackages = "br.ita.bditac")
@EnableIntegration
@ComponentScan(basePackages = "br.ita.bditac")
@EnableScheduling
public class Application {
	
    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler =
                       new MqttPahoMessageHandler(Credencial.getNome(), mqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(MQTTConstants.MQTT_DEFAULT_TOPIC);
        return messageHandler;
    }

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    @MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
    public interface BDITACGateway {
    	
    	void sendToMqtt(String payload, @Header(MqttHeaders.TOPIC) String topic);
    	
    }
    
    @Bean
	public MqttPahoClientFactory mqttClientFactory() {
	
    	DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
    	factory.setServerURIs(MQTTConstants.MQTT_DEFAULT_HOST, MQTTConstants.MQTT_BACKUP_HOST);
    	factory.setUserName(Credencial.getNome());
    	factory.setPassword(Credencial.getSenha());
  
    	return factory;
  
	}
	
    public static void main(String[] args) {
    	
    	new SpringApplicationBuilder(Application.class)
			.web(true)
			.run(args);
		
    }
   
}
