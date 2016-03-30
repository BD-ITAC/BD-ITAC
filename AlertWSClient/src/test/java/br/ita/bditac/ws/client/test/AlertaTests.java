package br.ita.bditac.ws.client.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.ita.bditac.ws.client.AlertaService;
import br.ita.bditac.ws.model.Alerta;
import junit.framework.TestCase;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AlertaTests extends TestCase {

    private static final String HOST_URL = "http://localhost:8080";
    
    @Test
    public void test01PostAlerta() {
        AlertaService alertaService = new AlertaService(HOST_URL);
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
        Alerta alertaRetorno = alertaService.addAlerta(alertaNovo);
        assertEquals("Resposta(descricao):'" + alertaRetorno.getDescricaoResumida() + "' do POST diferente do que foi enviado: '" + alertaNovo.getDescricaoResumida() + "'!", alertaRetorno.getDescricaoResumida(), alertaNovo.getDescricaoResumida());
    }
    
    @Test
    public void test02GetAlertaById() {
        AlertaService alertaService = new AlertaService(HOST_URL);
        Alerta alerta = alertaService.getAlertaById(1);
        assertEquals("Resposta(descricao):'" + alerta.getDescricaoResumida() + "' do POST diferente do que foi enviado!", alerta.getDescricaoResumida(), "Alerta de deslizamento");
    }
    
}
