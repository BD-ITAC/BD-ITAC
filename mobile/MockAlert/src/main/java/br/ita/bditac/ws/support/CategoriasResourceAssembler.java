package br.ita.bditac.ws.support;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import br.ita.bditac.model.Categorias;
import br.ita.bditac.ws.service.CategoriasController;


@Component
public class CategoriasResourceAssembler extends ResourceAssemblerSupport<Categorias, CategoriasResource> {
	
	public CategoriasResourceAssembler() {
		super(CategoriasController.class, CategoriasResource.class);
	}
	
	public CategoriasResource toResource(Categorias categorias) {
		return instantiateResource(categorias);
	}
	
	@Override
	protected CategoriasResource instantiateResource(Categorias categorias) {
		return new CategoriasResource(categorias);
	}

}
