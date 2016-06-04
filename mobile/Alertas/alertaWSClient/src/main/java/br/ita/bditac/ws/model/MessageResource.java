package br.ita.bditac.ws.model;


import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class MessageResource extends Resource<Message> {

    public MessageResource() {
        super(new Message());
    }

    public MessageResource(Message message) {
        super(message);
    }

    public MessageResource(Message message, Iterable<Link> links) {
        super(message, links);
    }

    public MessageResource(Message message, Link... links) {
        super(message, links);
    }

}
