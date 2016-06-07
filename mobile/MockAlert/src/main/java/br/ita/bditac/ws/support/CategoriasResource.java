package br.ita.bditac.ws.support;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import br.ita.bditac.model.Categorias;


public class CategoriasResource extends Resource<Categorias> {

	public CategoriasResource() {
		super (new Categorias());
	}
	
	public CategoriasResource(Categorias categorias) {
		super(categorias);
	}
	
	public CategoriasResource(Categorias categorias, Iterable<Link> links) {
		super(categorias, links);
	}
	
	public CategoriasResource(Categorias categorias, Link... links) {
		super(categorias, links);
	}
	
}
