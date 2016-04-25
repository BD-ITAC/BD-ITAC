package br.ita.bditac.mqtt.model;


import java.io.Serializable;
import java.util.UUID;


public class Sensor implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private UUID uuid;
    
    private SensorTipo sensorTipo;
    
    public Sensor() {
    	uuid = UUID.randomUUID();
    	sensorTipo = SensorTipo.Desconhecido;
    }
    
    
    public Sensor(SensorTipo sensorTipo) {
    	uuid = UUID.randomUUID();
    	this.sensorTipo = sensorTipo;
    }

	
	public UUID getUuid() {
		return uuid;
	}

	
	public SensorTipo getTipo() {
		return sensorTipo;
	}
    
    
}
