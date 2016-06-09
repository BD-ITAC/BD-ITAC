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
@Table(name = "estado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estado.findAll", query = "SELECT e FROM Estado e"),
    @NamedQuery(name = "Estado.findByEstSigla", query = "SELECT e FROM Estado e WHERE e.estSigla = :estSigla"),
    @NamedQuery(name = "Estado.findByEstNome", query = "SELECT e FROM Estado e WHERE e.estNome = :estNome")})
public class Estado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "est_sigla")
    private String estSigla;
    @Basic(optional = false)
    @Column(name = "est_nome")
    private String estNome;

    public Estado() {
    }

    public Estado(String estSigla) {
        this.estSigla = estSigla;
    }

    public Estado(String estSigla, String estNome) {
        this.estSigla = estSigla;
        this.estNome = estNome;
    }

    public String getEstSigla() {
        return estSigla;
    }

    public void setEstSigla(String estSigla) {
        this.estSigla = estSigla;
    }

    public String getEstNome() {
        return estNome;
    }

    public void setEstNome(String estNome) {
        this.estNome = estNome;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estSigla != null ? estSigla.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estado)) {
            return false;
        }
        Estado other = (Estado) object;
        if ((this.estSigla == null && other.estSigla != null) || (this.estSigla != null && !this.estSigla.equals(other.estSigla))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bditac.Estado[ estSigla=" + estSigla + " ]";
    }
    
}
