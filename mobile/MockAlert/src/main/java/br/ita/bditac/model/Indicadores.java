package br.ita.bditac.model;

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
	
	private static Map<String, Integer> indicadores = new HashMap<String, Integer>();
	
	public static void addIndicador(String nome, int valor) {
		indicadores.put(nome, valor);
	}
	
	public static int getIndicador(String nome) {
		return indicadores.get(nome);
	}
	
	public Map<String, Integer> getIndicadores() {
		return indicadores;
	}

}
