
package com.mycompany.pets.model.classes.services;

import com.mycompany.pets.model.classes.enumsandinterfaces.Status;
import com.mycompany.pets.model.classes.superclasses.Service;
import com.mycompany.pets.model.classes.superclasses.Type;
import java.time.LocalDate;
import java.util.Date;

public class Procedure {
    private int idProcedure;
    private String name;
    private double price;
    private Status status;
    private Type typeProcedure;
    private Service service;

    // Constructor
    public Procedure(int idProcedure, String name, double price, Status status, Type typeProcedure, Service service) {
        this.idProcedure = idProcedure;
        this.name = name;
        this.price = price;
        this.status = status;
        this.typeProcedure = typeProcedure;
        this.service = service;
    }
    public Procedure(){
    }

    // Getters and Setters
    public int getIdProcedure() {
        return idProcedure;
    }

    public void setIdProcedure(int idProcedure) {
        this.idProcedure = idProcedure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Type getTypeProcedure() {
        return typeProcedure;
    }

    public void setTypeProcedure(Type typeProcedure) {
        this.typeProcedure = typeProcedure;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    // To String
    @Override
    public String toString() {
        return "Procedure{" +
                "idProcedure=" + idProcedure +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", status=" + status +
                ", typeProcedure=" + typeProcedure +
                ", service=" + service +
                '}';
    }

}

