package br.ita.bditac.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import br.ita.bditac.model.Alerta;
import br.ita.bditac.model.Evento;
import br.ita.bditac.model.Message;
import br.ita.bditac.model.Message.Type;
import br.ita.bditac.support.Haversine;

@Component
public class AlertaService {
    
    private Map<Integer, Alerta> alertas = new HashMap<Integer, Alerta>();
    
    private int _idAlerta;
    
    private Map<Integer, Evento> eventos = new HashMap<Integer, Evento>();
    
    private int _idEvento;

    private Map<Integer, Message> messages = new HashMap<Integer, Message>();
    
    public AlertaService() {
        _idAlerta = 0;
        _idEvento = 0;

        messages.put(1, new Message(1, Message.Type.INFO, "001", "Ok", "Everything is all right!"));
        messages.put(2, new Message(2, Message.Type.ERROR, "999", "Error", "Something went very badly"));
    }

    public Alerta adicionarAlerta(Alerta alerta) {
        Alerta novoAlerta = new Alerta();
        
        novoAlerta.setDescricaoResumida(alerta.getDescricaoResumida());
        novoAlerta.setDescricaoCompleta(alerta.getDescricaoCompleta());
        novoAlerta.setFatorRiscoHumano(alerta.getFatorRiscoHumano());
        novoAlerta.setFatorRiscoMaterial(alerta.getFatorRiscoMaterial());
        novoAlerta.setCategoriaAlerta(alerta.getCategoriaAlerta());
        novoAlerta.setOrigemLatitude(alerta.getOrigemLatitude());
        novoAlerta.setOrigemLongitude(alerta.getOrigemLatitude());
        novoAlerta.setOrigemRaioKms(alerta.getOrigemRaioKms());
        novoAlerta.setDestinoLatitude(alerta.getDestinoLatitude());
        novoAlerta.setDestinoLongitude(alerta.getDestinoLongitude());
        novoAlerta.setDestinoRaioKms(alerta.getDestinoRaioKms());
        novoAlerta.setEndereco(alerta.getEndereco());
        novoAlerta.setId(++_idAlerta);
        
        alertas.put(novoAlerta.getId(),  novoAlerta);
        
        return novoAlerta;
    }
    
    public Alerta obterAlerta(int id) {
        return alertas.get(id);
    }
    
    public List<Alerta> obterAlertasPorRegiao(double latitude, double longitude, double raio) {
        List<Alerta> alertasPorRegiao = new ArrayList<Alerta>();
        
        for (Alerta alerta : alertas.values()) {
            if (Haversine.distance(alerta.getOrigemLatitude(), alerta.getOrigemLongitude(), latitude, longitude) <= raio) {
                alertasPorRegiao.add(alerta);
            }
        }
        
        return alertasPorRegiao;
    }
    
    public Evento adicionarEvento(Evento eventoRequest) {
        Evento novoEvento = new Evento();
        
        novoEvento.setDescricao(eventoRequest.getDescricao());
        novoEvento.setCategoria(eventoRequest.getCategoria());
        novoEvento.setNome(eventoRequest.getNome());
        novoEvento.setEmail(eventoRequest.getEmail());
        novoEvento.setTelefone(eventoRequest.getTelefone());
        novoEvento.setEndereco(eventoRequest.getEndereco());
        novoEvento.setId(++_idEvento);
        
        eventos.put(novoEvento.getId(), novoEvento);
        
        return novoEvento;
    }
    
    public Evento obterEvento(int id) {
        Evento evento = eventos.get(id);
        
        return evento;
    }
    
    public Message obterMessage(int id) {
        Message message = messages.get(id);

        return message;
    }

    public Message obterMessageInfo(int id, String info) {
        Message message = messages.get(id);
        Message resourceMessage = new Message(message.getId(), message.getType(), message.getStatus(), message.getDescription(), info);

        return resourceMessage;
    }

    public List<Message> obterMessages() {
        return new ArrayList<Message>(messages.values());
    }

}
