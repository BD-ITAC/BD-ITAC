package br.ita.bditac.ws.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author BD-ITAC
 *
 * A classe Categorias representa os tipos de alertas e crises possíveis.
 *
 */
public class Categorias implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<Integer, String> categorias;

    public Categorias() {
        this.categorias = new HashMap<Integer, String>();
    }

    public Categorias(Map<Integer, String> categorias) {
        this.categorias = categorias;
    }

    public Map<Integer, String> getCategorias() {
        return this.categorias;
    }

    public void setCategorias(Map<Integer, String> categorias) {
        this.categorias = categorias;
    }

    public void addCategoria(int id, String nome) {
        categorias.put(id, nome);
    }

    public String getCategoria(int id) {
        return categorias.get(id);
    }

    public void clear() {
        this.categorias.clear();
    }

}
