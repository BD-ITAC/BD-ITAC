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
@Table(name = "crise_tipo_classe")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CriseTipoClasse.findAll", query = "SELECT c FROM CriseTipoClasse c"),
    @NamedQuery(name = "CriseTipoClasse.findByCtcId", query = "SELECT c FROM CriseTipoClasse c WHERE c.ctcId = :ctcId"),
    @NamedQuery(name = "CriseTipoClasse.findByCtcDescricao", query = "SELECT c FROM CriseTipoClasse c WHERE c.ctcDescricao = :ctcDescricao")})
public class CriseTipoClasse implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ctc_id")
    private Integer ctcId;
    @Column(name = "ctc_descricao")
    private String ctcDescricao;

    public CriseTipoClasse() {
    }

    public CriseTipoClasse(Integer ctcId) {
        this.ctcId = ctcId;
    }

    public Integer getCtcId() {
        return ctcId;
    }

    public void setCtcId(Integer ctcId) {
        this.ctcId = ctcId;
    }

    public String getCtcDescricao() {
        return ctcDescricao;
    }

    public void setCtcDescricao(String ctcDescricao) {
        this.ctcDescricao = ctcDescricao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ctcId != null ? ctcId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CriseTipoClasse)) {
            return false;
        }
        CriseTipoClasse other = (CriseTipoClasse) object;
        if ((this.ctcId == null && other.ctcId != null) || (this.ctcId != null && !this.ctcId.equals(other.ctcId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bditac.CriseTipoClasse[ ctcId=" + ctcId + " ]";
    }
    
}
