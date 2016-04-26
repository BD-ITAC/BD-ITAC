package br.ita.bditac.model;


public enum TopicoTipo {

	Diagnosticos("br.ita.bditac/diagnosticos");
	
	private final String id;
	
	private TopicoTipo(String id) {
		this.id = id;
	}
		
	public boolean equalsName(String id) {
		return (id == null) ? false : id.equals(id);
	}
	
	public String toString() {
		return this.id;
	}

}
