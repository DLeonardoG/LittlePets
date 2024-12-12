package com.mycompany.pets.model.classes.animals;

public class Pet {

    private int id;
    private String name;

    public Pet(String name) {
        this.name = name;
        this.id = 1;
    }

    public Pet(int id) {
        this.name = "jota";
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Pet{" + "id=" + id + ", name=" + name + '}';
    }

}
