package br.ita.bditac.mqtt.service;


import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;


public class DiagnosticoMessageHandler implements MessageHandler {

	@Override
	public void handleMessage(Message<?> message) throws MessagingException {
		
		System.out.println("Messagem recebida: " + message);
		
	}

}
