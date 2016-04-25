package br.ita.bditac.mqtt.model;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;


@Component
public class SensorDAO {

	private static Map<UUID, Sensor> sensores = new HashMap<UUID, Sensor>();
	
	private static Map<Integer, Topico> topicos = new HashMap<Integer, Topico>();
	
	public static UUID adicionaSensor(SensorTipo sensorTipo) {
		Sensor sensor = new Sensor(sensorTipo);
		
		sensores.put(sensor.getUuid(), sensor);
		
		return sensor.getUuid();
	}
	
	
	public static Sensor obterSensor(UUID uuid) {
		return sensores.get(uuid);
	}
	
	
	public static int adicionarTopico(String descricao) {
		Topico novoTopico = new Topico(descricao);
		
		topicos.put(novoTopico.getId(), novoTopico);
		
		return novoTopico.getId();
	}
	
	
	public static Topico obterTopico(int id) {
		return topicos.get(id);
	}
	
}
