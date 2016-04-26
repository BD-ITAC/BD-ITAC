package br.ita.bditac.app;


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

import com.fasterxml.jackson.databind.ObjectMapper;

import br.ita.bditac.model.Credencial;
import br.ita.bditac.model.IOTFPayload;
import br.ita.bditac.mqtt.service.IOTFPayloadMessageHandler;
import br.ita.bditac.mqtt.support.MQTTConstants;


@SpringBootApplication
@IntegrationComponentScan(basePackages = "br.ita.bditac")
@ComponentScan(basePackages = "br.ita.bditac")
@EnableScheduling
public class Application {
	
    @Bean
    public DispatcherServletBeanPostProcessor dispatcherServletBeanPostProcessor() {
    	
        return new DispatcherServletBeanPostProcessor();
        
    }
    
    @Bean
    public MqttPahoClientFactory clientFactory() {
    	
      DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
      factory.setServerURIs(MQTTConstants.MQTT_HOST);
      factory.setUserName(Credencial.getNome());
      factory.setPassword(Credencial.getSenha());
      
      return factory;
      
    }

	@Bean
	public IntegrationFlow sensorFlow() {
		
		ObjectMapper mapper = new ObjectMapper();
		return IntegrationFlows.from(sensorInbound())
				.transform(Transformers.fromJson(IOTFPayload.class, new Jackson2JsonObjectMapper(mapper)))
				.handle(payloadHandler())
				.get();
		
	}

    @Bean
    public MessageProducerSupport sensorInbound() {
    	
    	MqttPahoMessageDrivenChannelAdapter adapter =
    			new MqttPahoMessageDrivenChannelAdapter(Credencial.getNome(), clientFactory());
    	adapter.addTopic("br.ita.bditac/test", MQTTConstants.MQTT_CHANNEL_ADAPTER_QOS);
    	adapter.setCompletionTimeout(MQTTConstants.MQTT_CHANNEL_ADAPTER_COMPLETION_TIMEOUT);
    	adapter.setConverter(new DefaultPahoMessageConverter());
    	adapter.setOutputChannel(sensorChannel());
    	
    	return adapter;
    	
    }

    @Bean
    public MessageHandler payloadHandler() {
    	
    	return new IOTFPayloadMessageHandler();
    	
    }

    @Bean
    public MessageChannel sensorChannel() {
    	
    	return new DirectChannel();
    	
    }
    
    public static void main(String[] args) {
    	
    	new SpringApplicationBuilder(Application.class)
    		.web(true)
    		.run(args);
    	
    }
   
}
