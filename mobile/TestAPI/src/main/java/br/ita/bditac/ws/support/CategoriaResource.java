package br.ita.bditac.ws.support;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import br.ita.bditac.model.Categoria;


public class CategoriaResource extends Resource<Categoria> {

	public CategoriaResource() {
		super (new Categoria(0, null));
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
