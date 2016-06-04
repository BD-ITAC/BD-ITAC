package br.ita.junit.test;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Categories.class)
@IncludeCategory(CategoriaComestiveis.class)
@ExcludeCategory(CategoriaComercializaveis.class)
@SuiteClasses({CategoriasAnimaisTest.class, CategoriasVegetaisTest.class})
public class CategoriasComestiveisNaoComercializaveisTest {

	/*
	 *   Deve executar:
	 *   	CategoriasAnimaisTest.veado();
	 *   	CategoriasVegetaisTest.coca();
	 */
}
