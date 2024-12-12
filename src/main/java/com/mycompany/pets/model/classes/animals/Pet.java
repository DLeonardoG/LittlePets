package com.mycompany.pets.model.classes.animals;

public class Pet {
    private int id;
    private String name;
    private int age;
    private String status;
    private double salePrice;

    // Constructor para mascotas disponibles para adopción
    public Pet(int id, String name, int age, String status) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.status = status;
        this.salePrice = 0.0; // default value, no sale price for adoption pets
    }

    // Constructor para mascotas en venta
    public Pet(int id, String name, int age, String status, double salePrice) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.status = status;
        this.salePrice = salePrice;
    }

    // Getters y Setters
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    // Método para mostrar la información del animal
    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", status='" + status + '\'' +
                ", salePrice=" + salePrice +
                '}';
    }
}
