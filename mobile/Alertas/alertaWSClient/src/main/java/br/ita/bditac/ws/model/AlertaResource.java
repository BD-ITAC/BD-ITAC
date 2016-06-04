package br.ita.bditac.ws.model;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

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
