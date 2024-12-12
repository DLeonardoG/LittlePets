
package com.mycompany.pets.model.classes.superclasses;

public class Supply {
    private int idSupply;
    private String name;
    private int stock;
    private Type typeSupply;
    private double price;

    public Supply(int idSupply, String name, int stock, Type typeSupply, double price) {
        this.idSupply = idSupply;
        this.name = name;
        this.stock = stock;
        this.typeSupply = typeSupply;
        this.price = price;
    }

    public int getIdSupply() {
        return idSupply;
    }

    public void setIdSupply(int idSupply) {
        this.idSupply = idSupply;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Type getTypeSupply() {
        return typeSupply;
    }

    public void setTypeSupply(Type typeSupply) {
        this.typeSupply = typeSupply;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

@Override
public String toString() {
    return "    idSupply: " + idSupply + ",\n" +
           "    name: " + name + ",\n" +
           "    stock: " + stock + ",\n" +
           "    typeSupply: " + typeSupply + ",\n" +
           "    price: $" + price + "\n";
}

    
    
    
}
