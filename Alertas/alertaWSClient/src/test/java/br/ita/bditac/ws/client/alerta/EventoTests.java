package br.ita.bditac.ws.client.alerta;


import junit.framework.TestCase;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.ita.bditac.ws.client.EventoClient;
import br.ita.bditac.ws.model.Evento;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EventoTests extends TestCase {

    private static final String HOST_URL = "http://10.0.2.2:8080";
    
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
    
    @Test
    public void test02GetEventoById() {
        EventoClient eventoClient = new EventoClient(HOST_URL);
        Evento evento = eventoClient.getEventoById(1);
        assertNotNull(evento);
        assertEquals("Resposta(descricao):'" + evento.getDescricao() + "' do POST diferente do que foi enviado!", evento.getDescricao(), "Deslizamento na favela do Paraiso");
    }
    
    @Test
    public void test03GetEventoByIdInexistente() {
        EventoClient eventoClient = new EventoClient(HOST_URL);
        Evento evento = eventoClient.getEventoById(100);
        assertNull(evento);
    }
    
}
