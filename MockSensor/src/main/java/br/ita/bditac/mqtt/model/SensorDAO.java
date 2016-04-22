package br.ita.bditac.mqtt.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class SensorDAO {

	private Map<UUID, Sensor> sensores = new HashMap<UUID, Sensor>();
	
	public UUID adicionaSensor(Tipo tipo) {
		Sensor sensor = new Sensor(tipo);
		
		sensores.put(sensor.getUuid(), sensor);
		
		return sensor.getUuid();
	}
	
	public Sensor obterSensor(UUID uuid) {
		return sensores.get(uuid);
	}
	
}
