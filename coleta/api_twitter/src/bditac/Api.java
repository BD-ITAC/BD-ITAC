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
@Table(name = "api")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Api.findAll", query = "SELECT a FROM Api a"),
    @NamedQuery(name = "Api.findByApiId", query = "SELECT a FROM Api a WHERE a.apiId = :apiId"),
    @NamedQuery(name = "Api.findByApiNome", query = "SELECT a FROM Api a WHERE a.apiNome = :apiNome"),
    @NamedQuery(name = "Api.findByApiAtiva", query = "SELECT a FROM Api a WHERE a.apiAtiva = :apiAtiva"),
    @NamedQuery(name = "Api.findByApiUrl", query = "SELECT a FROM Api a WHERE a.apiUrl = :apiUrl")})
public class Api implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "api_id")
    private Integer apiId;
    @Basic(optional = false)
    @Column(name = "api_nome")
    private String apiNome;
    @Column(name = "api_ativa")
    private Boolean apiAtiva;
    @Column(name = "api_url")
    private String apiUrl;

    public Api() {
    }

    public Api(Integer apiId) {
        this.apiId = apiId;
    }

    public Api(Integer apiId, String apiNome) {
        this.apiId = apiId;
        this.apiNome = apiNome;
    }

    public Integer getApiId() {
        return apiId;
    }

    public void setApiId(Integer apiId) {
        this.apiId = apiId;
    }

    public String getApiNome() {
        return apiNome;
    }

    public void setApiNome(String apiNome) {
        this.apiNome = apiNome;
    }

    public Boolean getApiAtiva() {
        return apiAtiva;
    }

    public void setApiAtiva(Boolean apiAtiva) {
        this.apiAtiva = apiAtiva;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (apiId != null ? apiId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Api)) {
            return false;
        }
        Api other = (Api) object;
        if ((this.apiId == null && other.apiId != null) || (this.apiId != null && !this.apiId.equals(other.apiId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bditac.Api[ apiId=" + apiId + " ]";
    }
    
}
