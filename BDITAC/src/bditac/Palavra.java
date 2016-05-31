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
@Table(name = "palavra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Palavra.findAll", query = "SELECT p FROM Palavra p"),
    @NamedQuery(name = "Palavra.findByPalId", query = "SELECT p FROM Palavra p WHERE p.palId = :palId"),
    @NamedQuery(name = "Palavra.findByPalPalavra", query = "SELECT p FROM Palavra p WHERE p.palPalavra = :palPalavra"),
    @NamedQuery(name = "Palavra.findByPalTipo", query = "SELECT p FROM Palavra p WHERE p.palTipo = :palTipo"),
    @NamedQuery(name = "Palavra.findByPalFeminino", query = "SELECT p FROM Palavra p WHERE p.palFeminino = :palFeminino"),
    @NamedQuery(name = "Palavra.findByPalDiminutivo", query = "SELECT p FROM Palavra p WHERE p.palDiminutivo = :palDiminutivo"),
    @NamedQuery(name = "Palavra.findByPalAumentativo", query = "SELECT p FROM Palavra p WHERE p.palAumentativo = :palAumentativo"),
    @NamedQuery(name = "Palavra.findByPalPlural", query = "SELECT p FROM Palavra p WHERE p.palPlural = :palPlural"),
    @NamedQuery(name = "Palavra.findByPalSentimento", query = "SELECT p FROM Palavra p WHERE p.palSentimento = :palSentimento"),
    @NamedQuery(name = "Palavra.findByPalTempo", query = "SELECT p FROM Palavra p WHERE p.palTempo = :palTempo"),
    @NamedQuery(name = "Palavra.findByPalInfinitivo", query = "SELECT p FROM Palavra p WHERE p.palInfinitivo = :palInfinitivo")})
public class Palavra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pal_id")
    private Integer palId;
    @Basic(optional = false)
    @Column(name = "pal_palavra")
    private String palPalavra;
    @Column(name = "pal_tipo")
    private Character palTipo;
    @Column(name = "pal_feminino")
    private Integer palFeminino;
    @Column(name = "pal_diminutivo")
    private Integer palDiminutivo;
    @Column(name = "pal_aumentativo")
    private Integer palAumentativo;
    @Column(name = "pal_plural")
    private Integer palPlural;
    @Column(name = "pal_sentimento")
    private Character palSentimento;
    @Column(name = "pal_tempo")
    private Character palTempo;
    @Column(name = "pal_infinitivo")
    private Integer palInfinitivo;

    public Palavra() {
    }

    public Palavra(Integer palId) {
        this.palId = palId;
    }

    public Palavra(Integer palId, String palPalavra) {
        this.palId = palId;
        this.palPalavra = palPalavra;
    }

    public Integer getPalId() {
        return palId;
    }

    public void setPalId(Integer palId) {
        this.palId = palId;
    }

    public String getPalPalavra() {
        return palPalavra;
    }

    public void setPalPalavra(String palPalavra) {
        this.palPalavra = palPalavra;
    }

    public Character getPalTipo() {
        return palTipo;
    }

    public void setPalTipo(Character palTipo) {
        this.palTipo = palTipo;
    }

    public Integer getPalFeminino() {
        return palFeminino;
    }

    public void setPalFeminino(Integer palFeminino) {
        this.palFeminino = palFeminino;
    }

    public Integer getPalDiminutivo() {
        return palDiminutivo;
    }

    public void setPalDiminutivo(Integer palDiminutivo) {
        this.palDiminutivo = palDiminutivo;
    }

    public Integer getPalAumentativo() {
        return palAumentativo;
    }

    public void setPalAumentativo(Integer palAumentativo) {
        this.palAumentativo = palAumentativo;
    }

    public Integer getPalPlural() {
        return palPlural;
    }

    public void setPalPlural(Integer palPlural) {
        this.palPlural = palPlural;
    }

    public Character getPalSentimento() {
        return palSentimento;
    }

    public void setPalSentimento(Character palSentimento) {
        this.palSentimento = palSentimento;
    }

    public Character getPalTempo() {
        return palTempo;
    }

    public void setPalTempo(Character palTempo) {
        this.palTempo = palTempo;
    }

    public Integer getPalInfinitivo() {
        return palInfinitivo;
    }

    public void setPalInfinitivo(Integer palInfinitivo) {
        this.palInfinitivo = palInfinitivo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (palId != null ? palId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Palavra)) {
            return false;
        }
        Palavra other = (Palavra) object;
        if ((this.palId == null && other.palId != null) || (this.palId != null && !this.palId.equals(other.palId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bditac.Palavra[ palId=" + palId + " ]";
    }
    
}
