package br.ita.bditac.model;

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    private String type;

    private String status;

    private String description;

    private String info;

    public Message() {
        super();

        this.type = null;
        this.status = "";
        this.description = "";
        this.info = "";
    }

    public Message(String type, String status, String description, String info, boolean success, String message, boolean validationError) {
        super();

        this.type = type;
        this.status = status;
        this.description = description;
        this.info = info;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public String getInfo() {
        return info;
    }

}
