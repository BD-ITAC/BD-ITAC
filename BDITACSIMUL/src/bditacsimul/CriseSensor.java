/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bditacsimul;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Henrique
 */
@Entity
@Table(name = "crise_sensor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CriseSensor.findAll", query = "SELECT c FROM CriseSensor c"),
    @NamedQuery(name = "CriseSensor.findByCseId", query = "SELECT c FROM CriseSensor c WHERE c.cseId = :cseId"),
    @NamedQuery(name = "CriseSensor.findByCstId", query = "SELECT c FROM CriseSensor c WHERE c.cstId = :cstId"),
    @NamedQuery(name = "CriseSensor.findByCreId", query = "SELECT c FROM CriseSensor c WHERE c.creId = :creId"),
    @NamedQuery(name = "CriseSensor.findByCseDescricao", query = "SELECT c FROM CriseSensor c WHERE c.cseDescricao = :cseDescricao"),
    @NamedQuery(name = "CriseSensor.findByCseFaixaini", query = "SELECT c FROM CriseSensor c WHERE c.cseFaixaini = :cseFaixaini"),
    @NamedQuery(name = "CriseSensor.findByCseFaixafim", query = "SELECT c FROM CriseSensor c WHERE c.cseFaixafim = :cseFaixafim"),
    @NamedQuery(name = "CriseSensor.findByCseAlerta", query = "SELECT c FROM CriseSensor c WHERE c.cseAlerta = :cseAlerta"),
    @NamedQuery(name = "CriseSensor.findByCseCritico", query = "SELECT c FROM CriseSensor c WHERE c.cseCritico = :cseCritico")})
public class CriseSensor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cse_id")
    private Integer cseId;
    @Basic(optional = false)
    @Column(name = "cst_id")
    private int cstId;
    @Basic(optional = false)
    @Column(name = "cre_id")
    private int creId;
    @Column(name = "cse_descricao")
    private String cseDescricao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cse_faixaini")
    private Float cseFaixaini;
    @Column(name = "cse_faixafim")
    private Float cseFaixafim;
    @Column(name = "cse_alerta")
    private Float cseAlerta;
    @Column(name = "cse_critico")
    private Float cseCritico;

    public CriseSensor() {
    }

    public CriseSensor(Integer cseId) {
        this.cseId = cseId;
    }

    public CriseSensor(Integer cseId, int cstId, int creId) {
        this.cseId = cseId;
        this.cstId = cstId;
        this.creId = creId;
    }

    public Integer getCseId() {
        return cseId;
    }

    public void setCseId(Integer cseId) {
        this.cseId = cseId;
    }

    public int getCstId() {
        return cstId;
    }

    public void setCstId(int cstId) {
        this.cstId = cstId;
    }

    public int getCreId() {
        return creId;
    }

    public void setCreId(int creId) {
        this.creId = creId;
    }

    public String getCseDescricao() {
        return cseDescricao;
    }

    public void setCseDescricao(String cseDescricao) {
        this.cseDescricao = cseDescricao;
    }

    public Float getCseFaixaini() {
        return cseFaixaini;
    }

    public void setCseFaixaini(Float cseFaixaini) {
        this.cseFaixaini = cseFaixaini;
    }

    public Float getCseFaixafim() {
        return cseFaixafim;
    }

    public void setCseFaixafim(Float cseFaixafim) {
        this.cseFaixafim = cseFaixafim;
    }

    public Float getCseAlerta() {
        return cseAlerta;
    }

    public void setCseAlerta(Float cseAlerta) {
        this.cseAlerta = cseAlerta;
    }

    public Float getCseCritico() {
        return cseCritico;
    }

    public void setCseCritico(Float cseCritico) {
        this.cseCritico = cseCritico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cseId != null ? cseId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CriseSensor)) {
            return false;
        }
        CriseSensor other = (CriseSensor) object;
        if ((this.cseId == null && other.cseId != null) || (this.cseId != null && !this.cseId.equals(other.cseId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bditacsimul.CriseSensor[ cseId=" + cseId + " ]";
    }
    
}
