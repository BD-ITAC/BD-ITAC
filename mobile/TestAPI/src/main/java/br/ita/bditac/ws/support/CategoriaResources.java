package br.ita.bditac.ws.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;

import br.ita.bditac.model.Categoria;

public class CategoriaResources extends Resources<CategoriaResource> {

	public CategoriaResources() {
		super();
	}
	
	public CategoriaResources(Iterable<CategoriaResource> categorias, Iterable<Link> links) {
		super(categorias, links);
	}
	
	public CategoriaResources(Iterable<CategoriaResource> categorias, Link... links) {
		super(categorias, links);
	}
	
	public List<Categoria> unwrap() {
		Collection<CategoriaResource> resources = getContent();
		List<Categoria> list = new ArrayList<Categoria>(resources.size());
		
		for(CategoriaResource resource : resources) {
			list.add(resource.getContent());
		}
		
		return list;
	}

}
