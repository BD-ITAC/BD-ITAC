package br.ita.bditac.model;

import java.io.Serializable;

public class Indicador implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String id;

    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    private String descricao;
    
    private long valor;
    
    public Indicador() {
        
        this.id = "";
        
    	this.descricao = "";
    	this.valor = 0;
    	
    }
    
    public Indicador(String id, String descricao, long valor) {
        
        this.id = id;
        
    	this.descricao = descricao;
    	this.valor = valor;
    	
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
