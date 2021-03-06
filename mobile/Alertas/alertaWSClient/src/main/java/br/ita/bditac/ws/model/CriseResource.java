package br.ita.bditac.ws.model;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class CriseResource extends Resource<Crise> {

    public CriseResource() {
        super(new Crise());
    }

    public CriseResource(Crise crise) {
        super(crise);
    }

    public CriseResource(Crise crise, Iterable<Link> links) {
        super(crise, links);
    }

    public CriseResource(Crise crise, Link... links) {
        super(crise, links);
    }

}
