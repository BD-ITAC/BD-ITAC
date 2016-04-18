package br.ita.bditac.model;

import java.io.Serializable;

import org.springframework.hateoas.Identifiable;

public class Message implements Serializable, Identifiable<Integer> {

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

    public Message(int id, Type type, String status, String description, String info) {
        super();

        this.id = id;
        this.type = type;
        this.status = status;
        this.description = description;
        this.info = info;
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

    public void setInfo(String info) {
        this.info = info;
    }

}
