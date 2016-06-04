package br.ita.bditac.ws.model;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class IndicadoresResource extends Resource<Indicadores> {

    public IndicadoresResource() {
        super(new Indicadores());
    }

    public IndicadoresResource(Indicadores indicadores) {
        super(indicadores);
    }

    public IndicadoresResource(Indicadores indicadores, Iterable<Link> links) {
        super(indicadores, links);
    }

    public IndicadoresResource(Indicadores indicadores, Link... links) {
        super(indicadores, links);
    }

}
