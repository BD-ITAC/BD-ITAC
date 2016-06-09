package br.ita.bditac.ws.support;

import org.springframework.hateoas.Resource;

import br.ita.bditac.model.AlternateMessage;


public class AlternateMessageResource extends Resource<AlternateMessage> {
    
    public AlternateMessageResource() {
    	super(new AlternateMessage());
    }

    public AlternateMessageResource(AlternateMessage alternateMessage) {
        super(alternateMessage);
    }

}
