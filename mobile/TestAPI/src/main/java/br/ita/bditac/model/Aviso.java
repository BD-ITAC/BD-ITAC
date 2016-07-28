package br.ita.bditac.model;

import java.io.Serializable;


public class Aviso implements Serializable {

    private static final long serialVersionUID = 1L;

    private String descricao;
    
    private String tipodacrise;
    
    private String datahora;
    
    private String cidade;
    
    private double latitude;
    
    private double longitude;
    
    public Aviso() {
    	
    	this.descricao = "";
    	this.tipodacrise = "";
    	this.datahora = "";
    	this.cidade = "";
    	this.latitude = 0;
    	this.longitude = 0;
    	
    }

	public Aviso(
			String descricao, 
			String tipodacrise, 
			String datahora, 
			String cidade, 
			double latitude, 
			double longitude) {
		
		super();
		
		this.descricao = descricao;
		this.tipodacrise = tipodacrise;
		this.datahora = datahora;
		this.cidade = cidade;
		this.latitude = latitude;
		this.longitude = longitude;
		
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getTipodacrise() {
		return tipodacrise;
	}
	
	public void setTipodacrise(String tipodacrise) {
		this.tipodacrise = tipodacrise;
	}
	
	public String getDatahora() {
		return datahora;
	}
	
	public void setDatahora(String datahora) {
		this.datahora = datahora;
	}
	
	public String getCidade() {
		return cidade;
	}
	
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
    
}
