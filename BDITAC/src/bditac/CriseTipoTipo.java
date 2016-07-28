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
@Table(name = "crise_tipo_tipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CriseTipoTipo.findAll", query = "SELECT c FROM CriseTipoTipo c"),
    @NamedQuery(name = "CriseTipoTipo.findByCttId", query = "SELECT c FROM CriseTipoTipo c WHERE c.cttId = :cttId"),
    @NamedQuery(name = "CriseTipoTipo.findByCtsId", query = "SELECT c FROM CriseTipoTipo c WHERE c.ctsId = :ctsId"),
    @NamedQuery(name = "CriseTipoTipo.findByCtgId", query = "SELECT c FROM CriseTipoTipo c WHERE c.ctgId = :ctgId"),
    @NamedQuery(name = "CriseTipoTipo.findByCtcId", query = "SELECT c FROM CriseTipoTipo c WHERE c.ctcId = :ctcId"),
    @NamedQuery(name = "CriseTipoTipo.findByCttDescricao", query = "SELECT c FROM CriseTipoTipo c WHERE c.cttDescricao = :cttDescricao")})
public class CriseTipoTipo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ctt_id")
    private Integer cttId;
    @Basic(optional = false)
    @Column(name = "cts_id")
    private int ctsId;
    @Basic(optional = false)
    @Column(name = "ctg_id")
    private int ctgId;
    @Basic(optional = false)
    @Column(name = "ctc_id")
    private int ctcId;
    @Column(name = "ctt_descricao")
    private String cttDescricao;

    public CriseTipoTipo() {
    }

    public CriseTipoTipo(Integer cttId) {
        this.cttId = cttId;
    }

    public CriseTipoTipo(Integer cttId, int ctsId, int ctgId, int ctcId) {
        this.cttId = cttId;
        this.ctsId = ctsId;
        this.ctgId = ctgId;
        this.ctcId = ctcId;
    }

    public Integer getCttId() {
        return cttId;
    }

    public void setCttId(Integer cttId) {
        this.cttId = cttId;
    }

    public int getCtsId() {
        return ctsId;
    }

    public void setCtsId(int ctsId) {
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

    public String getCttDescricao() {
        return cttDescricao;
    }

    public void setCttDescricao(String cttDescricao) {
        this.cttDescricao = cttDescricao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cttId != null ? cttId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CriseTipoTipo)) {
            return false;
        }
        CriseTipoTipo other = (CriseTipoTipo) object;
        if ((this.cttId == null && other.cttId != null) || (this.cttId != null && !this.cttId.equals(other.cttId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bditac.CriseTipoTipo[ cttId=" + cttId + " ]";
    }
    
}
