package br.ita.junit.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class RealExptectedExceptionTest {
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	Real saldo = null;
	
	@Test
	public void metadeDeNada() {
		
		thrown.expect(NullPointerException.class);
		
		assertThat(saldo.divididoPor(2).getValor(), is(5.0));
		
	}

}
