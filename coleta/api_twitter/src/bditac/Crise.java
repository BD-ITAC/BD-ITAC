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
@Table(name = "crise")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Crise.findAll", query = "SELECT c FROM Crise c"),
    @NamedQuery(name = "Crise.findByCriId", query = "SELECT c FROM Crise c WHERE c.criId = :criId"),
    @NamedQuery(name = "Crise.findByCrtId", query = "SELECT c FROM Crise c WHERE c.crtId = :crtId"),
    @NamedQuery(name = "Crise.findByCriDescricao", query = "SELECT c FROM Crise c WHERE c.criDescricao = :criDescricao"),
    @NamedQuery(name = "Crise.findByCriInicio", query = "SELECT c FROM Crise c WHERE c.criInicio = :criInicio"),
    @NamedQuery(name = "Crise.findByCriFinal", query = "SELECT c FROM Crise c WHERE c.criFinal = :criFinal"),
    @NamedQuery(name = "Crise.findByCriAtiva", query = "SELECT c FROM Crise c WHERE c.criAtiva = :criAtiva"),
    @NamedQuery(name = "Crise.findByCriRegiao", query = "SELECT c FROM Crise c WHERE c.criRegiao = :criRegiao")})
public class Crise implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cri_id")
    private Integer criId;
    @Basic(optional = false)
    @Column(name = "crt_id")
    private int crtId;
    @Basic(optional = false)
    @Column(name = "cri_descricao")
    private String criDescricao;
    @Column(name = "cri_inicio")
    @Temporal(TemporalType.DATE)
    private Date criInicio;
    @Column(name = "cri_final")
    @Temporal(TemporalType.DATE)
    private Date criFinal;
    @Column(name = "cri_ativa")
    private Boolean criAtiva;
    @Column(name = "cri_regiao")
    private String criRegiao;
    @Basic(optional = false)
    @Column(name = "cid_id")
    private int cidId;
    @Basic(optional = false)
    @Column(name = "cri_geotipo")
    private Character criGeotipo;

    public Crise() {
    }

    public Crise(Integer criId) {
        this.criId = criId;
    }

    public Crise(Integer criId, int crtId, String criDescricao) {
        this.criId = criId;
        this.crtId = crtId;
        this.criDescricao = criDescricao;
    }

    public Integer getCriId() {
        return criId;
    }

    public void setCriId(Integer criId) {
        this.criId = criId;
    }

    public int getCrtId() {
        return crtId;
    }

    public void setCrtId(int crtId) {
        this.crtId = crtId;
    }

    public String getCriDescricao() {
        return criDescricao;
    }

    public void setCriDescricao(String criDescricao) {
        this.criDescricao = criDescricao;
    }

    public Date getCriInicio() {
        return criInicio;
    }

    public void setCriInicio(Date criInicio) {
        this.criInicio = criInicio;
    }

    public Date getCriFinal() {
        return criFinal;
    }

    public void setCriFinal(Date criFinal) {
        this.criFinal = criFinal;
    }

    public Boolean getCriAtiva() {
        return criAtiva;
    }

    public void setCriAtiva(Boolean criAtiva) {
        this.criAtiva = criAtiva;
    }

    public String getCriRegiao() {
        return criRegiao;
    }

    public void setCriRegiao(String criRegiao) {
        this.criRegiao = criRegiao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (criId != null ? criId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Crise)) {
            return false;
        }
        Crise other = (Crise) object;
        if ((this.criId == null && other.criId != null) || (this.criId != null && !this.criId.equals(other.criId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bditac.Crise[ criId=" + criId + " ]";
    }

    public int getCidId() {
        return cidId;
    }

    public void setCidId(int cidId) {
        this.cidId = cidId;
    }

    public Character getCriGeotipo() {
        return criGeotipo;
    }

    public void setCriGeotipo(Character criGeotipo) {
        this.criGeotipo = criGeotipo;
    }
    
}
