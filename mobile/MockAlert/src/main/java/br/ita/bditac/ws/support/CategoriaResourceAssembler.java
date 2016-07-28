package br.ita.bditac.ws.support;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import br.ita.bditac.model.Categoria;
import br.ita.bditac.ws.service.CategoriaController;


@Component
public class CategoriaResourceAssembler extends ResourceAssemblerSupport<Categoria, CategoriaResource> {
	
	public CategoriaResourceAssembler() {
		super(CategoriaController.class, CategoriaResource.class);
	}
	
	public CategoriaResource toResource(Categoria categoria) {
		return instantiateResource(categoria);
	}
	
	@Override
	protected CategoriaResource instantiateResource(Categoria categoria) {
		return new CategoriaResource(categoria);
	}

}
