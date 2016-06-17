package br.ita.bditac.model;

import java.io.Serializable;

public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int id;

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    private String description;
    
    public Categoria() {
        
        this.id = 0;
        
    	this.description = "";
    	
    }
    
    public Categoria(int id, String descricao) {
        
        this.id = id;
        
    	this.description = descricao;
    	
    }
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
      
}
