package br.ita.bditac.model;


public class Real {

	private double valor = 0;
	
	public Real(double valor) {
		this.valor = valor;
	}
	
	public Real vezes(int multiplicador) {
		this.valor *= multiplicador;
		
		return this;
	}
	
	public Real divididoPor(int divisor) {
		this.valor /= divisor;
		
		return this;
	}
	
	public double getValor() {
		return this.valor;
	}
	
}
