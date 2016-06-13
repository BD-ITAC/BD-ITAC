package br.ita.bditac.ws.support;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import br.ita.bditac.model.Indicador;


public class IndicadorResource extends Resource<Indicador> {
	
	public IndicadorResource() {
		super(new Indicador(null, null, 0));
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
