package br.ita.bditac.support;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import br.ita.bditac.model.Option;
import br.ita.bditac.service.OptionController;


public class OptionResourceAssembler extends ResourceAssemblerSupport<Option, OptionResource> {

    public OptionResourceAssembler() {
        super(OptionController.class, OptionResource.class);
    }
    
    @Override
    public OptionResource toResource(Option option) {
        OptionResource resource = createResourceWithId(-1, option);
        
        return resource;
    }
    
    @Override
    protected OptionResource instantiateResource(Option option) {
        return new OptionResource(option);
    }

}
