package br.ita.bditac.test;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import br.ita.bditac.app.Application;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class MQTTTest {

    @Autowired
    private WebApplicationContext context;

	@Test
	public void test() throws Exception {

    	String payload = new String("T E S T");
		Message<byte[]> message = MessageBuilder
				.withPayload(payload.getBytes())
				.setHeader(MqttHeaders.TOPIC, "bditac/test")
				.build();
		MessageChannel messageChannel = context.getBean("channel", MessageChannel.class);
		assertThat(messageChannel.send(message));
		Thread.sleep(4000);
		
	}

	@Test
	public void diag() throws Exception {

    	String payload = new String("D I A G");
		Message<byte[]> message = MessageBuilder
				.withPayload(payload.getBytes())
				.setHeader(MqttHeaders.TOPIC, "bditac/diag")
				.build();
		MessageChannel messageChannel = context.getBean("channel", MessageChannel.class);
		assertThat(messageChannel.send(message));
		Thread.sleep(4000);
		
	}

}