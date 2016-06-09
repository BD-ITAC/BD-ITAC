package br.ita.bditac.model;

import java.io.Serializable;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author BD-ITAC
 * 
 * A classe Alerta contém os dados necessários para dar suporte às aplicações periféricas na tomada 
 * de decisões durante e logo após a ocorrência de uma crise.
 * 
 * O Alerta emitido por uma aplicação de gerenciamento é uma classe especializada para atendimento 
 * das necessidades imediatas do usuário de aplicativo que deverá ser avisado, ou ainda de um 
 * dispositivo que irá atuar, nos primeiros momentos de uma situação de crise.
 *
 */
public class Alerta implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private static int _id;
    
    private int id;
    
    private long timestamp;

    public Integer getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Breve descrição da crise
     */
    private String descricaoResumida;
    
    /**
     * Descrição completa da crise com detalhes de causas e conseqüências
     */
    private String descricaoCompleta;
   
    /**
     * Indica o tipo de alerta:
     * - Enchente
     * - Incêndio
     * - Deslizamento (terra, encostas, etc.)
     * - Desmoronamento (prédios, equipamentos públicos (torres, etc.) e instalações industriais
     * - Terremotos
     * - Epidemias
     * - Apocalipse Zumbi
     * - etc.
     */
    private int categoriaAlerta;
    
    /**
     * Coordenadas do ponto de origem da crise - latitude
     */
    private double origemLatitude;
    
    /**
     * Coordenadas do ponto de origem da crise - longitute
     */
    private double origemLongitude;
    
    /**
     * Coordenadas do ponto de origem da crise - área de abrangência em kilometros
     */
    private double origemRaioKms;
    
    static {
    	_id = 0;
    }
    
    public Alerta() {
    	this.id = 0;
    	
    	this.timestamp = 0;
        this.descricaoResumida = "";
        this.descricaoCompleta = "";
        this.categoriaAlerta = 0;
        this.origemLatitude = 0;
        this.origemLongitude = 0;
        this.origemRaioKms = 0;
    }
    
    public Alerta(
            String descricaoResumida, 
            String descricaoCompleta, 
            int categoriaAlerta, 
            double origemLatitude, 
            double origemLongitude, 
            double origemRaioKms) {
        
        this.id = ++_id;
    	
    	this.timestamp = DateTime.now().getMillis();
        this.descricaoResumida = descricaoResumida;
        this.descricaoCompleta = descricaoCompleta;
        this.categoriaAlerta = categoriaAlerta;
        this.origemLatitude = origemLatitude;
        this.origemLongitude = origemLongitude;
        this.origemRaioKms = origemRaioKms;
    }   
    
    
    @JsonIgnore
	public long getTimestamp() {
		return timestamp;
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

    
    public int getCategoriaAlerta() {
        return categoriaAlerta;
    }

    
    public void setCategoriaAlerta(int categoriaAlerta) {
        this.categoriaAlerta = categoriaAlerta;
    }

    
    public double getOrigemLatitude() {
        return origemLatitude;
    }

    
    public void setOrigemLatitude(double origemLatitude) {
        this.origemLatitude = origemLatitude;
    }

    
    public double getOrigemLongitude() {
        return origemLongitude;
    }

    
    public void setOrigemLongitude(double origemLongitude) {
        this.origemLongitude = origemLongitude;
    }

    
    public double getOrigemRaioKms() {
        return origemRaioKms;
    }

    
    public void setOrigemRaioKms(double origemRaioKms) {
        this.origemRaioKms = origemRaioKms;
    }
    
}
