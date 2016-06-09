/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bditacsimul;

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
@Table(name = "crise_estacao_captura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CriseEstacaoCaptura.findAll", query = "SELECT c FROM CriseEstacaoCaptura c"),
    @NamedQuery(name = "CriseEstacaoCaptura.findByCecId", query = "SELECT c FROM CriseEstacaoCaptura c WHERE c.cecId = :cecId"),
    @NamedQuery(name = "CriseEstacaoCaptura.findByCreId", query = "SELECT c FROM CriseEstacaoCaptura c WHERE c.creId = :creId"),
    @NamedQuery(name = "CriseEstacaoCaptura.findByCecTemperatura", query = "SELECT c FROM CriseEstacaoCaptura c WHERE c.cecTemperatura = :cecTemperatura"),
    @NamedQuery(name = "CriseEstacaoCaptura.findByCecBarometrica", query = "SELECT c FROM CriseEstacaoCaptura c WHERE c.cecBarometrica = :cecBarometrica"),
    @NamedQuery(name = "CriseEstacaoCaptura.findByCecNivelagua", query = "SELECT c FROM CriseEstacaoCaptura c WHERE c.cecNivelagua = :cecNivelagua"),
    @NamedQuery(name = "CriseEstacaoCaptura.findByCecPluviometro", query = "SELECT c FROM CriseEstacaoCaptura c WHERE c.cecPluviometro = :cecPluviometro"),
    @NamedQuery(name = "CriseEstacaoCaptura.findByCecDatahora", query = "SELECT c FROM CriseEstacaoCaptura c WHERE c.cecDatahora = :cecDatahora"),
    @NamedQuery(name = "CriseEstacaoCaptura.findByCecNota", query = "SELECT c FROM CriseEstacaoCaptura c WHERE c.cecNota = :cecNota")})
public class CriseEstacaoCaptura implements Serializable {

    @Column(name = "cec_umidade")
    private Float cecUmidade;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cec_id")
    private Integer cecId;
    @Basic(optional = false)
    @Column(name = "cre_id")
    private int creId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cec_temperatura")
    private Float cecTemperatura;
    @Column(name = "cec_barometrica")
    private Float cecBarometrica;
    @Column(name = "cec_nivelagua")
    private Float cecNivelagua;
    @Column(name = "cec_pluviometro")
    private Float cecPluviometro;
    @Column(name = "cec_datahora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cecDatahora;
    @Column(name = "cec_nota")
    private String cecNota;

    public CriseEstacaoCaptura() {
    }

    public CriseEstacaoCaptura(Integer cecId) {
        this.cecId = cecId;
    }

    public CriseEstacaoCaptura(Integer cecId, int creId) {
        this.cecId = cecId;
        this.creId = creId;
    }

    public Integer getCecId() {
        return cecId;
    }

    public void setCecId(Integer cecId) {
        this.cecId = cecId;
    }

    public int getCreId() {
        return creId;
    }

    public void setCreId(int creId) {
        this.creId = creId;
    }

    public Float getCecTemperatura() {
        return cecTemperatura;
    }

    public void setCecTemperatura(Float cecTemperatura) {
        this.cecTemperatura = cecTemperatura;
    }

    public Float getCecBarometrica() {
        return cecBarometrica;
    }

    public void setCecBarometrica(Float cecBarometrica) {
        this.cecBarometrica = cecBarometrica;
    }

    public Float getCecNivelagua() {
        return cecNivelagua;
    }

    public void setCecNivelagua(Float cecNivelagua) {
        this.cecNivelagua = cecNivelagua;
    }

    public Float getCecPluviometro() {
        return cecPluviometro;
    }

    public void setCecPluviometro(Float cecPluviometro) {
        this.cecPluviometro = cecPluviometro;
    }

    public Date getCecDatahora() {
        return cecDatahora;
    }

    public void setCecDatahora(Date cecDatahora) {
        this.cecDatahora = cecDatahora;
    }

    public String getCecNota() {
        return cecNota;
    }

    public void setCecNota(String cecNota) {
        this.cecNota = cecNota;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cecId != null ? cecId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CriseEstacaoCaptura)) {
            return false;
        }
        CriseEstacaoCaptura other = (CriseEstacaoCaptura) object;
        if ((this.cecId == null && other.cecId != null) || (this.cecId != null && !this.cecId.equals(other.cecId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bditacsimul.CriseEstacaoCaptura[ cecId=" + cecId + " ]";
    }

    public Float getCecUmidade() {
        return cecUmidade;
    }

    public void setCecUmidade(Float cecUmidade) {
        this.cecUmidade = cecUmidade;
    }
    
}
