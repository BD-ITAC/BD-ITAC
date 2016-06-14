package br.ita.bditac.ws.support;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import br.ita.bditac.model.Aviso;


public class AvisoResource extends Resource<Aviso> {
    
    public AvisoResource() {
        super(new Aviso());
    }
    
    public AvisoResource(Aviso aviso) {
        super(aviso);
    }
    
    public AvisoResource(Aviso aviso, Iterable<Link> links) {
        super(aviso, links);
    }
    
    public AvisoResource(Aviso aviso, Link... links) {
        super(aviso, links);
    }

}
