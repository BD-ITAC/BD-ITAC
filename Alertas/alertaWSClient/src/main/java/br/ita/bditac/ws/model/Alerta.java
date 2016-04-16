package br.ita.bditac.ws.model;

import java.io.Serializable;

public class Alerta implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private String descricaoResumida;
    
    private String descricaoCompleta;
    
    private int fatorRiscoHumano;
    
    private int fatorRiscoMaterial;
    
    private int categoriaAlerta;
    
    private double origemLongitude;
    
    private double origemLatitude;
    
    private double origemRaioKms;

    public Alerta() {
        this.descricaoResumida = "";
        this.descricaoCompleta = "";
        this.fatorRiscoHumano = 0;
        this.fatorRiscoMaterial = 0;
        this.categoriaAlerta = 0;
        this.origemLatitude = 0;
        this.origemLongitude = 0;
        this.origemRaioKms = 0;
    }
    
    public Alerta(
            String descricaoResumida, 
            String descricaoCompleta, 
            int fatorRiscoHumano, 
            int fatorRiscoMaterial, 
            int categoriaAlerta, 
            double origemLatitude, 
            double origemLongitude, 
            double origemRaioKms) {
        this.descricaoResumida = descricaoResumida;
        this.descricaoCompleta = descricaoCompleta;
        this.fatorRiscoHumano = fatorRiscoHumano;
        this.fatorRiscoMaterial = fatorRiscoMaterial;
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
    
    
    public String getDescricaoResumida() {
        return descricaoResumida;
    }

    
    public void setDescricaoResumida(String descricaoResumida) {
        this.descricaoResumida = descricaoResumida;
    }

    
    public String getDescricaoCompleta() {
        return descricaoCompleta;
    }

    
    public void setDescricaoCompleta(String descricaoCompleta) {
        this.descricaoCompleta = descricaoCompleta;
    }

    
    public int getFatorRiscoHumano() {
        return fatorRiscoHumano;
    }

    
    public void setFatorRiscoHumano(int fatorRiscoHumano) {
        this.fatorRiscoHumano = fatorRiscoHumano;
    }

    
    public int getFatorRiscoMaterial() {
        return fatorRiscoMaterial;
    }

    
    public void setFatorRiscoMaterial(int fatorRiscoMaterial) {
        this.fatorRiscoMaterial = fatorRiscoMaterial;
    }

    
    public int getCategoriaAlerta() {
        return categoriaAlerta;
    }

    
    public void setCategoriaAlerta(int categoriaAlerta) {
        this.categoriaAlerta = categoriaAlerta;
    }

    
    public double getOrigemLongitude() {
        return origemLongitude;
    }

    
    public void setOrigemLongitude(double origemLongitude) {
        this.origemLongitude = origemLongitude;
    }

    
    public double getOrigemLatitude() {
        return origemLatitude;
    }

    
    public void setOrigemLatitude(double origemLatitude) {
        this.origemLatitude = origemLatitude;
    }

    
    public double getOrigemRaioKms() {
        return origemRaioKms;
    }

    
    public void setOrigemRaioKms(double origemRaioKms) {
        this.origemRaioKms = origemRaioKms;
    }

}
