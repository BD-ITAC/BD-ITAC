package br.ita.bditac.mqtt.app;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import br.ita.bditac.mqtt.model.Credencial;
import br.ita.bditac.mqtt.model.TopicoTipo;
import br.ita.bditac.mqtt.service.DiagnosticoMessageHandler;
import br.ita.bditac.mqtt.support.Constants;


@Configuration
@SpringBootApplication
@IntegrationComponentScan(basePackages = "br.ita.bditac")
public class MqttApplication {
	
    public static void main(String[] args) {
    	new SpringApplicationBuilder(MqttApplication.class).web(true).run(args);
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
    			new MqttPahoMessageDrivenChannelAdapter(Credencial.getNome(), mqttClientFactory(), TopicoTipo.Diagnosticos.toString());
    	adapter.setCompletionTimeout(Constants.MQTT_CHANNEL_ADAPTER_COMPLETION_TIMEOUT);
    	adapter.setConverter(new DefaultPahoMessageConverter());
    	adapter.setQos(Constants.MQTT_CHANNEL_ADAPTER_QOS);
    	adapter.setOutputChannel(channel());
    	
    	return adapter;
    }

    @Bean
    public MessageHandler handler() {
    	return new DiagnosticoMessageHandler();
    }

    @Bean
    public MessageChannel channel() {
    	return new DirectChannel();
    }
   
}
