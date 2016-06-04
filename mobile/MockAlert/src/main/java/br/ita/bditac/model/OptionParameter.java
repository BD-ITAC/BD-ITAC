package br.ita.bditac.model;

import java.io.Serializable;

public class OptionParameter implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String name;
    
    private String description;
    
    private String type;
    
    private boolean required;
    
    public OptionParameter() {
        this.name = "";
        this.description = "";
        this.type = "";
        this.required = true;
    }
    
    public OptionParameter(String name, String description, String type, boolean required) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.required = required;
    }

    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getType() {
        return type;
    }
    
    public boolean isRequired() {
        return required;
    }

}
