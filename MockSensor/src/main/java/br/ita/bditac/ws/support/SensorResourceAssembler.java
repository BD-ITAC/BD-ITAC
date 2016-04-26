package br.ita.bditac.ws.support;


import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import br.ita.bditac.model.Sensor;
import br.ita.bditac.ws.service.SensorController;


@Component
public class SensorResourceAssembler extends ResourceAssemblerSupport<Sensor, SensorResource> {

	public SensorResourceAssembler() {
		super(SensorController.class, SensorResource.class);
	}
	
	public SensorResource toResource(Sensor topico) {
		SensorResource resource = createResourceWithId(topico.getUuid(), topico);
		
		return resource;
	}
	
	
	@Override
	protected SensorResource instantiateResource(Sensor topico) {
		return new SensorResource(topico);
	}
	
}
