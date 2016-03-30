package br.ita.bditac.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author BD-ITAC
 * 
 * A classe Evento contém os dados necessários para permitir o cadastramento de um evento ad-hoc
 *
 */
public class Evento implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int id;

    @JsonIgnore
    public Integer getId() {
        return id;
    }
    
    @JsonIgnore
    public void setId(int id) {
        this.id = id;
    }
    
    protected String descricao;
    
    /**
     * Indica o tipo de evento:
     * - Enchente
     * - Incêndio
     * - Deslizamento (de terra ou pedras em encostas, margens de rio, lagos ou mar, etc.)
     * - Desmoronamento (prédios, equipamentos públicos (torres, etc.) e instalações industriais
     * - Terremotos
     * - Epidemias
     * - Apocalipse Zumbi
     * - etc.
     */
    protected int categoria;
    
    protected String nome;
    
    protected String email;
    
    protected String telefone;
    
    protected List<String> endereco;

    public Evento() {
        this.id = 0;
        
        this.descricao = "";
        this.categoria = 0;
        this.nome = "";
        this.email = "";
        this.telefone = "";
        this.endereco = new ArrayList<String>();
    }
    
    public Evento(
            String descricao,
            int categoria,
            String nome,
            String email,
            String telefone,
            List<String> endereco) {
        this.id = 0;
        
        this.descricao = descricao;
        this.categoria = categoria;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    
    public String getDescricao() {
        return descricao;
    }

    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    
    public int getCategoria() {
        return categoria;
    }

    
    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }
    

    public String getNome() {
        return nome;
    }

    
    public void setNome(String nome) {
        this.nome = nome;
    }

    
    public String getEmail() {
        return email;
    }

    
    public void setEmail(String email) {
        this.email = email;
    }

    
    public String getTelefone() {
        return telefone;
    }

    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    
    public List<String> getEndereco() {
        return endereco;
    }

    
    public void setEndereco(List<String> endereco) {
        this.endereco = endereco;
    }

    
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    
}
