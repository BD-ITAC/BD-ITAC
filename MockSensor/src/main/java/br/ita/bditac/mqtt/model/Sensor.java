package br.ita.bditac.mqtt.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * 
 * @author BD-ITAC
 * 
 */
public class Sensor implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private UUID uuid;
    
    private Tipo tipo;
    
    public Sensor() {
    	uuid = UUID.randomUUID();
    	tipo = Tipo.Desconhecido;
    }
    
    
    public Sensor(Tipo tipo) {
    	uuid = UUID.randomUUID();
    	this.tipo = tipo;
    }

	
	public UUID getUuid() {
		return uuid;
	}

	
	public Tipo getTipo() {
		return tipo;
	}
    
    
}
