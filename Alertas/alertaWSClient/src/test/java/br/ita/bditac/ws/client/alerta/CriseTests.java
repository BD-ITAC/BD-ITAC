package br.ita.bditac.ws.client.alerta;


import junit.framework.TestCase;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.ita.bditac.ws.client.CriseClient;
import br.ita.bditac.ws.model.Crise;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CriseTests extends TestCase {

    private static final String HOST_URL = "http://localhost:8080";

    /**
     *
     * = TS02-US02
     *
     * == Asserção:
     *
     * Testa a inclusão de um evento de crise usando o seriço de Alertas.
     *
     * == Dados:
     *
     * Uma estrutura de dados contendo o evento.
     *
     * === Estrutura de dados
     *
     * [source,json]
     * --
     *      {
     *          "evento" : {
     *          "descricao" : "Deslizamento na na favela do Paraiso",
     *          "categoria" : 0,
     *          "nome" : "Ze das Couves",
     *          "email" : "zedascouves@gmail.com",
     *          "telefone" : "(12) 99876-1234",
     *          "latitude": -25,
     *          "longitude": -45,
     *          }
     *      }
     * --
     *
     * == Execução:
     *
     * Uma chamada ao serviço de Alertas.
     *
     * == Resultado esperado:
     *
     * Uma estrutura de dados com a mesma informação da estrutura enviada.
     *
     * === Estrutura de dados
     *
     * [source,json]
     * --
     *      {
     *          "evento" : {
     *          "descricao" : "Deslizamento na na favela do Paraiso",
     *          "categoria" : 0,
     *          "nome" : "Ze das Couves",
     *          "email" : "zedascouves@gmail.com",
     *          "telefone" : "(12) 99876-1234",
     *          "latitude": -25,
     *          "longitude": -45,
     *          }
     *      }
     * --
     *
     */
    @Test
    public void test01PostEvento() {
        CriseClient criseClient= new CriseClient(HOST_URL);
        Crise criseNova= new Crise(
                "Deslizamento na favela do Paraiso",
                0,
                "Ze das Couves",
                "zedascouves@gmail.com",
                "(12) 99876-1234",
                -25.0D,
                -45.0D);
        Crise criseRetorno= criseClient.addEvento(criseNova);
        assertNotNull(criseRetorno);
        assertEquals("Resposta(descricao):'" + criseRetorno.getDescricao() + "' do POST diferente do que foi enviado: '" + criseNova.getDescricao() + "'!", criseRetorno.getDescricao(), criseNova.getDescricao());
    }

    /**
     *
     * = TS02-US02
     *
     * == Asserção:
     *
     * Testa a obtenção de um evento de crise do seriço de Alertas:
     *
     * == Dados:
     *
     * Identificação do evento na requisição.
     *
     * == Execução:
     *
     * Uma chamada ao serviço de Alertas.
     *
     * == Resultado esperado:
     *
     * Uma estrutura de dados com os dados do evento.
     *
     * === Estrutura de dados
     *
     [source,json]
     * --
     *      {
     *          "evento" : {
     *          "descricao" : "Deslizamento na na favela do Paraiso",
     *          "categoria" : 0,
     *          "nome" : "Ze das Couves",
     *          "email" : "zedascouves@gmail.com",
     *          "telefone" : "(12) 99876-1234",
     *          "latitude": 50,
     *          "longitude": 50,
     *          }
     *      }
     * --
     *
     */
    @Test
    public void test02GetEventoById() {
        CriseClient criseClient= new CriseClient(HOST_URL);
        Crise crise= criseClient.getEventoById(1);
        assertNotNull(crise);
        assertEquals("Resposta(descricao):'" + crise.getDescricao() + "' do POST diferente do que foi enviado!", crise.getDescricao(), "Deslizamento na favela do Paraiso");
    }

    /**
     *
     * = TS02-US02
     *
     * == Asserção:
     *
     * Testa a inclusão de um evento de crise usando o seriço de Alertas:
     *
     * == Dados:
     *
     * Identificação do evento na requisição.
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
    public void test03GetEventoByIdInexistente() {
        CriseClient criseClient= new CriseClient(HOST_URL);
        Crise crise= criseClient.getEventoById(100);
        assertNull(crise);
    }
    
}
