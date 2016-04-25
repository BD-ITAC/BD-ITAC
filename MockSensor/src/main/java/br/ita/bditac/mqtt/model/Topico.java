package br.ita.bditac.mqtt.model;


public class Topico {

	private int id;
	private String descricao;
	
	private static int _id = 0;
	
	public Topico(String descricao) {
		this.id = ++_id;
		this.descricao = descricao;
	}
	
	
	public int getId() {
		return id;
	}
	
	
	public String getDescricao() {
		return descricao;
	}
	
}
