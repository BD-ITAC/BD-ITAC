/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bditac;

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
@Table(name = "crise_identificacao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CriseIdentificacao.findAll", query = "SELECT c FROM CriseIdentificacao c"),
    @NamedQuery(name = "CriseIdentificacao.findByCiiId", query = "SELECT c FROM CriseIdentificacao c WHERE c.ciiId = :ciiId"),
    @NamedQuery(name = "CriseIdentificacao.findByCrtId", query = "SELECT c FROM CriseIdentificacao c WHERE c.crtId = :crtId"),
    @NamedQuery(name = "CriseIdentificacao.findByPalId", query = "SELECT c FROM CriseIdentificacao c WHERE c.palId = :palId"),
    @NamedQuery(name = "CriseIdentificacao.findByCiiTipo", query = "SELECT c FROM CriseIdentificacao c WHERE c.ciiTipo = :ciiTipo"),
    @NamedQuery(name = "CriseIdentificacao.findByCiiSentimento", query = "SELECT c FROM CriseIdentificacao c WHERE c.ciiSentimento = :ciiSentimento"),
    @NamedQuery(name = "CriseIdentificacao.findByCiiTempo", query = "SELECT c FROM CriseIdentificacao c WHERE c.ciiTempo = :ciiTempo")})
public class CriseIdentificacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cii_id")
    private Integer ciiId;
    @Basic(optional = false)
    @Column(name = "crt_id")
    private int crtId;
    @Basic(optional = false)
    @Column(name = "pal_id")
    private int palId;
    @Basic(optional = false)
    @Column(name = "cii_tipo")
    private Character ciiTipo;
    @Column(name = "cii_sentimento")
    private Character ciiSentimento;
    @Column(name = "cii_tempo")
    private Character ciiTempo;

    public CriseIdentificacao() {
    }

    public CriseIdentificacao(Integer ciiId) {
        this.ciiId = ciiId;
    }

    public CriseIdentificacao(Integer ciiId, int crtId, int palId, Character ciiTipo) {
        this.ciiId = ciiId;
        this.crtId = crtId;
        this.palId = palId;
        this.ciiTipo = ciiTipo;
    }

    public Integer getCiiId() {
        return ciiId;
    }

    public void setCiiId(Integer ciiId) {
        this.ciiId = ciiId;
    }

    public int getCrtId() {
        return crtId;
    }

    public void setCrtId(int crtId) {
        this.crtId = crtId;
    }

    public int getPalId() {
        return palId;
    }

    public void setPalId(int palId) {
        this.palId = palId;
    }

    public Character getCiiTipo() {
        return ciiTipo;
    }

    public void setCiiTipo(Character ciiTipo) {
        this.ciiTipo = ciiTipo;
    }

    public Character getCiiSentimento() {
        return ciiSentimento;
    }

    public void setCiiSentimento(Character ciiSentimento) {
        this.ciiSentimento = ciiSentimento;
    }

    public Character getCiiTempo() {
        return ciiTempo;
    }

    public void setCiiTempo(Character ciiTempo) {
        this.ciiTempo = ciiTempo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ciiId != null ? ciiId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CriseIdentificacao)) {
            return false;
        }
        CriseIdentificacao other = (CriseIdentificacao) object;
        if ((this.ciiId == null && other.ciiId != null) || (this.ciiId != null && !this.ciiId.equals(other.ciiId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bditac.CriseIdentificacao[ ciiId=" + ciiId + " ]";
    }
    
}
