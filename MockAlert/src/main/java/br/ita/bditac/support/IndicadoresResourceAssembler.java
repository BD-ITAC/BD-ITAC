package br.ita.bditac.support;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import br.ita.bditac.model.Indicadores;
import br.ita.bditac.service.IndicadoresController;

@Component
public class IndicadoresResourceAssembler extends ResourceAssemblerSupport<Indicadores, IndicadoresResource> {
	
	public IndicadoresResourceAssembler() {
		super(IndicadoresController.class, IndicadoresResource.class);
	}
	
	public IndicadoresResource toResource(Indicadores indicadores) {
		IndicadoresResource resource = createResourceWithId(0, indicadores);
		
		return resource;
	}
	
	@Override
	protected IndicadoresResource instantiateResource(Indicadores indicadores) {
		return new IndicadoresResource(indicadores);
	}

}
