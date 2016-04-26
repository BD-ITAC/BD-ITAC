package br.ita.bditac.model;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;


public class IOTFPayloadMessageHandler implements MessageHandler {

	private IOTFPayload payload;
	
	@Override
	public void handleMessage(Message<?> message) throws MessagingException {
		try {
			this.payload = (IOTFPayload)message.getPayload();
		}
		catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public IOTFPayload getPayload() {
		return this.payload;
	}
	
}
