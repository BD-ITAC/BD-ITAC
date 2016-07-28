package br.ita.bditac.ws.client.alerta;


import junit.framework.TestCase;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.ita.bditac.ws.client.CriseClient;
import br.ita.bditac.ws.model.Crise;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CriseTests extends TestCase {

    private static final String HOST_URL = "http://200.144.14.28/rest/avisos";

    @Test
    public void test01PostCrise() {
        CriseClient criseClient= new CriseClient(HOST_URL);
        Crise criseNova= new Crise(
                "Deslizamento na favela do Paraiso",
                0,
                "Ze das Couves",
                "zedascouves@gmail.com",
                "(12) 99876-1234",
                -25.0D,
                -45.0D,
                AlertaTests.getFoto());
        assertTrue(criseClient.addCrise(criseNova));
    }

}
