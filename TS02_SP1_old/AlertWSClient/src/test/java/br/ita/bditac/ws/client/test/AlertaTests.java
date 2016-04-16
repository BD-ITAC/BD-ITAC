package br.ita.bditac.ws.client.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.ita.bditac.ws.client.AlertaClient;
import br.ita.bditac.ws.model.Alerta;
import junit.framework.TestCase;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AlertaTests extends TestCase {

    private static final String HOST_URL = "http://localhost:8080";
    
    @Test
    public void test01PostAlerta() {
        AlertaClient alertaClient = new AlertaClient(HOST_URL);
        List<String> endereco = new ArrayList<String>();
        endereco.add("rua das Casas");
        endereco.add("numero das Portas");
        Alerta alertaNovo = new Alerta(
                "Alerta de deslizamento",
                "Perigo de deslizamento na altura do Km 20 da rodovia Tamoios, pista Sao Jose dos Campos/Litoral",
                5,
                5,
                0,
                0.50,
                0.50,
                1.0,
                0.50,
                0.50,
                1.0,
                endereco);
        Alerta alertaRetorno = alertaClient.addAlerta(alertaNovo);
        assertNotNull(alertaRetorno);
        assertEquals("Resposta(descricao):'" + alertaRetorno.getDescricaoResumida() + "' do POST diferente do que foi enviado: '" + alertaNovo.getDescricaoResumida() + "'!", alertaRetorno.getDescricaoResumida(), alertaNovo.getDescricaoResumida());
    }
    
    @Test
    public void test02GetAlertaById() {
        AlertaClient alertaClient = new AlertaClient(HOST_URL);
        Alerta alerta = alertaClient.getAlertaById(1);
        assertNotNull(alerta);
        assertEquals("Resposta(descricao):'" + alerta.getDescricaoResumida() + "' do POST diferente do que foi enviado!", alerta.getDescricaoResumida(), "Alerta de deslizamento");
    }
    
    @Test
    public void test03GetAlertaByIdInexistente() {
        AlertaClient alertaClient = new AlertaClient(HOST_URL);
        Alerta alerta = alertaClient.getAlertaById(100);
        assertNull(alerta);
    }
    
    @Test
    public void test04GetAlertaByRegiao() {
        AlertaClient alertaClient = new AlertaClient(HOST_URL);
        List<Alerta> alertas = alertaClient.getAlertaByRegiao(0.5D, 0.5D, 1D);
        if(alertas.size() == 0) {
            fail("Não localizou os alertas experados na região!");
        }
        assertEquals("Resposta(descricao):'" + alertas.get(0).getDescricaoResumida() + "' do POST diferente do que foi enviado!", alertas.get(0).getDescricaoResumida(), "Alerta de deslizamento");
    }
    
    @Test
    public void test05GetAlertaByRegiaoInexistente() {
        AlertaClient alertaClient = new AlertaClient(HOST_URL);
        List<Alerta> alertas = alertaClient.getAlertaByRegiao(0.6D, 0.6D, 10D);
        assertEquals("Resposta inexperada!", alertas.size(), 0);
    }
    
    @Test
    public void test06GetRegiaoComAlerta() {
        AlertaClient alertaClient = new AlertaClient(HOST_URL);
        assertTrue("Falta de alerta inexperado em região com alertas", alertaClient.hasAlerta(0.5D, 0.5D, 1D));
    }
    
    @Test
    public void test75GetRegiaoSemAlerta() {
        AlertaClient alertaClient = new AlertaClient(HOST_URL);
        assertFalse("Alerta inexperado em região sem alertas", alertaClient.hasAlerta(0.6D, 0.6D, 10D));
    }
    
}
