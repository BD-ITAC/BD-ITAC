package br.ita.bditac.ws.client.alerta;


import junit.framework.TestCase;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.ita.bditac.ws.client.IndicadoresClient;
import br.ita.bditac.ws.model.Indicadores;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IndicadoresTests extends TestCase {

    private static final String HOST_URL = "http://localhost:8080";

    /**
     * = TS02-US06
     *
     * == Asserção:
     *
     * Testa a obtenção dos indicadores utilizando o serviço de Alertas.
     *
     * == Dados:
     *
     * N/A.
     *
     * == Execução:
     *
     * Uma chamada ao serviço de Alertas
     *
     * == Resultado esperado:
     *
     * Uma estrutura de dados com a informação dos indicadores disponíveis e seus valores.
     *
     * === Estrutura de Dados
     *
     * [source,json]
     * --
     * {
     *      "indicadores": {
     *          "Cadastrados": 31,
     *          "Finalizados": 19,
     *          "Em andamento": 6
     *      }
     * }
     * --
     */
    @Test
    public void test01GetIndicadores() {
        IndicadoresClient indicadoresClient = new IndicadoresClient(HOST_URL);
        Indicadores indicadores = indicadoresClient.getIndicadores();
        assertNotNull(indicadores);
    }

}
