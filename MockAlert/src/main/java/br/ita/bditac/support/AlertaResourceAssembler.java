package br.ita.bditac.support;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import br.ita.bditac.model.Alerta;
import br.ita.bditac.service.AlertaController;

@Component
public class AlertaResourceAssembler extends ResourceAssemblerSupport<Alerta, AlertaResource> {

    public AlertaResourceAssembler() {
        super(AlertaController.class, AlertaResource.class);
    }
    
    public AlertaResource toResource(Alerta alerta) {
        AlertaResource resource = createResourceWithId(alerta.getId(), alerta);
        
        return resource;
    }    
    
    @Override
    protected AlertaResource instantiateResource(Alerta alerta) {
        return new AlertaResource(alerta);
    }
    
}
