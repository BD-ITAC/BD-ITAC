package br.ita.junit.test;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Categories.class)
@IncludeCategory(CategoriaComestiveis.class)
@SuiteClasses({CategoriasAnimaisTest.class, CategoriasVegetaisTest.class})
public class CategoriasComestiveisTest {

	/*
	 *   Deve executar:
	 *   	CategoriasAnimaisTest.porco();
	 *   	CategoriasAnimaisTest.veado();
	 *   	CategoriasVegetaisTest.batata();
	 *   	CategoriasVegetaisTest.coca();
	 */
}
