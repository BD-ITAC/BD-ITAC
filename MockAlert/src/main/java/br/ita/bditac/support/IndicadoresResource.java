package br.ita.bditac.support;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import br.ita.bditac.model.Indicadores;

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
