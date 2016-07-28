package br.ita.junit.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class RealParameterTest {

	@Parameter(value = 0) public double valor;
	@Parameter(value = 1) public int i;
	
	@Parameters public static Collection<Object[]> data() {
		Object[][] data = new Object[][] {
			{0.0, 0},
			{5.0, 1},
			{10.0, 2}
		};
		
		return Arrays.asList(data);
	}
	
	@Test
	public void multiplicarPorInteiro() {
		assertThat(new Real(valor).vezes(i).getValor(), is(valor * i));
	}
	
}
