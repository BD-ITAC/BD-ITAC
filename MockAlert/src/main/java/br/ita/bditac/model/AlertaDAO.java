package br.ita.bditac.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import br.ita.bditac.support.Haversine;

@Component
public class AlertaDAO {
    
    private Map<Integer, Alerta> alertas = new HashMap<Integer, Alerta>();
    
    private int _idAlerta;
    
    private Map<Integer, Crise> crises = new HashMap<Integer, Crise>();
    
    private int _idcrise;

    private Map<Integer, Message> messages = new HashMap<Integer, Message>();
    
    private Indicadores indicadores;
    
    public AlertaDAO() {
        _idAlerta = 0;
        _idcrise = 0;

        messages.put(1, new Message(1, Message.Type.INFO, "001", "Ok", "Everything is all right!"));
        messages.put(2, new Message(2, Message.Type.ERROR, "999", "Error", "Something went very badly"));
    }

    public Alerta adicionarAlerta(Alerta alerta) {
        Alerta novoAlerta = new Alerta(
        		alerta.getTimestamp(),
        		alerta.getDescricaoResumida(),
        		alerta.getDescricaoCompleta(),
        		alerta.getCategoriaAlerta(),
        		alerta.getOrigemLatitude(),
        		alerta.getOrigemLongitude(),
        		alerta.getOrigemRaioKms());
        
        novoAlerta.setId(++_idAlerta);
        
        alertas.put(novoAlerta.getId(),  novoAlerta);
        
        return novoAlerta;
    }
    
    public boolean obterAlerta(double latitude, double longitude, double raio) {
        List<Alerta> alertasPorRegiao = new ArrayList<Alerta>();
        
        for (Alerta alerta : alertas.values()) {
            if (Haversine.distance(alerta.getOrigemLatitude(), alerta.getOrigemLongitude(), latitude, longitude) <= raio) {
                alertasPorRegiao.add(alerta);
                break;
            }
        }
        
        return alertasPorRegiao.size() != 0;
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
    
    public void removerAlerta(int id) {
    	alertas.remove(id);
    }
    
    public Crise adicionarcrise(Crise criseRequest) {
        Crise novaCrise = new Crise(
        		criseRequest.getDescricao(),
        		criseRequest.getCategoria(),
        		criseRequest.getNome(),
        		criseRequest.getEmail(),
        		criseRequest.getTelefone(),
        		criseRequest.getLatitude(),
        		criseRequest.getLongitude());

        novaCrise.setId(++_idcrise);
        
        crises.put(novaCrise.getId(), novaCrise);
        
        return novaCrise;
    }
    
    public Crise obtercrise(int id) {
        Crise crise = crises.get(id);
        
        return crise;
    }
    
    public Indicadores adicionarIndicador(String nome, int valor) {
    	this.indicadores.addIndicador(nome, valor);
    	
    	return this.indicadores;
    }
    
    public Indicadores adicionarIndicadores(Indicadores indicadores) {
    	this.indicadores = indicadores;
    	
    	return this.indicadores;
    }
    
    public Indicadores obterIndicadores() {
    	return this.indicadores;
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
