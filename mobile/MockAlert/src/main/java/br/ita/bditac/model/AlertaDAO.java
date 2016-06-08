package br.ita.bditac.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import br.ita.bditac.ws.support.Haversine;

@Component
public class AlertaDAO {
    
    private static Map<Integer, Alerta> _alertas = new HashMap<Integer, Alerta>();
    
    private static Map<Integer, Crise> _crises = new HashMap<Integer, Crise>();
    
    private static Map<Integer, Categoria> _categorias = new HashMap<Integer, Categoria>();
    
    private static Map<Integer, Indicador> _indicadores = new HashMap<Integer, Indicador>();

    private static Map<Integer, Message> _messages = new HashMap<Integer, Message>();
    
    static {
        _messages.put(1, new Message(1, Message.Type.INFO, "001", "Ok", "Everything is all right!"));
        _messages.put(2, new Message(2, Message.Type.ERROR, "999", "Error", "Something went very badly"));
        
        _indicadores.put(1, new Indicador("Abertos", 5));
        _indicadores.put(2, new Indicador("Em andamento", 1));
        _indicadores.put(3, new Indicador("Encerrados", 4));
        
        _categorias.put(1, new Categoria("Alagamento"));        
        _categorias.put(2, new Categoria("Incêndio"));        
        _categorias.put(3, new Categoria("Deslizamento"));        
    }

    public static Alerta adicionarAlerta(Alerta alerta) {
        Alerta novoAlerta = new Alerta(
        		alerta.getDescricaoResumida(),
        		alerta.getDescricaoCompleta(),
        		alerta.getCategoriaAlerta(),
        		alerta.getOrigemLatitude(),
        		alerta.getOrigemLongitude(),
        		alerta.getOrigemRaioKms());
        
        _alertas.put(novoAlerta.getId(),  novoAlerta);
        
        return novoAlerta;
    }
    
    public static boolean obterAlerta(long timestamp, double latitude, double longitude, double raio) {
        List<Alerta> alertasPorRegiao = new ArrayList<Alerta>();
        
        for (Alerta alerta : _alertas.values()) {
            if (alerta.getTimestamp() > timestamp && Haversine.distance(alerta.getOrigemLatitude(), alerta.getOrigemLongitude(), latitude, longitude) <= raio) {
                alertasPorRegiao.add(alerta);
                break;
            }
        }
        
        return alertasPorRegiao.size() != 0;
    }
    
    public static Alerta obterAlerta(int id) {
        return _alertas.get(id);
    }
    
    public static List<Alerta> obterAlertasPorRegiao(long timestamp, double latitude, double longitude, double raio) {
        List<Alerta> alertasPorRegiao = new ArrayList<Alerta>();
        
        for (Alerta alerta : _alertas.values()) {
            if (alerta.getTimestamp() > timestamp && Haversine.distance(alerta.getOrigemLatitude(), alerta.getOrigemLongitude(), latitude, longitude) <= raio) {
                alertasPorRegiao.add(alerta);
            }
        }
        
        return alertasPorRegiao;
    }
    
    public static void removerAlerta(int id) {
    	_alertas.remove(id);
    }
    
    public static Crise adicionarCrise(Crise criseRequest) {
    	
        Crise novaCrise = new Crise(
        		criseRequest.getDescricao(),
        		criseRequest.getCategoria(),
        		criseRequest.getNome(),
        		criseRequest.getEmail(),
        		criseRequest.getTelefone(),
        		criseRequest.getLatitude(),
        		criseRequest.getLongitude(),
        		criseRequest.getFotografia());

        _crises.put(novaCrise.getId(), novaCrise);
        
        return novaCrise;
    }
    
    public static Crise obterCrise(int id) {
        Crise crise = _crises.get(id);
        
        return crise;
    }
    
    public static List<Indicador> obterIndicadores(double latitude, double longitude, double raio) {
    	List<Indicador> indicadores = new ArrayList<Indicador>();
    	
    	for(Indicador indicador : _indicadores.values()) {
    		indicadores.add(indicador);
    	}
    	
    	return indicadores;
    }

    public static Categoria obterCategoria(int id) {
    	Categoria categoria = _categorias.get(id);
    	
    	return categoria;
    }
    
    public static List<Categoria> obterCategorias() {
    	List<Categoria> categorias = new ArrayList<Categoria>();
    	
    	for(Categoria categoria : _categorias.values()) {
    		categorias.add(categoria);
    	}
    	
    	return categorias;
    }
    
    public static Message obterMessage(int id) {
        Message message = _messages.get(id);

        return message;
    }

    public static Message obterMessageInfo(int id, String info) {
        Message message = _messages.get(id);
        Message resourceMessage = new Message(message.getId(), message.getType(), message.getStatus(), message.getDescription(), info);

        return resourceMessage;
    }

    public static List<Message> obterMessages() {
        return new ArrayList<Message>(_messages.values());
    }

}
