package br.ufjf.dcc196.trb3.selfmanagement.models;

/**
 * Created by arthurlorenzi on 03/12/17.
 */

public class Tag {

    private long id;
    private String name;

    public Tag(long id) {
        this.id = id;
    }

    public Tag(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
