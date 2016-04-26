package br.ita.bditac.ws.support;


import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import br.ita.bditac.model.Topico;
import br.ita.bditac.ws.service.TopicoController;


@Component
public class TopicoResourceAssembler extends ResourceAssemblerSupport<Topico, TopicoResource> {

	public TopicoResourceAssembler() {
		super(TopicoController.class, TopicoResource.class);
	}
	
	public TopicoResource toResource(Topico topico) {
		TopicoResource resource = createResourceWithId(topico.getId(), topico);
		
		return resource;
	}
	
	
	@Override
	protected TopicoResource instantiateResource(Topico topico) {
		return new TopicoResource(topico);
	}

}
