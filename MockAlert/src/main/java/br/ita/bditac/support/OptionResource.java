package br.ita.bditac.support;

import org.springframework.hateoas.ResourceSupport;

import br.ita.bditac.model.Option;

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
