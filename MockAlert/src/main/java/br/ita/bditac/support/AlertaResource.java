package br.ita.bditac.support;

import org.springframework.hateoas.ResourceSupport;

import br.ita.bditac.model.Alerta;

public class AlertaResource extends ResourceSupport {
    
    private Alerta alerta;
    
    public AlertaResource(Alerta alerta) {
        super();
        
        this.alerta = alerta;
    }
    
    public Alerta getAlerta() {
        return alerta;
    }

}
