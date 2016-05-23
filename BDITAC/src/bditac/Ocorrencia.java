/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bditac;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Henrique
 */
@Entity
@Table(name = "ocorrencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ocorrencia.findAll", query = "SELECT o FROM Ocorrencia o"),
    @NamedQuery(name = "Ocorrencia.findByOcrId", query = "SELECT o FROM Ocorrencia o WHERE o.ocrId = :ocrId"),
    @NamedQuery(name = "Ocorrencia.findByCriId", query = "SELECT o FROM Ocorrencia o WHERE o.criId = :criId"),
    @NamedQuery(name = "Ocorrencia.findByCapId", query = "SELECT o FROM Ocorrencia o WHERE o.capId = :capId"),
    @NamedQuery(name = "Ocorrencia.findByApiId", query = "SELECT o FROM Ocorrencia o WHERE o.apiId = :apiId"),
    @NamedQuery(name = "Ocorrencia.findByOcrIdApi", query = "SELECT o FROM Ocorrencia o WHERE o.ocrIdApi = :ocrIdApi"),
    @NamedQuery(name = "Ocorrencia.findByOcrCriacao", query = "SELECT o FROM Ocorrencia o WHERE o.ocrCriacao = :ocrCriacao"),
    @NamedQuery(name = "Ocorrencia.findByOcrUsuId", query = "SELECT o FROM Ocorrencia o WHERE o.ocrUsuId = :ocrUsuId"),
    @NamedQuery(name = "Ocorrencia.findByOcrUsuNome", query = "SELECT o FROM Ocorrencia o WHERE o.ocrUsuNome = :ocrUsuNome"),
    @NamedQuery(name = "Ocorrencia.findByOcrUsuScreenNome", query = "SELECT o FROM Ocorrencia o WHERE o.ocrUsuScreenNome = :ocrUsuScreenNome"),
    @NamedQuery(name = "Ocorrencia.findByOcrFonte", query = "SELECT o FROM Ocorrencia o WHERE o.ocrFonte = :ocrFonte"),
    @NamedQuery(name = "Ocorrencia.findByOcrLingua", query = "SELECT o FROM Ocorrencia o WHERE o.ocrLingua = :ocrLingua"),
    @NamedQuery(name = "Ocorrencia.findByOcrPaisCodigo", query = "SELECT o FROM Ocorrencia o WHERE o.ocrPaisCodigo = :ocrPaisCodigo"),
    @NamedQuery(name = "Ocorrencia.findByOcrPais", query = "SELECT o FROM Ocorrencia o WHERE o.ocrPais = :ocrPais"),
    @NamedQuery(name = "Ocorrencia.findByOcrLocal", query = "SELECT o FROM Ocorrencia o WHERE o.ocrLocal = :ocrLocal"),
    @NamedQuery(name = "Ocorrencia.findByOcrCoordenadas", query = "SELECT o FROM Ocorrencia o WHERE o.ocrCoordenadas = :ocrCoordenadas"),
    @NamedQuery(name = "Ocorrencia.findByOcrGeo", query = "SELECT o FROM Ocorrencia o WHERE o.ocrGeo = :ocrGeo"),
    @NamedQuery(name = "Ocorrencia.findByOcrIdentificacao", query = "SELECT o FROM Ocorrencia o WHERE o.ocrIdentificacao = :ocrIdentificacao"),
    @NamedQuery(name = "Ocorrencia.findByOcrSentimento", query = "SELECT o FROM Ocorrencia o WHERE o.ocrSentimento = :ocrSentimento"),
    @NamedQuery(name = "Ocorrencia.findByOcrTempo", query = "SELECT o FROM Ocorrencia o WHERE o.ocrTempo = :ocrTempo")})
