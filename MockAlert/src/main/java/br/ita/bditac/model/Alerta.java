package br.ita.bditac.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author BD-ITAC
 * 
 * A classe Alerta contém os dados necessários para dar suporte às aplicações periféricas na tomada 
 * de decisões durante e logo após a ocorrência de um evento.
 * 
 * O Alerta emitido por uma aplicação de gerenciamento é uma classe especializada para atendimento 
 * das necessidades imediatas do usuário de aplicativo que deverá ser avisado, ou ainda de um 
 * dispositivo que irá atuar, nos primeiros momentos de uma situação de crise.
 *
 */
public class Alerta implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int id;

    @JsonIgnore
    public Integer getId() {
        return id;
    }
    
    @JsonIgnore
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Breve descrição do evento
     */
    private String descricaoResumida;
    
    /**
     * Descrição completa do evento com detalhes de causas e conseqüências
     */
    private String descricaoCompleta;
    
    /**
     * Fator de risco para a vida humana:
     * - Crítico:
     *      Pode causar mortes ou ferimentos gravíssimos que impossibilitem a sobrevivência de 
     *      indivíduos envolvidos e não podem ser tratados no local do evento - os indivíduos 
     *      devem ser imediamente deslocados e receberem tratamento durante o transporte
     * - Sério:
     *      Pode causar ferimentos maiores que impossibilitem a locomoção porém sem risco 
     *      imediato de vida para os indivícuos envolvidos e não podem ser tratados no local 
     *      do evento - os indivíduos devem receber tratamento prévio e imediatamente deslocados
     * - Moderado:
     *      Pode causar ferimentos graves mas que possibilitam os indivídus a se locomoverem por 
     *      conta própria do local de risco e devem ser tratados em curto prazo no local do evento
     * - Menor:
     *      Pode causar ferimentos leves que podem ser tratados em médio prazo fora do local do evento
     * - Negligenciável:
     *      Pode causar pequenos ferimentos que não precisam ser tratados no local do evento
     */
    private int fatorRiscoHumano;
    
    /**
     * Fator de risco para instalações e equipamentos:
     * - Crítico:
     *      Pode causar destruição completa ou irrecuperável de instalações e equipamentos no local
     *      do evento e terão de ser reconstruídos ou reinstalados
     * - Sério:
     *      Pode causar destruição parcial mas reparável às instalações e equipmentos no local do 
     *      evento e seu uso ficará impossibilitado durante os reparos
     * - Moderado:
     *      Pode causar danos moderados mas reparáveis às instalações e equipamentos no local e seu uso 
     *      ficará impossibilitado durante os reparos
     * - Menor:
     *      Pode causar danos moderados mas reparáveis às instalações e equipamentos no local mas
     *      seu uso poderá prosseguir durante os reparos
     * - Negligenciável:
     *      Pode causar pequenos danos às instalações e equipamentos no local e seu uso poderá prosseguir
     *      durante os reparos
     */
    private int fatorRiscoMaterial;
    
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
     * Coordenadas do ponto de origem do evento - latitude
     */
    private double origemLatitude;
    
    /**
     * Coordenadas do ponto de origem do evento - longitute
     */
    private double origemLongitude;
    
    /**
     * Coordenadas do ponto de origem do evento - área de abrangência em kilometros
     */
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
