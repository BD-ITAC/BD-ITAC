package br.ita.bditac.ws.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;

import br.ita.bditac.model.Aviso;


public class AvisoResources extends Resources<AvisoResource> {

    public AvisoResources() {
        super();
    }
    
    public AvisoResources(Iterable<AvisoResource> avisos, Iterable<Link> links) {
        super(avisos, links);
    }
    
    public AvisoResources(Iterable<AvisoResource> avisos, Link... links) {
        super(avisos, links);
    }
    
    public List<Aviso> unwrap() {
        Collection<AvisoResource> resources = getContent();
        List<Aviso> list = new ArrayList<Aviso>(resources.size());
        
        for(AvisoResource resource : resources) {
            list.add(resource.getContent());
        }
        
        return list;
    }

}