public class Ocorrencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ocr_id")
    private Integer ocrId;
    @Basic(optional = false)
    @Column(name = "cri_id")
    private int criId;
    @Basic(optional = false)
    @Column(name = "cap_id")
    private int capId;
    @Basic(optional = false)
    @Column(name = "api_id")
    private int apiId;
    @Column(name = "ocr_id_api")
    private Long ocrIdApi;
    @Column(name = "ocr_criacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ocrCriacao;
    @Lob
    @Column(name = "ocr_texto")
    private String ocrTexto;
    @Column(name = "ocr_usu_id")
    private Long ocrUsuId;
    @Column(name = "ocr_usu_nome")
    private String ocrUsuNome;
    @Column(name = "ocr_usu_screen_nome")
    private String ocrUsuScreenNome;
    @Column(name = "ocr_fonte")
    private String ocrFonte;
    @Column(name = "ocr_lingua")
    private String ocrLingua;
    @Column(name = "ocr_pais_codigo")
    private String ocrPaisCodigo;
    @Column(name = "ocr_pais")
    private String ocrPais;
    @Column(name = "ocr_local")
    private String ocrLocal;
    @Column(name = "ocr_coordenadas")
    private String ocrCoordenadas;
    @Column(name = "ocr_favorite")
    private int ocrFavorite;
    @Column(name = "ocr_retweet")
    private int ocrRetweet;
    @Column(name = "ocr_geo")
    private Character ocrGeo;
    @Column(name = "ocr_identificacao")
    private Character ocrIdentificacao;
    @Column(name = "ocr_identper")
    private Float ocrIdenper;
    @Column(name = "ocr_gravado")
    private Character ocrGravado;
    @Column(name = "ocr_sentimento")
    private Character ocrSentimento;
    @Column(name = "ocr_tempo")
    private Character ocrTempo;

    public Ocorrencia() {
    }

    public Ocorrencia(Integer ocrId) {
        this.ocrId = ocrId;
    }

    public Ocorrencia(Integer ocrId, int criId, int capId, int apiId) {
        this.ocrId = ocrId;
        this.criId = criId;
        this.capId = capId;
        this.apiId = apiId;
    }

    public Integer getOcrId() {
        return ocrId;
    }

    public void setOcrId(Integer ocrId) {
        this.ocrId = ocrId;
    }

    public int getCriId() {
        return criId;
    }

    public void setCriId(int criId) {
        this.criId = criId;
    }

    public int getCapId() {
        return capId;
    }

    public void setCapId(int capId) {
        this.capId = capId;
    }

    public int getApiId() {
        return apiId;
    }

    public void setApiId(int apiId) {
        this.apiId = apiId;
    }

    public Long getOcrIdApi() {
        return ocrIdApi;
    }

    public void setOcrIdApi(Long ocrIdApi) {
        this.ocrIdApi = ocrIdApi;
    }

    public Date getOcrCriacao() {
        return ocrCriacao;
    }

    public void setOcrCriacao(Date ocrCriacao) {
        this.ocrCriacao = ocrCriacao;
    }

    public String getOcrTexto() {
        return ocrTexto;
    }

    public void setOcrTexto(String ocrTexto) {
        this.ocrTexto = ocrTexto;
    }

    public Long getOcrUsuId() {
        return ocrUsuId;
    }

    public void setOcrUsuId(Long ocrUsuId) {
        this.ocrUsuId = ocrUsuId;
    }

    public String getOcrUsuNome() {
        return ocrUsuNome;
    }

    public void setOcrUsuNome(String ocrUsuNome) {
        this.ocrUsuNome = ocrUsuNome;
    }

    public String getOcrUsuScreenNome() {
        return ocrUsuScreenNome;
    }

    public void setOcrUsuScreenNome(String ocrUsuScreenNome) {
        this.ocrUsuScreenNome = ocrUsuScreenNome;
    }

    public String getOcrFonte() {
        return ocrFonte;
    }

    public void setOcrFonte(String ocrFonte) {
        this.ocrFonte = ocrFonte;
    }

    public String getOcrLingua() {
        return ocrLingua;
    }

    public void setOcrLingua(String ocrLingua) {
        this.ocrLingua = ocrLingua;
    }

    public String getOcrPaisCodigo() {
        return ocrPaisCodigo;
    }

    public void setOcrPaisCodigo(String ocrPaisCodigo) {
        this.ocrPaisCodigo = ocrPaisCodigo;
    }

    public String getOcrPais() {
        return ocrPais;
    }

    public void setOcrPais(String ocrPais) {
        this.ocrPais = ocrPais;
    }

    public String getOcrLocal() {
        return ocrLocal;
    }

    public void setOcrLocal(String ocrLocal) {
        this.ocrLocal = ocrLocal;
    }

    public String getOcrCoordenadas() {
        return ocrCoordenadas;
    }

    public void setOcrCoordenadas(String ocrCoordenadas) {
        this.ocrCoordenadas = ocrCoordenadas;
    }

    public Character getOcrGeo() {
        return ocrGeo;
    }

    public void setOcrGeo(Character ocrGeo) {
        this.ocrGeo = ocrGeo;
    }

    public Character getOcrIdentificacao() {
        return ocrIdentificacao;
    }

    public void setOcrIdentificacao(Character ocrIdentificacao) {
        this.ocrIdentificacao = ocrIdentificacao;
    }

    public Character getOcrSentimento() {
        return ocrSentimento;
    }

    public void setOcrSentimento(Character ocrSentimento) {
        this.ocrSentimento = ocrSentimento;
    }

    public Character getOcrTempo() {
        return ocrTempo;
    }

    public void setOcrTempo(Character ocrTempo) {
        this.ocrTempo = ocrTempo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ocrId != null ? ocrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ocorrencia)) {
            return false;
        }
        Ocorrencia other = (Ocorrencia) object;
        if ((this.ocrId == null && other.ocrId != null) || (this.ocrId != null && !this.ocrId.equals(other.ocrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bditac.Ocorrencia[ ocrId=" + ocrId + " ]";
    }

    public Float getOcrIdenper() {
        return ocrIdenper;
    }

    public void setOcrIdenper(Float ocrIdenper) {
        this.ocrIdenper = ocrIdenper;
    }

    public Character getOcrGravado() {
        return ocrGravado;
    }

    public void setOcrGravado(Character ocrGravado) {
        this.ocrGravado = ocrGravado;
    }

    public int getOcrFavorite() {
        return ocrFavorite;
    }

    public void setOcrFavorite(int ocrFavorite) {
        this.ocrFavorite = ocrFavorite;
    }

    public int getOcrRetweet() {
        return ocrRetweet;
    }

    public void setOcrRetweet(int ocrRetweet) {
        this.ocrRetweet = ocrRetweet;
    }

}
