package br.ita.junit.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import br.ita.bditac.model.Real;

@RunWith(Theories.class)
public class RealTheoriesTest {
	
	@DataPoints public static double[] valores() {
		return new double[] {
				0.0,
				5.0,
				10.0
		};
	}
	
	@DataPoints public static int[] divisores() {
		return new int[] {
				0,
				1,
				2
		};
	}
	
	@Theory
	public void multiplicarInversoDividir(double valor, int divisor) {
		assumeThat(divisor, not(0));
		assertThat(new Real(valor).vezes(divisor).divididoPor(divisor).getValor(), is(valor));
	}
	
}
