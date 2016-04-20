package br.ita.bditac.ws.support;

import org.springframework.hateoas.ResourceSupport;

import br.ita.bditac.ws.model.Message;

public class MessageResource extends ResourceSupport {

    private Message message;

    public MessageResource(Message message) {
        super();

        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

}
