package br.ita.bditac.ws.support;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import br.ita.bditac.model.Sensor;

public class SensorResource extends Resource<Sensor> {
	
	public SensorResource() {
		super(new Sensor());
	}
	
	public SensorResource(Sensor sensor) {
		super(sensor);
	}
	
	public SensorResource(Sensor sensor, Iterable<Link> links) {
		super(sensor, links);
	}
	
	public SensorResource(Sensor sensor, Link... links) {
        super(sensor, links);
    }
    
}
