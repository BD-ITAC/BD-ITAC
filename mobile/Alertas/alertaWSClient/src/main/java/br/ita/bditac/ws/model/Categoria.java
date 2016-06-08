package br.ita.bditac.ws.model;

import java.io.Serializable;


public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    public Integer getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    private String descricao;

    public Categoria() {

    	this.descricao = "";

    }
    
    public Categoria(String descricao) {

    	this.descricao = descricao;
    	
    }
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
      
}
