package br.ita.bditac.model;


public enum Categorias {

	Alagamento("Alagamento"),
	Incendio("Incêndio"),
	AcidenteVeicular("Acidente Veicular"),
	AcidenteAereo("Acidente Aéreo"),
	Assassinato("Assassinato"),
	Roubo("Roubo"),
	Terremoto("Terremoto"),
	Desmoronamento("Desmoronamento"),
	Tiroteio("Tiroteio");
	
	private final String name;
	
	private Categorias(String name) {
		this.name = name;
	}
	
	public boolean equalsName(String otherName) {
		return (otherName == null) ? false : name.equals(otherName);
	}
	
	public String toString() {
		return this.name;
	}
	
}
