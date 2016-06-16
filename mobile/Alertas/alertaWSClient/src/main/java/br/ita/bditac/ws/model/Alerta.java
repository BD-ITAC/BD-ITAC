package br.ita.bditac.ws.model;

import java.io.Serializable;


public class Alerta implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int id;

    private String timestamp;
    
    private String descricaoResumida;
    
    private String descricaoCompleta;

    private String categoriaAlerta;

    private double origemLatitude;

    private double origemLongitude;

    private double origemRaioKms;

    protected Alerta() {
        this.id = 0;
        this.timestamp = "";
        this.descricaoResumida = "";
        this.descricaoCompleta = "";
        this.categoriaAlerta = "";
        this.origemLatitude = 0;
        this.origemLongitude = 0;
        this.origemRaioKms = 0;
    }

    protected Alerta(
            int id,
            String timestamp,
            String descricaoResumida, 
            String descricaoCompleta, 
            String categoriaAlerta,
            double origemLatitude, 
            double origemLongitude, 
            double origemRaioKms) {
        this.id = id;
        this.timestamp = timestamp;
        this.descricaoResumida = descricaoResumida;
        this.descricaoCompleta = descricaoCompleta;
        this.categoriaAlerta = categoriaAlerta;
        this.origemLatitude = origemLatitude;
        this.origemLongitude = origemLongitude;
        this.origemRaioKms = origemRaioKms;

    }

    public int getId() {
        return id;
    }

    
    public void setId(int id) {
        this.id = id;
    }


    public String getTimestamp() {
        return timestamp;
    }


    public String getDescricaoResumida() {
        return descricaoResumida;
    }

    
    public String getDescricaoCompleta() {
        return descricaoCompleta;
    }

    
    public String getCategoriaAlerta() {
        return categoriaAlerta;
    }

    
    public double getOrigemLatitude() {
        return origemLatitude;
    }


    public double getOrigemLongitude() {
        return origemLongitude;
    }

    
    public double getOrigemRaioKms() {
        return origemRaioKms;
    }

}
