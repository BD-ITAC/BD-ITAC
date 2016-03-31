package br.ita.bditac.support;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import br.ita.bditac.model.Evento;

public class EventoResource extends Resource<Evento> {

    public EventoResource() {
        super(new Evento());
    }
    
    public EventoResource(Evento evento) {
        super(evento);
    }

    public EventoResource(Evento evento, Iterable<Link> links) {
        super(evento, links);
    }
    
    public EventoResource(Evento evento, Link... links) {
        super(evento, links);
    }
    
}
