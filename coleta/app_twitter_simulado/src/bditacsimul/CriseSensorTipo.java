/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bditacsimul;

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
@Table(name = "crise_sensor_tipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CriseSensorTipo.findAll", query = "SELECT c FROM CriseSensorTipo c"),
    @NamedQuery(name = "CriseSensorTipo.findByCstId", query = "SELECT c FROM CriseSensorTipo c WHERE c.cstId = :cstId"),
    @NamedQuery(name = "CriseSensorTipo.findByCstNome", query = "SELECT c FROM CriseSensorTipo c WHERE c.cstNome = :cstNome"),
    @NamedQuery(name = "CriseSensorTipo.findByCstDescricao", query = "SELECT c FROM CriseSensorTipo c WHERE c.cstDescricao = :cstDescricao")})
public class CriseSensorTipo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cst_id")
    private Integer cstId;
    @Column(name = "cst_nome")
    private String cstNome;
    @Column(name = "cst_descricao")
    private String cstDescricao;

    public CriseSensorTipo() {
    }

    public CriseSensorTipo(Integer cstId) {
        this.cstId = cstId;
    }

    public Integer getCstId() {
        return cstId;
    }

    public void setCstId(Integer cstId) {
        this.cstId = cstId;
    }

    public String getCstNome() {
        return cstNome;
    }

    public void setCstNome(String cstNome) {
        this.cstNome = cstNome;
    }

    public String getCstDescricao() {
        return cstDescricao;
    }

    public void setCstDescricao(String cstDescricao) {
        this.cstDescricao = cstDescricao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cstId != null ? cstId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CriseSensorTipo)) {
            return false;
        }
        CriseSensorTipo other = (CriseSensorTipo) object;
        if ((this.cstId == null && other.cstId != null) || (this.cstId != null && !this.cstId.equals(other.cstId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bditacsimul.CriseSensorTipo[ cstId=" + cstId + " ]";
    }
    
}
