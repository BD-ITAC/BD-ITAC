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
@Table(name = "cidade")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cidade.findAll", query = "SELECT c FROM Cidade c"),
    @NamedQuery(name = "Cidade.findByCidId", query = "SELECT c FROM Cidade c WHERE c.cidId = :cidId"),
    @NamedQuery(name = "Cidade.findByEstSigla", query = "SELECT c FROM Cidade c WHERE c.estSigla = :estSigla"),
    @NamedQuery(name = "Cidade.findByCidNome", query = "SELECT c FROM Cidade c WHERE c.cidNome = :cidNome")})
public class Cidade implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cid_id")
    private Integer cidId;
    @Basic(optional = false)
    @Column(name = "est_sigla")
    private String estSigla;
    @Basic(optional = false)
    @Column(name = "cid_nome")
    private String cidNome;

    public Cidade() {
    }

    public Cidade(Integer cidId) {
        this.cidId = cidId;
    }

    public Cidade(Integer cidId, String estSigla, String cidNome) {
        this.cidId = cidId;
        this.estSigla = estSigla;
        this.cidNome = cidNome;
    }

    public Integer getCidId() {
        return cidId;
    }

    public void setCidId(Integer cidId) {
        this.cidId = cidId;
    }

    public String getEstSigla() {
        return estSigla;
    }

    public void setEstSigla(String estSigla) {
        this.estSigla = estSigla;
    }

    public String getCidNome() {
        return cidNome;
    }

    public void setCidNome(String cidNome) {
        this.cidNome = cidNome;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cidId != null ? cidId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cidade)) {
            return false;
        }
        Cidade other = (Cidade) object;
        if ((this.cidId == null && other.cidId != null) || (this.cidId != null && !this.cidId.equals(other.cidId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bditac.Cidade[ cidId=" + cidId + " ]";
    }
    
}
