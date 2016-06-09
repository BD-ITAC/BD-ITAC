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
@Table(name = "crise_tipo_grupo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CriseTipoGrupo.findAll", query = "SELECT c FROM CriseTipoGrupo c"),
    @NamedQuery(name = "CriseTipoGrupo.findByCtgId", query = "SELECT c FROM CriseTipoGrupo c WHERE c.ctgId = :ctgId"),
    @NamedQuery(name = "CriseTipoGrupo.findByCtcId", query = "SELECT c FROM CriseTipoGrupo c WHERE c.ctcId = :ctcId"),
    @NamedQuery(name = "CriseTipoGrupo.findByCtgDescricao", query = "SELECT c FROM CriseTipoGrupo c WHERE c.ctgDescricao = :ctgDescricao")})
public class CriseTipoGrupo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ctg_id")
    private Integer ctgId;
    @Basic(optional = false)
    @Column(name = "ctc_id")
    private int ctcId;
    @Column(name = "ctg_descricao")
    private String ctgDescricao;

    public CriseTipoGrupo() {
    }

    public CriseTipoGrupo(Integer ctgId) {
        this.ctgId = ctgId;
    }

    public CriseTipoGrupo(Integer ctgId, int ctcId) {
        this.ctgId = ctgId;
        this.ctcId = ctcId;
    }

    public Integer getCtgId() {
        return ctgId;
    }

    public void setCtgId(Integer ctgId) {
        this.ctgId = ctgId;
    }

    public int getCtcId() {
        return ctcId;
    }

    public void setCtcId(int ctcId) {
        this.ctcId = ctcId;
    }

    public String getCtgDescricao() {
        return ctgDescricao;
    }

    public void setCtgDescricao(String ctgDescricao) {
        this.ctgDescricao = ctgDescricao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ctgId != null ? ctgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CriseTipoGrupo)) {
            return false;
        }
        CriseTipoGrupo other = (CriseTipoGrupo) object;
        if ((this.ctgId == null && other.ctgId != null) || (this.ctgId != null && !this.ctgId.equals(other.ctgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bditac.CriseTipoGrupo[ ctgId=" + ctgId + " ]";
    }
    
}
