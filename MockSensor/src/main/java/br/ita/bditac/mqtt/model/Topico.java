package br.ita.bditac.mqtt.model;


public enum Topico {
	
	Diagnosticos("br.ita.bditac/diagnostico"),
	NivelAgua("br.ita.bditac/nivel.agua");
	
	private final String id;
	
	private Topico(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
	
	public boolean equalsName(String id) {
		return (id == null) ? false : id.equals(id);
	}
	
	public String toString() {
		return this.id;
	}

}
