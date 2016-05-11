package br.ita.bditac.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventoResource extends Resource<Evento> {

    public EventoResource() {
        super(new Evento());
    }

    public EventoResource(Evento evento) {
        super(evento);
    }

    @JsonIgnore
    public EventoResource(Evento evento, Iterable<Link> links) {
        super(evento, links);
    }

    @JsonIgnore
    public EventoResource(Evento evento, Link... links) {
        super(evento, links);
    }

}
