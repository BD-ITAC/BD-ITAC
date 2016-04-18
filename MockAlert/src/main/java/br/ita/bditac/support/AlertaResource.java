package br.ita.bditac.support;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import br.ita.bditac.model.Alerta;

public class AlertaResource extends Resource<Alerta> {
    
    public AlertaResource() {
        super(new Alerta());
    }
    
    public AlertaResource(Alerta alerta) {
        super(alerta);
    }
    
    public AlertaResource(Alerta alerta, Iterable<Link> links) {
        super(alerta, links);
    }
    
    public AlertaResource(Alerta alerta, Link... links) {
        super(alerta, links);
    }
    
}
