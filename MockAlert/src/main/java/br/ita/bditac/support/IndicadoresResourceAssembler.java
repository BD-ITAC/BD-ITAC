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
		return instantiateResource(indicadores);
	}
	
	@Override
	protected IndicadoresResource instantiateResource(Indicadores indicadores) {
		return new IndicadoresResource(indicadores);
	}

}
