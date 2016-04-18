package br.ita.bditac.support;

import org.springframework.hateoas.ResourceSupport;

import br.ita.bditac.model.Message;

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
