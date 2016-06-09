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
@Table(name = "crise_tipo_subgrupo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CriseTipoSubgrupo.findAll", query = "SELECT c FROM CriseTipoSubgrupo c"),
    @NamedQuery(name = "CriseTipoSubgrupo.findByCtsId", query = "SELECT c FROM CriseTipoSubgrupo c WHERE c.ctsId = :ctsId"),
    @NamedQuery(name = "CriseTipoSubgrupo.findByCtgId", query = "SELECT c FROM CriseTipoSubgrupo c WHERE c.ctgId = :ctgId"),
    @NamedQuery(name = "CriseTipoSubgrupo.findByCtcId", query = "SELECT c FROM CriseTipoSubgrupo c WHERE c.ctcId = :ctcId"),
    @NamedQuery(name = "CriseTipoSubgrupo.findByCtsDescricao", query = "SELECT c FROM CriseTipoSubgrupo c WHERE c.ctsDescricao = :ctsDescricao")})
public class CriseTipoSubgrupo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cts_id")
    private Integer ctsId;
    @Basic(optional = false)
    @Column(name = "ctg_id")
    private int ctgId;
    @Basic(optional = false)
    @Column(name = "ctc_id")
    private int ctcId;
    @Column(name = "cts_descricao")
    private String ctsDescricao;

    public CriseTipoSubgrupo() {
    }

    public CriseTipoSubgrupo(Integer ctsId) {
        this.ctsId = ctsId;
    }

    public CriseTipoSubgrupo(Integer ctsId, int ctgId, int ctcId) {
        this.ctsId = ctsId;
        this.ctgId = ctgId;
        this.ctcId = ctcId;
    }

    public Integer getCtsId() {
        return ctsId;
    }

    public void setCtsId(Integer ctsId) {
        this.ctsId = ctsId;
    }

    public int getCtgId() {
        return ctgId;
    }

    public void setCtgId(int ctgId) {
        this.ctgId = ctgId;
    }

    public int getCtcId() {
        return ctcId;
    }

    public void setCtcId(int ctcId) {
        this.ctcId = ctcId;
    }

    public String getCtsDescricao() {
        return ctsDescricao;
    }

    public void setCtsDescricao(String ctsDescricao) {
        this.ctsDescricao = ctsDescricao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ctsId != null ? ctsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CriseTipoSubgrupo)) {
            return false;
        }
        CriseTipoSubgrupo other = (CriseTipoSubgrupo) object;
        if ((this.ctsId == null && other.ctsId != null) || (this.ctsId != null && !this.ctsId.equals(other.ctsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bditac.CriseTipoSubgrupo[ ctsId=" + ctsId + " ]";
    }
    
}
