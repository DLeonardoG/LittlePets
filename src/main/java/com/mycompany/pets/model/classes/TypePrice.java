/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pets.model.classes;

import com.mycompany.pets.model.classes.superclasses.Type;

/**
 *
 * @author DELL
 */
public class TypePrice extends Type{
    private double price;

    public TypePrice(double price, int id, String type) {
        super(id, type);
        this.price = price;
    }
    public TypePrice() {
    }

    public TypePrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return super.toString()+
                " price: "+price;
    }
    
    
}
