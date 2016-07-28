package org.spark.model.pojo;

public enum CriseType {
	TWITTER(0), FACEBOOK(1);
	
	private int tipo;
	CriseType(int valor) {
		tipo = valor;
	}
	
	public int getTipo() {
		return tipo;
	}

}
