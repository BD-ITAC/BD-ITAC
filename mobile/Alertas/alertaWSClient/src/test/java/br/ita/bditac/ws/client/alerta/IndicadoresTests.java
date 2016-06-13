package br.ita.bditac.ws.client.alerta;


import junit.framework.TestCase;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;

import br.ita.bditac.ws.client.IndicadoresClient;
import br.ita.bditac.ws.model.Indicador;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IndicadoresTests extends TestCase {

    private static final String HOST_URL = "http://200.144.14.28/rest/avisos/indicators";

    @Test
    public void test01GetIndicadores() {
        IndicadoresClient indicadoresClient = new IndicadoresClient(HOST_URL);
        List<Indicador> indicadores = indicadoresClient.getIndicadores();
        assertNotNull(indicadores);
    }

}
