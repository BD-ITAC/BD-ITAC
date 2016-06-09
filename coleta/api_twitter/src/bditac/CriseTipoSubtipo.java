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
@Table(name = "crise_tipo_subtipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CriseTipoSubtipo.findAll", query = "SELECT c FROM CriseTipoSubtipo c"),
    @NamedQuery(name = "CriseTipoSubtipo.findByCtpId", query = "SELECT c FROM CriseTipoSubtipo c WHERE c.ctpId = :ctpId"),
    @NamedQuery(name = "CriseTipoSubtipo.findByCttId", query = "SELECT c FROM CriseTipoSubtipo c WHERE c.cttId = :cttId"),
    @NamedQuery(name = "CriseTipoSubtipo.findByCtsId", query = "SELECT c FROM CriseTipoSubtipo c WHERE c.ctsId = :ctsId"),
    @NamedQuery(name = "CriseTipoSubtipo.findByCtgId", query = "SELECT c FROM CriseTipoSubtipo c WHERE c.ctgId = :ctgId"),
    @NamedQuery(name = "CriseTipoSubtipo.findByCtcId", query = "SELECT c FROM CriseTipoSubtipo c WHERE c.ctcId = :ctcId"),
    @NamedQuery(name = "CriseTipoSubtipo.findByCtpDescricao", query = "SELECT c FROM CriseTipoSubtipo c WHERE c.ctpDescricao = :ctpDescricao")})
public class CriseTipoSubtipo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ctp_id")
    private Integer ctpId;
    @Basic(optional = false)
    @Column(name = "ctt_id")
    private int cttId;
    @Basic(optional = false)
    @Column(name = "cts_id")
    private int ctsId;
    @Basic(optional = false)
    @Column(name = "ctg_id")
    private int ctgId;
    @Basic(optional = false)
    @Column(name = "ctc_id")
    private int ctcId;
    @Column(name = "ctp_descricao")
    private String ctpDescricao;

    public CriseTipoSubtipo() {
    }

    public CriseTipoSubtipo(Integer ctpId) {
        this.ctpId = ctpId;
    }

    public CriseTipoSubtipo(Integer ctpId, int cttId, int ctsId, int ctgId, int ctcId) {
        this.ctpId = ctpId;
        this.cttId = cttId;
        this.ctsId = ctsId;
        this.ctgId = ctgId;
        this.ctcId = ctcId;
    }

    public Integer getCtpId() {
        return ctpId;
    }

    public void setCtpId(Integer ctpId) {
        this.ctpId = ctpId;
    }

    public int getCttId() {
        return cttId;
    }

    public void setCttId(int cttId) {
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

    public String getCtpDescricao() {
        return ctpDescricao;
    }

    public void setCtpDescricao(String ctpDescricao) {
        this.ctpDescricao = ctpDescricao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ctpId != null ? ctpId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CriseTipoSubtipo)) {
            return false;
        }
        CriseTipoSubtipo other = (CriseTipoSubtipo) object;
        if ((this.ctpId == null && other.ctpId != null) || (this.ctpId != null && !this.ctpId.equals(other.ctpId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bditac.CriseTipoSubtipo[ ctpId=" + ctpId + " ]";
    }
    
}
