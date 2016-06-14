package br.ita.bditac.ws.client.alerta;


import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;

import br.ita.bditac.ws.client.AvisoClient;
import br.ita.bditac.ws.model.Aviso;

import static junit.framework.Assert.assertNotNull;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AvisoTest {

    private static final String HOST_URL = "http://200.144.14.28/rest/alerts";

    @Test
    public void test01GetCategorias() {
        AvisoClient avisoClient = new AvisoClient(HOST_URL);
        List<Aviso> avisos = avisoClient.getAvisos();
        assertNotNull(avisos);
    }

}
