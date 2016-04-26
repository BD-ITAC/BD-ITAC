package br.ita.bditac.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


public class Sensor implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String uuid;
    
    private int tipo;
    
    private List<Topico> topicos = new ArrayList<Topico>();
    
    public Sensor() {
    	uuid = UUID.randomUUID().toString();
    	tipo = SensorTipo.Desconhecido.ordinal();
    }
    
    public Sensor(SensorTipo sensorTipo, Iterable<Topico> topicos) {
    	uuid = UUID.randomUUID().toString();
    	this.tipo = sensorTipo.ordinal();
    	
    	for(Topico topico : topicos) {
    		this.topicos.add(topico);
    	}
    }
    
    public Sensor(SensorTipo sensorTipo, Topico... topicos) {
    	this(sensorTipo, Arrays.asList(topicos));
    }
    
    public Sensor(Sensor sensor) {
    	this.tipo = sensor.tipo;
    	this.topicos = sensor.topicos;
    }

	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	
	public int getTipo() {
		return tipo;
	}
	
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
    
	
	public List<Topico> getTopicos() {
		return this.topicos;
	}
    
	
	public void setTopicos(List<Topico> topicos) {
		this.topicos = topicos;
	}
	
	
}
