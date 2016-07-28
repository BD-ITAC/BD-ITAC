package br.ita.bditac.model;

import java.io.Serializable;

public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private static int _id;
    
    private int id;

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    private String descricao;
    
    static {
    	_id = 0;
    }
    
    public Categoria() {
    	this.id = 0;
    	
    	this.descricao = "";
    }
    
    public Categoria(String descricao) {
        
        this.id = ++_id;
        
    	this.descricao = descricao;
    	
    }
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
      
}
