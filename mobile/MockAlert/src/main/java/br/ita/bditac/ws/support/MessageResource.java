package br.ita.bditac.ws.support;


import org.springframework.hateoas.Resource;

import br.ita.bditac.model.Message;


public class MessageResource extends Resource<Message> {
    
    public MessageResource() {
    	super(new Message());
    }

    public MessageResource(Message message) {
        super(message);
    }

}
