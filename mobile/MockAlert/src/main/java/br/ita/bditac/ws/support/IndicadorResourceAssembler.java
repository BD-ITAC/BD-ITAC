package br.ita.bditac.ws.support;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import br.ita.bditac.model.Indicador;
import br.ita.bditac.ws.service.IndicadorController;


@Component
public class IndicadorResourceAssembler extends ResourceAssemblerSupport<Indicador, IndicadorResource> {
	
	public IndicadorResourceAssembler() {
		super(IndicadorController.class, IndicadorResource.class);
	}
	
	public IndicadorResource toResource(Indicador indicador) {
		return instantiateResource(indicador);
	}
	
	@Override
	protected IndicadorResource instantiateResource(Indicador indicador) {
		return new IndicadorResource(indicador);
	}

}
