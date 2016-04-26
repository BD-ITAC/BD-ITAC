package br.ita.bditac.ws.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import br.ita.bditac.model.Mensagem;
import br.ita.bditac.model.SensorDAO;
import br.ita.bditac.ws.service.MessageController;

@Component
public class MessageResourceAssembler extends ResourceAssemblerSupport<Mensagem, MessageResource> {

    public MessageResourceAssembler() {
        super(MessageController.class, MessageResource.class);
    }

    public MessageResource toResource(int id, String info) {
        Mensagem mensagem = SensorDAO.obterMessageInfo(id, info);
        MessageResource resource = createResourceWithId(mensagem, mensagem);

        return resource;
    }

    @Override
    public MessageResource toResource(Mensagem mensagem) {
        MessageResource resource = createResourceWithId(mensagem, mensagem);

        return resource;
    }

    public List<MessageResource> toResources(List<Mensagem> mensagens) {
        List<MessageResource> list = new ArrayList<MessageResource>();

        for (Mensagem mensagem : mensagens) {
            list.add(createResourceWithId(mensagem, mensagem));
        }

        return list;
    }

    @Override
    protected MessageResource instantiateResource(Mensagem mensagem) {
        return new MessageResource(mensagem);
    }

}
