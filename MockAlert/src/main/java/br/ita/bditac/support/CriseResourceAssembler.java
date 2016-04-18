package br.ita.bditac.support;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import br.ita.bditac.model.Crise;
import br.ita.bditac.service.CriseController;

@Component
public class CriseResourceAssembler extends ResourceAssemblerSupport<Crise, CriseResource> {

    public CriseResourceAssembler() {
        super(CriseController.class, CriseResource.class);
    }
    
    public CriseResource toResource(Crise crise) {
        CriseResource resource = createResourceWithId(crise.getId(), crise);
        
        return resource;
    }
    
    @Override
    protected CriseResource instantiateResource(Crise crise) {
        return new CriseResource(crise);
    }
    
}
