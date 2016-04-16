package br.ita.bditac.support;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import br.ita.bditac.model.Evento;
import br.ita.bditac.service.EventoController;

@Component
public class EventoResourceAssembler extends ResourceAssemblerSupport<Evento, EventoResource> {

    public EventoResourceAssembler() {
        super(EventoController.class, EventoResource.class);
    }
    
    public EventoResource toResource(Evento evento) {
        EventoResource resource = createResourceWithId(evento.getId(), evento);
        
        return resource;
    }
    
    @Override
    protected EventoResource instantiateResource(Evento evento) {
        return new EventoResource(evento);
    }
    
}
