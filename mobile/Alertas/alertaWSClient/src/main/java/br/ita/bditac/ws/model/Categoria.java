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

    private String description;

    public Categoria() {

    	this.description= "";

    }
    
    public Categoria(int id, String description) {

        this.id = id;
    	this.description=description;
    	
    }
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description=description;
	}
      
}
