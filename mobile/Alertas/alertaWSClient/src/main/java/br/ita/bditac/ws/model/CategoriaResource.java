package br.ita.bditac.ws.model;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;


public class CategoriaResource extends Resource<Categoria> {

	public CategoriaResource() {
		super (new Categoria());
	}
	
	public CategoriaResource(Categoria categoria) {
		super(categoria);
	}
	
	public CategoriaResource(Categoria categoria, Iterable<Link> links) {
		super(categoria, links);
	}
	
	public CategoriaResource(Categoria categoria, Link... links) {
		super(categoria, links);
	}
	
}
