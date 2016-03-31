package br.ita.bditac.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import br.ita.bditac.model.AlertaDAO;
import br.ita.bditac.model.Message;
import br.ita.bditac.service.MessageController;

@Component
public class MessageResourceAssembler extends ResourceAssemblerSupport<Message, MessageResource> {

    @Autowired
    private AlertaDAO service;

    public MessageResourceAssembler() {
        super(MessageController.class, MessageResource.class);
    }

    public MessageResource toResource(int id, String info) {
        Message message = service.obterMessageInfo(id, info);
        MessageResource resource = createResourceWithId(message, message);

        return resource;
    }

    @Override
    public MessageResource toResource(Message message) {
        MessageResource resource = createResourceWithId(message, message);

        return resource;
    }

    public List<MessageResource> toResources(List<Message> messages) {
        List<MessageResource> list = new ArrayList<MessageResource>();

        for (Message message : messages) {
            list.add(createResourceWithId(message, message));
        }

        return list;
    }

    @Override
    protected MessageResource instantiateResource(Message message) {
        return new MessageResource(message);
    }

}
