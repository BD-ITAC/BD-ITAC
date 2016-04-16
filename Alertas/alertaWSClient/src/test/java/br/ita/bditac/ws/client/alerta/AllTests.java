package br.ita.bditac.ws.client.alerta;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.ita.bditac.ws.model.Indicadores;


@RunWith(Suite.class)
@SuiteClasses({ EventoTests.class, AlertaTests.class, IndicadoresTests.class })
public class AllTests {

}
