package br.ita.bditac.ws.support;

import org.springframework.hateoas.ResourceSupport;

import br.ita.bditac.ws.model.Option;

public class OptionResource extends ResourceSupport {
    
    private Option option;
    
    public OptionResource(Option option) {
        super();
        
        this.option = option;
    }
    
    public Option getOption() {
        return option;
    }

}
