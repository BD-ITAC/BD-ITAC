package br.ita.bditac.ws.model;

import java.io.Serializable;
import java.util.Map;

public class EventoResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Evento evento; 

    private Map<String, Map<String, String>> _links;

    
    public Evento getEvento() {
        return evento;
    }

    
    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    
    public Map<String, Map<String, String>> get_links() {
        return _links;
    }

    
    public void set_links(Map<String, Map<String, String>> _links) {
        this._links = _links;
    }
    
}
