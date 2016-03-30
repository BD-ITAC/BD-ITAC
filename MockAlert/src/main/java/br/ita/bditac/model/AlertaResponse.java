package br.ita.bditac.model;

import java.io.Serializable;
import java.util.Map;

public class AlertaResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Alerta alerta;

    private Map<String, Map<String, String>> _links;

    
    public Alerta getAlerta() {
        return alerta;
    }

    
    public void setAlerta(Alerta alerta) {
        this.alerta = alerta;
    }

    
    public Map<String, Map<String, String>> get_links() {
        return _links;
    }

    
    public void set_links(Map<String, Map<String, String>> _links) {
        this._links = _links;
    }

}
