package br.ita.bditac.ws.client.alerta;


import junit.framework.TestCase;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.ita.bditac.ws.client.EventoClient;
import br.ita.bditac.ws.model.Evento;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EventoTests extends TestCase {

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
     *          "latitude": 50,
     *          "longitude": 50,
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
     *          "latitude": 50,
     *          "longitude": 50,
     *          }
     *      }
     * --
     *
     */
    @Test
    public void test01PostEvento() {
        EventoClient eventoClient = new EventoClient(HOST_URL);
        Evento eventoNovo = new Evento(
                "Deslizamento na favela do Paraiso",
                0,
                "Ze das Couves",
                "zedascouves@gmail.com",
                "(12) 99876-1234",
                50.0D,
                50.0D);
        Evento eventoRetorno = eventoClient.addEvento(eventoNovo);
        assertNotNull(eventoRetorno);
        assertEquals("Resposta(descricao):'" + eventoRetorno.getDescricao() + "' do POST diferente do que foi enviado: '" + eventoNovo.getDescricao() + "'!", eventoRetorno.getDescricao(), eventoNovo.getDescricao());
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
        EventoClient eventoClient = new EventoClient(HOST_URL);
        Evento evento = eventoClient.getEventoById(1);
        assertNotNull(evento);
        assertEquals("Resposta(descricao):'" + evento.getDescricao() + "' do POST diferente do que foi enviado!", evento.getDescricao(), "Deslizamento na favela do Paraiso");
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
        EventoClient eventoClient = new EventoClient(HOST_URL);
        Evento evento = eventoClient.getEventoById(100);
        assertNull(evento);
    }
    
}
