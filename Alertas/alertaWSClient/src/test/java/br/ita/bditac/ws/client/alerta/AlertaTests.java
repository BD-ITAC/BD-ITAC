package br.ita.bditac.ws.client.alerta;


import junit.framework.TestCase;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;

import br.ita.bditac.ws.client.AlertaClient;
import br.ita.bditac.ws.model.Alerta;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AlertaTests extends TestCase {

    private static final String HOST_URL = "http://localhost:8080";

    /**
     *
     * == Asserção:
     *
     * Testa a inclusão de um alerta utilizando o serviço de Alertas.
     *
     * == Dados:
     *
     * Uma estrutura de dados com os dados do alerta.
     *
     * === Estrutura de Dados
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
     *          "origemLongitude" : 0.5,
     *          "origemLatitude" : 0.5,
     *          "origemRaioKms" : 1.0,
     *          }
     *      }
     * --
     *
     * == Execução:
     *
     * Uma chamada ao serviço de de Alertas
     *
     * == Resultado esperado:
     *
     * Uma estrutura de dados com a mesma informação da estrutura enviada.
     *
     * === Estrutura de Dados
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
     *          "origemLongitude" : 0.5,
     *          "origemLatitude" : 0.5,
     *          "origemRaioKms" : 1.0,
     *          }
     *      }
     * --
     *
     */
    @Test
    public void test01PostAlerta() {
        AlertaClient alertaClient = new AlertaClient(HOST_URL);
        Alerta alertaNovo = new Alerta(
                "Alerta de deslizamento",
                "Perigo de deslizamento na altura do Km 20 da rodovia Tamoios, pista Sao Jose dos Campos/Litoral",
                5,
                5,
                0,
                0.50,
                0.50,
                1.0);
        Alerta alertaRetorno = alertaClient.addAlerta(alertaNovo);
        assertNotNull(alertaRetorno);
        assertEquals("Resposta(descricao):'" + alertaRetorno.getDescricaoResumida() + "' do POST diferente do que foi enviado: '" + alertaNovo.getDescricaoResumida() + "'!", alertaRetorno.getDescricaoResumida(), alertaNovo.getDescricaoResumida());
    }

    /**
     *
     * == Asserção:
     *
     * Testa a obtenção de um alerta de crise do seriço de Alertas:
     *
     * == Dados:
     *
     * Identificação do alerta na requisição.
     *
     * == Execução:
     *
     * Uma chamada ao serviço de Alertas.
     *
     * == Resultado esperado:
     *
     * Uma estrutura de dados com os dados do alerta.
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
     *          "origemLongitude" : 0.5,
     *          "origemLatitude" : 0.5,
     *          "origemRaioKms" : 1.0,
     *          }
     *      }
     * --
     *
     */
    @Test
    public void test02GetAlertaById() {
        AlertaClient alertaClient = new AlertaClient(HOST_URL);
        Alerta alerta = alertaClient.getAlertaById(1);
        assertNotNull(alerta);
        assertEquals("Resposta(descricao):'" + alerta.getDescricaoResumida() + "' do POST diferente do que foi enviado!", alerta.getDescricaoResumida(), "Alerta de deslizamento");
    }

    /**
     *
     * == Asserção:
     *
     * Testa a obtenção de um alerta de crise do seriço de Alertas:
     *
     * == Dados:
     *
     * Identificação do alerta na requisição.
     *
     * == Execução:
     *
     * Uma chamada ao serviço de Alertas.
     *
     * == Resultado esperado:
     *
     * O código de estado da chamada deve ser o código HTTP 404 (NOT FOUND).
     *
     */
    @Test
    public void test03GetAlertaByIdInexistente() {
        AlertaClient alertaClient = new AlertaClient(HOST_URL);
        Alerta alerta = alertaClient.getAlertaById(100);
        assertNull(alerta);
    }

    /**
     *
     * == Asserção:
     *
     * Testa a obtenção de alertas dentro de uma área identificada por um ponto geográfico e um raio no seriço de Alertas:
     *
     * == Dados:
     *
     * Identificação da área de pesquisa desejada.
     *
     * === Dados informados:
     *
     * [source,java]
     * --
     *  Map<String, Double> params = new HashMap<String, Double>();
     *  params.put("latitude", 50.0);
     *  params.put("longitude", 50.0);
     *  params.put("raio", 1D);
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
     *          "origemLongitude" : 0.5,
     *          "origemLatitude" : 0.5,
     *          "origemRaioKms" : 1.0,
     *          }
     *      }
     * --
     *
     */
    @Test
    public void test04GetAlertaByRegiao() {
        AlertaClient alertaClient = new AlertaClient(HOST_URL);
        List<Alerta> alertas = alertaClient.getAlertaByRegiao(0.5D, 0.5D, 1D);
        if(alertas.size() == 0) {
            fail("Não localizou os alertas experados na região!");
        }
        assertEquals("Resposta(descricao):'" + alertas.get(0).getDescricaoResumida() + "' do POST diferente do que foi enviado!", alertas.get(0).getDescricaoResumida(), "Alerta de deslizamento");
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
     *  /latitude/0.60/longitude/0.60/raio/1
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
        assertTrue("Falta de alerta inexperado em região com alertas", alertaClient.hasAlerta(0.5D, 0.5D, 1D));
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
