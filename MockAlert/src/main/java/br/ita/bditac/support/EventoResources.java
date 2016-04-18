package br.ita.bditac.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;

import br.ita.bditac.model.Evento;

public class EventoResources extends Resources<EventoResource> {

    public EventoResources() {
        super();
    }
    
    public EventoResources(Iterable<EventoResource> eventos, Iterable<Link> links) {
        super(eventos, links);
    }
    
    public EventoResources(Iterable<EventoResource> eventos, Link... links) {
        super(eventos, links);
    }
    
    public List<Evento> unwrap() {
        Collection<EventoResource> resources = getContent();
        List<Evento> list = new ArrayList<Evento>(resources.size());
        
        for(EventoResource resource : resources) {
            list.add(resource.getContent());
        }
        
        return list;
    }
    
}
