package br.ita.bditac.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Categorias implements Serializable {

    private static final long serialVersionUID = 1L;
	
	private static Map<Integer, String> categorias = new HashMap<Integer, String>();
	
	public static void addCategoria(int id, String nome) {
		categorias.put(id, nome);
	}
	
	public static String getCategoria(int id) {
		return categorias.get(id);
	}
	
	public Map<Integer, String> getCategorias() {
		return categorias;
	}

}
