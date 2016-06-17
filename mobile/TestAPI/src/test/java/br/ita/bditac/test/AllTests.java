package br.ita.bditac.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ APIGetTests.class, APIPostTests.class })
public class AllTests {

}
