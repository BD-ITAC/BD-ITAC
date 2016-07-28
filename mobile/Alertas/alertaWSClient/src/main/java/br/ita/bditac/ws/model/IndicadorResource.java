package br.ita.bditac.ws.model;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;


public class IndicadorResource extends Resource<Indicador> {
	
	public IndicadorResource() {
		super(new Indicador());
	}
	
	public IndicadorResource(Indicador indicador) {
		super(indicador);
	}
	
	public IndicadorResource(Indicador indicador, Iterable<Link> links) {
		super(indicador, links);
	}
	
	public IndicadorResource(Indicador indicador, Link... links) {
		super(indicador, links);
	}

}
