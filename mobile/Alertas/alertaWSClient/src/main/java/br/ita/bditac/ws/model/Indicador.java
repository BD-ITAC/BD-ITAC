package br.ita.bditac.ws.model;

import java.io.Serializable;


public class Indicador implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private String descricao;
    
    private long valor;

    public Indicador() {

    	this.descricao = "";

    }
    
    public Indicador(int id, String descricao, long valor) {

		this.id = id;
    	this.descricao = descricao;
    	this.valor = valor;
    	
    }

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public long getValor() {
		return valor;
	}
	
	public void setValor(long valor) {
		this.valor = valor;
	}
	
}
