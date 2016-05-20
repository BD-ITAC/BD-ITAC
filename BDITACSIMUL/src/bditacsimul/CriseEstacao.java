/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bditacsimul;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "crise_estacao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CriseEstacao.findAll", query = "SELECT c FROM CriseEstacao c"),
    @NamedQuery(name = "CriseEstacao.findByCreId", query = "SELECT c FROM CriseEstacao c WHERE c.creId = :creId"),
    @NamedQuery(name = "CriseEstacao.findByCriId", query = "SELECT c FROM CriseEstacao c WHERE c.criId = :criId"),
    @NamedQuery(name = "CriseEstacao.findByCreDescricao", query = "SELECT c FROM CriseEstacao c WHERE c.creDescricao = :creDescricao"),
    @NamedQuery(name = "CriseEstacao.findByCreInicio", query = "SELECT c FROM CriseEstacao c WHERE c.creInicio = :creInicio"),
    @NamedQuery(name = "CriseEstacao.findByCreFinal", query = "SELECT c FROM CriseEstacao c WHERE c.creFinal = :creFinal"),
    @NamedQuery(name = "CriseEstacao.findByCreLongitude", query = "SELECT c FROM CriseEstacao c WHERE c.creLongitude = :creLongitude"),
    @NamedQuery(name = "CriseEstacao.findByCreLatitude", query = "SELECT c FROM CriseEstacao c WHERE c.creLatitude = :creLatitude"),
    @NamedQuery(name = "CriseEstacao.findByCreRegiao", query = "SELECT c FROM CriseEstacao c WHERE c.creRegiao = :creRegiao")})
public class CriseEstacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cre_id")
    private Integer creId;
    @Basic(optional = false)
    @Column(name = "cri_id")
    private int criId;
    @Column(name = "cre_descricao")
    private String creDescricao;
    @Column(name = "cre_inicio")
    @Temporal(TemporalType.DATE)
    private Date creInicio;
    @Column(name = "cre_final")
    @Temporal(TemporalType.DATE)
    private Date creFinal;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cre_longitude")
    private Double creLongitude;
    @Column(name = "cre_latitude")
    private Double creLatitude;
    @Column(name = "cre_regiao")
    private String creRegiao;

    public CriseEstacao() {
    }

    public CriseEstacao(Integer creId) {
        this.creId = creId;
    }

    public CriseEstacao(Integer creId, int criId) {
        this.creId = creId;
        this.criId = criId;
    }

    public Integer getCreId() {
        return creId;
    }

    public void setCreId(Integer creId) {
        this.creId = creId;
    }

    public int getCriId() {
        return criId;
    }

    public void setCriId(int criId) {
        this.criId = criId;
    }

    public String getCreDescricao() {
        return creDescricao;
    }

    public void setCreDescricao(String creDescricao) {
        this.creDescricao = creDescricao;
    }

    public Date getCreInicio() {
        return creInicio;
    }

    public void setCreInicio(Date creInicio) {
        this.creInicio = creInicio;
    }

    public Date getCreFinal() {
        return creFinal;
    }

    public void setCreFinal(Date creFinal) {
        this.creFinal = creFinal;
    }

    public Double getCreLongitude() {
        return creLongitude;
    }

    public void setCreLongitude(Double creLongitude) {
        this.creLongitude = creLongitude;
    }

    public Double getCreLatitude() {
        return creLatitude;
    }

    public void setCreLatitude(Double creLatitude) {
        this.creLatitude = creLatitude;
    }

    public String getCreRegiao() {
        return creRegiao;
    }

    public void setCreRegiao(String creRegiao) {
        this.creRegiao = creRegiao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (creId != null ? creId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CriseEstacao)) {
            return false;
        }
        CriseEstacao other = (CriseEstacao) object;
        if ((this.creId == null && other.creId != null) || (this.creId != null && !this.creId.equals(other.creId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bditacsimul.CriseEstacao[ creId=" + creId + " ]";
    }
    
}
