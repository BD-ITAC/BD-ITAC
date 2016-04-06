package br.ita.bditac.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AlertaResource extends Resource<Alerta> {

    public AlertaResource() {
        super(new Alerta());
    }

    public AlertaResource(Alerta alerta) {
        super(alerta);
    }

    @JsonIgnore
    public AlertaResource(Alerta alerta, Iterable<Link> links) {
        super(alerta, links);
    }

    @JsonIgnore
    public AlertaResource(Alerta alerta, Link... links) {
        super(alerta, links);
    }

}
