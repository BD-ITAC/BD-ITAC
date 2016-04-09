package br.ita.bditac.ws.model;

import java.io.Serializable;

public class Evento implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private String descricao;

    private int categoria;
    
    private String nome;
    
    private String email;
    
    private String telefone;

    private double latitude;

    private double longitude;
    
    public Evento() {
        this.id = 0;
        
        this.descricao = "";
        this.categoria = 0;
        this.nome = "";
        this.email = "";
        this.telefone = "";
    }
    
    public Evento(
            String descricao,
            int categoria,
            String nome,
            String email,
            String telefone,
            double latitude,
            double longitude) {
        this.id = 0;
        
        this.descricao = descricao;
        this.categoria = categoria;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }
    
    
    public int getId() {
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


    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


    public double getLatitude() {
        return latitude;
    }


    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    public double getLongitude() {
        return longitude;
    }

}
