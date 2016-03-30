package br.ita.bditac.support;

import org.springframework.hateoas.ResourceSupport;

import br.ita.bditac.model.Evento;

public class EventoResource extends ResourceSupport {
    
    private Evento evento;
    
    public EventoResource(Evento evento) {
        super();
        
        this.evento = evento;
    }
    
    public Evento getEvento() {
        return evento;
    }

}
