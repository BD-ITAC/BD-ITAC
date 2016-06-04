package br.ita.junit.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import br.ita.bditac.model.Real;


@RunWith(JUnit4.class)
public class RealTest {

	@Test
	public void multiplicarPorInteiro() {

		assertThat(new Real(5).vezes(2).getValor(), is(10.0));
		
	}
		
}