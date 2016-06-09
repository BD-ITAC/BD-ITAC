/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bditac;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Henrique
 */
@Entity
@Table(name = "crise_api")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CriseApi.findAll", query = "SELECT c FROM CriseApi c"),
    @NamedQuery(name = "CriseApi.findByCapId", query = "SELECT c FROM CriseApi c WHERE c.capId = :capId"),
    @NamedQuery(name = "CriseApi.findByCriId", query = "SELECT c FROM CriseApi c WHERE c.criId = :criId"),
    @NamedQuery(name = "CriseApi.findByApiId", query = "SELECT c FROM CriseApi c WHERE c.apiId = :apiId"),
    @NamedQuery(name = "CriseApi.findByCapInicio", query = "SELECT c FROM CriseApi c WHERE c.capInicio = :capInicio"),
    @NamedQuery(name = "CriseApi.findByCapFinal", query = "SELECT c FROM CriseApi c WHERE c.capFinal = :capFinal"),
    @NamedQuery(name = "CriseApi.findByCapAtiva", query = "SELECT c FROM CriseApi c WHERE c.capAtiva = :capAtiva"),
    @NamedQuery(name = "CriseApi.findByCapKey", query = "SELECT c FROM CriseApi c WHERE c.capKey = :capKey"),
    @NamedQuery(name = "CriseApi.findByCapSecret", query = "SELECT c FROM CriseApi c WHERE c.capSecret = :capSecret"),
    @NamedQuery(name = "CriseApi.findByCapToken", query = "SELECT c FROM CriseApi c WHERE c.capToken = :capToken"),
    @NamedQuery(name = "CriseApi.findByCapTokenSecret", query = "SELECT c FROM CriseApi c WHERE c.capTokenSecret = :capTokenSecret")})
public class CriseApi implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cap_id")
    private Integer capId;
    @Basic(optional = false)
    @Column(name = "cri_id")
    private int criId;
    @Basic(optional = false)
    @Column(name = "api_id")
    private int apiId;
    @Column(name = "cap_inicio")
    @Temporal(TemporalType.DATE)
    private Date capInicio;
    @Column(name = "cap_final")
    @Temporal(TemporalType.DATE)
    private Date capFinal;
    @Column(name = "cap_ativa")
    private Boolean capAtiva;
    @Column(name = "cap_key")
    private String capKey;
    @Column(name = "cap_secret")
    private String capSecret;
    @Column(name = "cap_token")
    private String capToken;
    @Column(name = "cap_token_secret")
    private String capTokenSecret;

    public CriseApi() {
    }

    public CriseApi(Integer capId) {
        this.capId = capId;
    }

    public CriseApi(Integer capId, int criId, int apiId) {
        this.capId = capId;
        this.criId = criId;
        this.apiId = apiId;
    }

    public Integer getCapId() {
        return capId;
    }

    public void setCapId(Integer capId) {
        this.capId = capId;
    }

    public int getCriId() {
        return criId;
    }

    public void setCriId(int criId) {
        this.criId = criId;
    }

    public int getApiId() {
        return apiId;
    }

    public void setApiId(int apiId) {
        this.apiId = apiId;
    }

    public Date getCapInicio() {
        return capInicio;
    }

    public void setCapInicio(Date capInicio) {
        this.capInicio = capInicio;
    }

    public Date getCapFinal() {
        return capFinal;
    }

    public void setCapFinal(Date capFinal) {
        this.capFinal = capFinal;
    }

    public Boolean getCapAtiva() {
        return capAtiva;
    }

    public void setCapAtiva(Boolean capAtiva) {
        this.capAtiva = capAtiva;
    }

    public String getCapKey() {
        return capKey;
    }

    public void setCapKey(String capKey) {
        this.capKey = capKey;
    }

    public String getCapSecret() {
        return capSecret;
    }

    public void setCapSecret(String capSecret) {
        this.capSecret = capSecret;
    }

    public String getCapToken() {
        return capToken;
    }

    public void setCapToken(String capToken) {
        this.capToken = capToken;
    }

    public String getCapTokenSecret() {
        return capTokenSecret;
    }

    public void setCapTokenSecret(String capTokenSecret) {
        this.capTokenSecret = capTokenSecret;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (capId != null ? capId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CriseApi)) {
            return false;
        }
        CriseApi other = (CriseApi) object;
        if ((this.capId == null && other.capId != null) || (this.capId != null && !this.capId.equals(other.capId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bditac.CriseApi[ capId=" + capId + " ]";
    }
    
}
