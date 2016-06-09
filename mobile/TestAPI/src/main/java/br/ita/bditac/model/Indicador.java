package br.ita.bditac.model;

import java.io.Serializable;

public class Indicador implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private static int _id;
    
    private int id;

    public Integer getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    private String descricao;
    
    private long valor;
    
    static {
    	_id = 0;
    }
    
    public Indicador() {
    	this.id = 0;
    	
    	this.descricao = "";
    }
    
    public Indicador(String descricao, long valor) {
        
        this.id = ++_id;
        
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
