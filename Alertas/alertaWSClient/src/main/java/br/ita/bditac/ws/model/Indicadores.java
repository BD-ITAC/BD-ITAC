package br.ita.bditac.ws.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author BD-ITAC
 *
 * A classe Indicadores representa os dados de indicadores de ocorrências de crises.
 *
 */
public class Indicadores implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<String, Integer> indicadores;

    public Indicadores() {
        this.indicadores = new HashMap<String, Integer>();
    }

    public Indicadores(Map<String, Integer> indicadores) {
        this.indicadores = indicadores;
    }

    public Map<String, Integer> getIndicadores() {
        return this.indicadores;
    }

    public void setIndicadores(Map<String, Integer> indicadores) {
        this.indicadores = indicadores;
    }

    public void addIndicador(String nome, int valor) {
        indicadores.put(nome, valor);
    }

    public int getIndicador(String nome) {
        return indicadores.get(nome);
    }

    public void clear() {
        this.indicadores.clear();
    }

}
