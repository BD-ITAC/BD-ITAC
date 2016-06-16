package br.ita.bditac.ws.model;


import java.io.Serializable;


public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    public enum Type {

        INFO(0, "Information"), WARNING(1, "Warning"), ERROR(2, "Error");

        private final int value;

        private final String description;

        private Type(int value, String description) {
            this.value = value;
            this.description = description;
        }

        public int value() {
            return value;
        }

        public String description() {
            return description;
        }

        @Override
        public String toString() {
            return Integer.toString(value);
        }

    }

    private Type type;

    private String status;

    private String description;

    private String info;

    private boolean success;

    private String message;

    private boolean validationError;

    public Message() {
        super();

        this.id = 0;
        this.type = null;
        this.status = "";
        this.description = "";
        this.info = "";

        this.success = true;
        this.message = "";
        this.validationError = false;
    }

    public Message(int id, Type type, String status, String description, String info, boolean success, String message, boolean validationError) {
        super();

        this.id = id;
        this.type = type;
        this.status = status;
        this.description = description;
        this.info = info;

        this.success = success;
        this.message = message;
        this.validationError = validationError;
    }

    public Integer getId() {
        return id;
    }

    public Type getType() {
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

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public boolean isValidationError() {
        return validationError;
    }

}
