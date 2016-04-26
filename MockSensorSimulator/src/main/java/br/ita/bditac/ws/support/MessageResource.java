package br.ita.bditac.ws.support;

import org.springframework.hateoas.Resource;

import br.ita.bditac.model.Mensagem;

public class MessageResource extends Resource<Mensagem> {
    
    public MessageResource() {
    	super(new Mensagem());
    }

    public MessageResource(Mensagem mensagem) {
        super(mensagem);
    }

}
