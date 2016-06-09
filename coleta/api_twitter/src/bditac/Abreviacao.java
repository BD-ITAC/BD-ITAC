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
@Table(name = "abreviacao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Abreviacao.findAll", query = "SELECT a FROM Abreviacao a"),
    @NamedQuery(name = "Abreviacao.findByAbvId", query = "SELECT a FROM Abreviacao a WHERE a.abvId = :abvId"),
    @NamedQuery(name = "Abreviacao.findByPalId", query = "SELECT a FROM Abreviacao a WHERE a.palId = :palId"),
    @NamedQuery(name = "Abreviacao.findByAbvAbreviacao", query = "SELECT a FROM Abreviacao a WHERE a.abvAbreviacao = :abvAbreviacao")})
public class Abreviacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "abv_id")
    private Integer abvId;
    @Basic(optional = false)
    @Column(name = "pal_id")
    private int palId;
    @Column(name = "abv_abreviacao")
    private String abvAbreviacao;
    @Column(name = "abv_explicacao")
    private String abvExplicacao;

    public Abreviacao() {
    }

    public Abreviacao(Integer abvId) {
        this.abvId = abvId;
    }

    public Abreviacao(Integer abvId, int palId) {
        this.abvId = abvId;
        this.palId = palId;
    }

    public Integer getAbvId() {
        return abvId;
    }

    public void setAbvId(Integer abvId) {
        this.abvId = abvId;
    }

    public int getPalId() {
        return palId;
    }

    public void setPalId(int palId) {
        this.palId = palId;
    }

    public String getAbvAbreviacao() {
        return abvAbreviacao;
    }

    public void setAbvAbreviacao(String abvAbreviacao) {
        this.abvAbreviacao = abvAbreviacao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (abvId != null ? abvId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Abreviacao)) {
            return false;
        }
        Abreviacao other = (Abreviacao) object;
        if ((this.abvId == null && other.abvId != null) || (this.abvId != null && !this.abvId.equals(other.abvId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bditac.Abreviacao[ abvId=" + abvId + " ]";
    }

    public String getAbvExplicacao() {
        return abvExplicacao;
    }

    public void setAbvExplicacao(String abvExplicacao) {
        this.abvExplicacao = abvExplicacao;
    }
    
}
