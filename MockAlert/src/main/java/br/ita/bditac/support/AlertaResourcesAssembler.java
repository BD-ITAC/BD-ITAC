package br.ita.bditac.support;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import br.ita.bditac.model.Alerta;
import br.ita.bditac.service.AlertaController;

public class AlertaResourcesAssembler extends ResourceAssemblerSupport<Alerta, AlertaResources> {

    public AlertaResourcesAssembler() {
        super(AlertaController.class, AlertaResources.class);
    }
    
    public AlertaResources toResource(Alerta alerta) {
        AlertaResources resources = createResourceWithId(alerta.getId(), alerta);
        
        return resources;
    }

}
