package br.ita.bditac.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;

import br.ita.bditac.model.Crise;

public class CriseResources extends Resources<CriseResource> {

    public CriseResources() {
        super();
    }
    
    public CriseResources(Iterable<CriseResource> crises, Iterable<Link> links) {
        super(crises, links);
    }
    
    public CriseResources(Iterable<CriseResource> crises, Link... links) {
        super(crises, links);
    }
    
    public List<Crise> unwrap() {
        Collection<CriseResource> resources = getContent();
        List<Crise> list = new ArrayList<Crise>(resources.size());
        
        for(CriseResource resource : resources) {
            list.add(resource.getContent());
        }
        
        return list;
    }
    
}
