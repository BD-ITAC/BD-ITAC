package br.ita.bditac.ws.model;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

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
