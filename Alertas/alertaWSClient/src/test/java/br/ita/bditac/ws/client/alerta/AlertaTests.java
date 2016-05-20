package br.ita.bditac.ws.client.alerta;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import junit.framework.TestCase;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;

import br.ita.bditac.ws.client.AlertaClient;
import br.ita.bditac.ws.client.CriseClient;
import br.ita.bditac.ws.model.Alerta;
import br.ita.bditac.ws.model.Crise;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AlertaTests extends TestCase {

    private static final String HOST_URL = "http://localhost:8080";

    private static Bitmap _foto = null;

    public static Bitmap getFoto() {

        try {
            if(_foto == null) {
                _foto = BitmapFactory.decodeFile(Thread.currentThread().getContextClassLoader().getResource("foto.png").getPath());
            }
        }
        catch(Exception ex) {
            _foto = null;
        }

        return _foto;

    }

    /**
     *
     * == Asserção:
     *
     * Testa a obtenção de todos alertas dentro de uma área identificada por um ponto geográfico e um raio no seriço de Alertas:
     *
     * == Dados:
     *
     * Identificação da área de pesquisa desejada.
     *
     * === Dados informados:
     *
     * [source,java]
     * --
     *  double latitude = -25.0;
     *  double longitude = -45.0;
     *  double raio = 1.0;
     * --
     *
     * == Execução:
     *
     * Uma chamada ao serviço de Alertas.
     *
     * == Resultado esperado:
     *
     * Uma lista de dados com os dados dos alertas na área informada.
     *
     * === Estrutura de dados
     *
     * [source,json]
     * --
     *      {
     *          "alerta": {
     *          "descricaoResumida" : "Alerta de deslizamento",
     *          "descricaoCompleta" : "Perigo de deslizamento na altura do Km 20 da rodovia Tamoios, pista Sao Jose dos Campos/Litoral",
     *          "fatorRiscoHumano" : 5,
     *          "fatorRiscoMaterial" : 5,
     *          "categoriaAlerta" : 0,
     *          "origemLatitude" : -25.0,
     *          "origemLongitude" : -45.0,
     *          "origemRaioKms" : 1.0,
     *          }
     *      }
     * --
     *
     */
    @Test
    public void test04GetAlertaByRegiaoTodos() {
        CriseClient criseClient = new CriseClient(HOST_URL);

        Crise criseNova = new Crise(
                "Deslizamento na favela do Paraiso",
                0,
                "Ze das Couves",
                "zedascouves@gmail.com",
                "(12) 99876-1234",
                -25.0D,
                -45.0D,
                getFoto());
        criseClient.addCrise(criseNova);

        AlertaClient alertaClient = new AlertaClient(HOST_URL);
        List<Alerta> alertas = alertaClient.getAlertaByRegiao(-25.0, -45.0, 10);
        if(alertas == null || alertas.size() == 0) {
            fail("Não localizou os alertas esperados na região!");
        }
        assertEquals("Resposta(descricao):'" + criseNova.getDescricao() + "' do POST diferente do que foi enviado!", alertas.get(0).getDescricaoResumida(), "Alerta de deslizamento");
    }

    /**
     *
     * == Asserção:
     *
     * Testa a obtenção de todos alertas recentes de uma área identificada por um ponto geográfico e um raio no seriço de Alertas:
     *
     * == Dados:
     *
     * Identificação da área de pesquisa desejada.
     *
     * === Dados informados:
     *
     * [source,java]
     * --
     *  double latitude = -25.0;
     *  double longitude = -45.0;
     *  double raio = 1.0;
     * --
     *
     * == Execução:
     *
     * Uma chamada ao serviço de Alertas.
     *
     * == Resultado esperado:
     *
     * Uma lista de dados com os dados dos alertas na área informada.
     *
     * === Estrutura de dados
     *
     * [source,json]
     * --
     *      {
     *          "alerta": {
     *          "descricaoResumida" : "Alerta de deslizamento",
     *          "descricaoCompleta" : "Perigo de deslizamento na altura do Km 20 da rodovia Tamoios, pista Sao Jose dos Campos/Litoral",
     *          "fatorRiscoHumano" : 5,
     *          "fatorRiscoMaterial" : 5,
     *          "categoriaAlerta" : 0,
     *          "origemLatitude" : -25.0,
     *          "origemLongitude" : -45.0,
     *          "origemRaioKms" : 1.0,
     *          }
     *      }
     * --
     *
     */
    @Test
    public void test04GetAlertaByRegiaoRecentes() {
        CriseClient criseClient = new CriseClient(HOST_URL);

        Crise criseNova = new Crise(
                "Deslizamento na favela do Paraiso",
                0,
                "Ze das Couves",
                "zedascouves@gmail.com",
                "(12) 99876-1234",
                -25.0D,
                -45.0D,
                getFoto());
        criseClient.addCrise(criseNova);

        AlertaClient alertaClient = new AlertaClient(HOST_URL);
        List<Alerta> alertas = alertaClient.getAlertaByRegiaoRecentes(-25.0, -45.0, 10);
        if(alertas == null || alertas.size() == 0) {
            fail("Não localizou os alertas esperados na região!");
        }
        assertEquals("Resposta(descricao):'" + criseNova.getDescricao() + "' do POST diferente do que foi enviado!", alertas.get(0).getDescricaoResumida(), "Alerta de deslizamento");
    }

    /**
     *
     * == Asserção:
     *
     * Testa a obtenção de alertas dentro de uma área identificada por um ponto geográfico e um raio no seriço de Alertas onde não há alertas:
     *
     * == Dados:
     *
     * Identificação da área de pesquisa desejada.
     *
     * === Dados informados:
     *
     * [source,http]
     * --
     *  /latitude/0.60/longitude/0.60/raio/1
     * --
     *
     * == Execução:
     *
     * Uma chamada ao serviço de Alertas.
     *
     * == Resultado esperado:
     *
     * O código de estado de retorno deve ser o código HTTP 404 (NOT FOUND).
     *
     */
    @Test
    public void test05GetAlertaByRegiaoInexistente() {
        AlertaClient alertaClient = new AlertaClient(HOST_URL);
        List<Alerta> alertas = alertaClient.getAlertaByRegiao(0.6D, 0.6D, 10D);
        assertEquals("Resposta inexperada!", alertas, null);
    }

    /**
     *
     * == Asserção:
     *
     * Testa a obtenção de alertas dentro de uma área identificada por um ponto geográfico e um raio no seriço de Alertas onde não há alertas:
     *
     * == Dados:
     *
     * Identificação da área de pesquisa desejada.
     *
     * === Dados informados:
     *
     * [source,http]
     * --
     *  /latitude/-25.0/longitude/-45.0/raio/1
     * --
     *
     * == Execução:
     *
     * Uma chamada ao serviço de Alertas.
     *
     * == Resultado esperado:
     *
     * O código de estado de retorno deve ser o código HTTP 200 (OK).
     *
     */
    @Test
    public void test06GetRegiaoComAlerta() {
        AlertaClient alertaClient = new AlertaClient(HOST_URL);
        assertTrue("Falta de alerta inexperado em região com alertas", alertaClient.hasAlerta(-25.0D, -45.0D, 10D));
    }

    /**
     *
     * == Asserção:
     *
     * Testa a obtenção de alertas dentro de uma área identificada por um ponto geográfico e um raio no seriço de Alertas onde não há alertas:
     *
     * == Dados:
     *
     * Identificação da área de pesquisa desejada.
     *
     * === Dados informados:
     *
     * [source,http]
     * --
     *  /latitude/0.60/longitude/0.60/raio/1
     * --
     *
     * == Execução:
     *
     * Uma chamada ao serviço de Alertas.
     *
     * == Resultado esperado:
     *
     * O código de estado de retorno deve ser o código HTTP 404 (NOT FOUND).
     *
     */
    @Test
    public void test07GetRegiaoSemAlerta() {
        AlertaClient alertaClient = new AlertaClient(HOST_URL);
        assertFalse("Alerta inexperado em região sem alertas", alertaClient.hasAlerta(0.6D, 0.6D, 10D));
    }

}
