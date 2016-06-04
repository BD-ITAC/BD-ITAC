package br.ita.bditac.model;

import java.io.Serializable;
import java.util.List;

public class Option implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String method;
    
    private String description;
    
    private List<OptionParameter> parameters;

    public Option(String method, String description, List<OptionParameter> parameters) {
        this.method = method;
        this.description = description;
        this.parameters = parameters;
    }

    public String getMethod() {
        return this.method;
    }
    
    public String getDescription() {
        return this.description;
    }

    public List<OptionParameter> getParameters() {
        return parameters;
    }
    
}
