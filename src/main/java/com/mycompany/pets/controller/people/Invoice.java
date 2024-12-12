/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pets.controller.people;

import com.mycompany.pets.model.classes.people.Owner;
import com.mycompany.pets.model.classes.superclasses.Type;
import com.mycompany.pets.model.classes.utilities.UtilityTime;

public class Invoice {
     private int id;
    private String date;
    private Owner owner;
    private double total; 
    private Type typeService; 

    public Invoice(int id, Owner owner, double price, Type typeService) {
        this.id = id;
        this.date = UtilityTime.getCurrentDateTimeAsString();
        this.owner = owner;
        this.total = price;
        this.typeService = typeService;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Type getTypeService() {
        return typeService;
    }

    public void setTypeService(Type typeService) {
        this.typeService = typeService;
    }

@Override
public String toString() {
    return "---------------------\n" +
           "Invoice ID: " + id + "\n" +
           "Date: " + date + "\n" +
           "Owner: " + owner + "\n" +
           "Total Amount: $" + total + "\n" +
           "Service Type: " + typeService + "\n" +
           "---------------------";
}

}