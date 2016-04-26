package br.ita.bditac.app;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.scheduling.annotation.EnableScheduling;

import br.ita.bditac.model.Credencial;
import br.ita.bditac.model.TopicoTipo;
import br.ita.bditac.ws.support.Constants;


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
    public MqttPahoClientFactory mqttClientFactory() {
    	
      DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
      factory.setServerURIs(Constants.MQTT_HOST);
      factory.setUserName(Credencial.getNome());
      factory.setPassword(Credencial.getSenha());
      
      return factory;
      
    }

	@Bean
	public IntegrationFlow mqttInFlow() {
		
		return IntegrationFlows.from(inbound())
				.transform(p -> p)
				.handle(handler())
				.get();
		
	}

    @Bean
    public MessageProducerSupport inbound() {
    	
    	MqttPahoMessageDrivenChannelAdapter adapter =
    			new MqttPahoMessageDrivenChannelAdapter(Credencial.getNome(), mqttClientFactory());
    	adapter.addTopic(TopicoTipo.Diagnosticos.toString(), Constants.MQTT_CHANNEL_ADAPTER_QOS);
    	adapter.setCompletionTimeout(Constants.MQTT_CHANNEL_ADAPTER_COMPLETION_TIMEOUT);
    	adapter.setConverter(new DefaultPahoMessageConverter());
    	adapter.setOutputChannel(channel());
    	
    	return adapter;
    	
    }

    @Bean
    public MessageHandler handler() {
    	
    	return new MessageHandler() {

    		@Override
    		public void handleMessage(Message<?> message) throws MessagingException {
    			System.out.println(message.getPayload());
    		}

    	};
    	
    }

    @Bean
    public MessageChannel channel() {
    	
    	return new DirectChannel();
    	
    }
    
    public static void main(String[] args) {
    	
    	new SpringApplicationBuilder(Application.class)
    		.web(true)
    		.run(args);
    	
    }
   
}
