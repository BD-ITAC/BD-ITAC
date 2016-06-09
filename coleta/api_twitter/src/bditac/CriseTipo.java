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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Henrique
 */
@Entity
@Table(name = "crise_tipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CriseTipo.findAll", query = "SELECT c FROM CriseTipo c"),
    @NamedQuery(name = "CriseTipo.findByCrtId", query = "SELECT c FROM CriseTipo c WHERE c.crtId = :crtId"),
    @NamedQuery(name = "CriseTipo.findByCtpId", query = "SELECT c FROM CriseTipo c WHERE c.ctpId = :ctpId"),
    @NamedQuery(name = "CriseTipo.findByCttId", query = "SELECT c FROM CriseTipo c WHERE c.cttId = :cttId"),
    @NamedQuery(name = "CriseTipo.findByCtsId", query = "SELECT c FROM CriseTipo c WHERE c.ctsId = :ctsId"),
    @NamedQuery(name = "CriseTipo.findByCtgId", query = "SELECT c FROM CriseTipo c WHERE c.ctgId = :ctgId"),
    @NamedQuery(name = "CriseTipo.findByCtcId", query = "SELECT c FROM CriseTipo c WHERE c.ctcId = :ctcId"),
    @NamedQuery(name = "CriseTipo.findByCrtDescricao", query = "SELECT c FROM CriseTipo c WHERE c.crtDescricao = :crtDescricao"),
    @NamedQuery(name = "CriseTipo.findByCrtCobrade", query = "SELECT c FROM CriseTipo c WHERE c.crtCobrade = :crtCobrade")})
public class CriseTipo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "crt_id")
    private Integer crtId;
    @Basic(optional = false)
    @Column(name = "ctp_id")
    private int ctpId;
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
    @Column(name = "crt_descricao")
    private String crtDescricao;
    @Lob
    @Column(name = "crt_definicao")
    private String crtDefinicao;
    @Column(name = "crt_cobrade")
    private String crtCobrade;

    public CriseTipo() {
    }

    public CriseTipo(Integer crtId) {
        this.crtId = crtId;
    }

    public CriseTipo(Integer crtId, int ctpId, int cttId, int ctsId, int ctgId, int ctcId) {
        this.crtId = crtId;
        this.ctpId = ctpId;
        this.cttId = cttId;
        this.ctsId = ctsId;
        this.ctgId = ctgId;
        this.ctcId = ctcId;
    }

    public Integer getCrtId() {
        return crtId;
    }

    public void setCrtId(Integer crtId) {
        this.crtId = crtId;
    }

    public int getCtpId() {
        return ctpId;
    }

    public void setCtpId(int ctpId) {
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

    public String getCrtDescricao() {
        return crtDescricao;
    }

    public void setCrtDescricao(String crtDescricao) {
        this.crtDescricao = crtDescricao;
    }

    public String getCrtDefinicao() {
        return crtDefinicao;
    }

    public void setCrtDefinicao(String crtDefinicao) {
        this.crtDefinicao = crtDefinicao;
    }

    public String getCrtCobrade() {
        return crtCobrade;
    }

    public void setCrtCobrade(String crtCobrade) {
        this.crtCobrade = crtCobrade;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (crtId != null ? crtId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CriseTipo)) {
            return false;
        }
        CriseTipo other = (CriseTipo) object;
        if ((this.crtId == null && other.crtId != null) || (this.crtId != null && !this.crtId.equals(other.crtId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bditac.CriseTipo[ crtId=" + crtId + " ]";
    }
    
}
