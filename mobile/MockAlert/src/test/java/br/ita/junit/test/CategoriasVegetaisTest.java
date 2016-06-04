package br.ita.junit.test;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class CategoriasVegetaisTest {

	@Category({CategoriaComercializaveis.class, CategoriaComestiveis.class})
	@Test public void batata() {}

	@Category({CategoriaComestiveis.class})
	@Test public void coca() {}

	@Category({CategoriaComercializaveis.class})
	@Test public void rosa() {}

}
