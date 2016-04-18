package br.ita.bditac.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AlertaResources extends Resources<AlertaResource> {

    public AlertaResources() {
        super();
    }

    @JsonIgnore
    public AlertaResources(Iterable<AlertaResource> alertas, Iterable<Link> links) {
        super(alertas, links);
    }

    @JsonIgnore
    public AlertaResources(Iterable<AlertaResource> alertas, Link... links) {
        super(alertas, links);
    }

    public List<Alerta> unwrap() {
        Collection<AlertaResource> resources = getContent();
        List<Alerta> list = new ArrayList<Alerta>(resources.size());

        for(AlertaResource resource : resources) {
            list.add(resource.getContent());
        }

        return list;
    }

}
