package br.ita.junit.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Verifier;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class RealVerifyTest {

	private double valor = 5.0;

	private int multiplicador = 2;

	private double resultado = 0.0;

	@Rule public Verifier collector = new Verifier() {
		@Override
		protected void verify() {
			assertThat(resultado, is(not(0.0)));
		}
	};

	@Test
	public void multiplicarPorInteiro() {
		resultado = new Real(valor).vezes(multiplicador).getValor();
		assertThat(resultado, is(10.0));
	}

}
