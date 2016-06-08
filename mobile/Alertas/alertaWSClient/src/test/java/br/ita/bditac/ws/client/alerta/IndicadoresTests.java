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

    private static final String HOST_URL = "http://localhost:8081";

    @Test
    public void test01GetIndicadores() {
        IndicadoresClient indicadoresClient = new IndicadoresClient(HOST_URL);
        List<Indicador> indicadores = indicadoresClient.getIndicadores(-15.7976, -47.8344, 10.0);
        assertNotNull(indicadores);
    }

}
