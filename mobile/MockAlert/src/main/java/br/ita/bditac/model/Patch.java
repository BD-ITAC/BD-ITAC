package br.ita.bditac.model;

import java.io.Serializable;

public class Patch implements Serializable {

    private static final long serialVersionUID = 1L;

    public interface Operation {

        String REPLACE = "replace";
    }

    private String op;

    private String path;

    private String value;

    public Patch() {
        super();

        this.op = "";
        this.path = "";
        this.value = "";
    }

    public Patch(String op, String path, String value) {
        super();

        this.op = op;
        this.path = path;
        this.value = value;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
