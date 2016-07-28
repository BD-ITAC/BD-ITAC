package br.ita.bditac.ws.model;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class IndicadorResources extends Resources<IndicadorResource> {

	public IndicadorResources() {
		super();
	}
	
	public IndicadorResources(Iterable<IndicadorResource> indicadores, Iterable<Link> links) {
		super(indicadores, links);
	}
	
	public IndicadorResources(Iterable<IndicadorResource> indicadores, Link... links) {
		super(indicadores, links);
	}
	
	public List<Indicador> unwrap() {
		Collection<IndicadorResource> resources = getContent();
		List<Indicador> list = new ArrayList<Indicador>(resources.size());
		
		for(IndicadorResource resource : resources) {
			list.add(resource.getContent());
		}
		
		return list;
	}
	
}
