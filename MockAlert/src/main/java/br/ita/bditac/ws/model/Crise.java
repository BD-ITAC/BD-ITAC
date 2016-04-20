package br.ita.bditac.ws.model;

import java.io.Serializable;

import org.apache.commons.codec.binary.Base64;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author BD-ITAC
 * 
 * A classe Crise contém os dados necessários para permitir o cadastramento de um crise ad-hoc
 *
 */
public class Crise implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private static int _id;
    
    private int id;

    @JsonIgnore
    public Integer getId() {
        return id;
    }
    
    @JsonIgnore
    public void setId(int id) {
        this.id = id;
    }
    
    private String descricao;
    
    /**
     * Indica o tipo de crise:
     * - Enchente
     * - Incêndio
     * - Deslizamento (de terra ou pedras em encostas, margens de rio, lagos ou mar, etc.)
     * - Desmoronamento (prédios, equipamentos públicos (torres, etc.) e instalações industriais
     * - Terremotos
     * - Epidemias
     * - Apocalipse Zumbi
     * - etc.
     */
    private int categoria;
    
    private String nome;
    
    private String email;
    
    private String telefone;
    
    private double latitude;
    
    private double longitude;
    
    private byte[] fotografia;
    
    static {
    	_id = 0;
    }
    
    public Crise() {
        this.id = 0;
        
        this.descricao = "";
        this.categoria = 0;
        this.nome = "";
        this.email = "";
        this.telefone = "";
        this.latitude = 0D;
        this.longitude = 0D;
        this.fotografia = null;
    }
    
    public Crise(
            String descricao,
            int categoria,
            String nome,
            String email,
            String telefone,
            double latitude,
            double longitude,
            String fotografia) {
    	
        this.id = ++_id;
        
        this.descricao = descricao;
        this.categoria = categoria;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.latitude = latitude;
        this.longitude = longitude;
    	this.fotografia = Base64.encodeBase64(fotografia.getBytes());
    }

    
    public String getDescricao() {
        return descricao;
    }

    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    
    public int getCategoria() {
        return categoria;
    }

    
    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }
    

    public String getNome() {
        return nome;
    }

    
    public void setNome(String nome) {
        this.nome = nome;
    }

    
    public String getEmail() {
        return email;
    }

    
    public void setEmail(String email) {
        this.email = email;
    }

    
    public String getTelefone() {
        return telefone;
    }

    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    
    public static long getSerialversionuid() {
        return serialVersionUID;
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
	
	
	public String getFotografia() {
		return new String(fotografia);
	}
	
	
	public void setFotografia(String fotografia) {
		this.fotografia = fotografia.getBytes();
	}
    
}
