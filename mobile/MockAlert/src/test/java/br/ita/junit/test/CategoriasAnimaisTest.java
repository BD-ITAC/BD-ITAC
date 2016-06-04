package br.ita.junit.test;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class CategoriasAnimaisTest {

	@Category({CategoriaComercializaveis.class, CategoriaComestiveis.class})
	@Test public void porco() {}

	@Category({CategoriaComestiveis.class})
	@Test public void veado() {}

	@Category({CategoriaComercializaveis.class})
	@Test public void cachorro() {}

}
