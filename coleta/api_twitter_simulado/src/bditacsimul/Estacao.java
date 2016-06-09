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
@Table(name = "estacao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estacao.findAll", query = "SELECT e FROM Estacao e"),
    @NamedQuery(name = "Estacao.findByEstId", query = "SELECT e FROM Estacao e WHERE e.estId = :estId"),
    @NamedQuery(name = "Estacao.findByCreId", query = "SELECT e FROM Estacao e WHERE e.creId = :creId"),
    @NamedQuery(name = "Estacao.findByEstTemperatura", query = "SELECT e FROM Estacao e WHERE e.estTemperatura = :estTemperatura"),
    @NamedQuery(name = "Estacao.findByEstBarometrica", query = "SELECT e FROM Estacao e WHERE e.estBarometrica = :estBarometrica"),
    @NamedQuery(name = "Estacao.findByEstNivelagua", query = "SELECT e FROM Estacao e WHERE e.estNivelagua = :estNivelagua"),
    @NamedQuery(name = "Estacao.findByEstPluviometro", query = "SELECT e FROM Estacao e WHERE e.estPluviometro = :estPluviometro"),
    @NamedQuery(name = "Estacao.findByEstDatahora", query = "SELECT e FROM Estacao e WHERE e.estDatahora = :estDatahora"),
    @NamedQuery(name = "Estacao.findByEstNota", query = "SELECT e FROM Estacao e WHERE e.estNota = :estNota"),
    @NamedQuery(name = "Estacao.findByEstLido", query = "SELECT e FROM Estacao e WHERE e.estLido = :estLido")})
public class Estacao implements Serializable {

    @Column(name = "est_umidade")
    private Float estUmidade;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "est_id")
    private Integer estId;
    @Basic(optional = false)
    @Column(name = "cre_id")
    private int creId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "est_temperatura")
    private Float estTemperatura;
    @Column(name = "est_barometrica")
    private Float estBarometrica;
    @Column(name = "est_nivelagua")
    private Float estNivelagua;
    @Column(name = "est_pluviometro")
    private Float estPluviometro;
    @Column(name = "est_datahora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estDatahora;
    @Column(name = "est_nota")
    private String estNota;
    @Basic(optional = false)
    @Column(name = "est_lido")
    private Character estLido;

    public Estacao() {
    }

    public Estacao(Integer estId) {
        this.estId = estId;
    }

    public Estacao(Integer estId, int creId, Character estLido) {
        this.estId = estId;
        this.creId = creId;
        this.estLido = estLido;
    }

    public Integer getEstId() {
        return estId;
    }

    public void setEstId(Integer estId) {
        this.estId = estId;
    }

    public int getCreId() {
        return creId;
    }

    public void setCreId(int creId) {
        this.creId = creId;
    }

    public Float getEstTemperatura() {
        return estTemperatura;
    }

    public void setEstTemperatura(Float estTemperatura) {
        this.estTemperatura = estTemperatura;
    }

    public Float getEstBarometrica() {
        return estBarometrica;
    }

    public void setEstBarometrica(Float estBarometrica) {
        this.estBarometrica = estBarometrica;
    }

    public Float getEstNivelagua() {
        return estNivelagua;
    }

    public void setEstNivelagua(Float estNivelagua) {
        this.estNivelagua = estNivelagua;
    }

    public Float getEstPluviometro() {
        return estPluviometro;
    }

    public void setEstPluviometro(Float estPluviometro) {
        this.estPluviometro = estPluviometro;
    }

    public Date getEstDatahora() {
        return estDatahora;
    }

    public void setEstDatahora(Date estDatahora) {
        this.estDatahora = estDatahora;
    }

    public String getEstNota() {
        return estNota;
    }

    public void setEstNota(String estNota) {
        this.estNota = estNota;
    }

    public Character getEstLido() {
        return estLido;
    }

    public void setEstLido(Character estLido) {
        this.estLido = estLido;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estId != null ? estId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estacao)) {
            return false;
        }
        Estacao other = (Estacao) object;
        if ((this.estId == null && other.estId != null) || (this.estId != null && !this.estId.equals(other.estId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bditacsimul.Estacao[ estId=" + estId + " ]";
    }

    public Float getEstUmidade() {
        return estUmidade;
    }

    public void setEstUmidade(Float estUmidade) {
        this.estUmidade = estUmidade;
    }
    
}
