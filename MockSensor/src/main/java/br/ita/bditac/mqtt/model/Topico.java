package br.ita.bditac.mqtt.model;


public enum Topico {
	
	Diagnosticos("br.ita.bditac/diagnostico"),
	NivelAgua("br.ita.bditac/nivel.agua");
	
	private final String nome;
	
	private Topico(String nome) {
		this.nome = nome;
	}
	
	public boolean equalsName(String nome) {
		return (nome == null) ? false : nome.equals(nome);
	}
	
	public String toString() {
		return this.nome;
	}

}
