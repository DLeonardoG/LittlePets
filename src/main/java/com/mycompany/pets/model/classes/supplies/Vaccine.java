/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pets.model.classes.supplies;

import com.mycompany.pets.model.classes.superclasses.Supply;
import com.mycompany.pets.model.classes.superclasses.Type;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author DELL
 */
public class Vaccine extends Supply {

    private int batch;
    private LocalDate expirationDate;
    private Type type;
    private int idVaccine;

    public Vaccine(Supply supply, int batch, LocalDate expirationDate, Type type, int idVaccine) {
        super(supply.getIdSupply(), supply.getName(), supply.getStock(), supply.getTypeSupply(), supply.getPrice());
        this.batch = batch;
        this.expirationDate = expirationDate;
        this.type = type;
        this.idVaccine = idVaccine;
    }

    public int getBatch() {
        return batch;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getIdVaccine() {
        return idVaccine;
    }

    public void setIdVaccine(int idVaccine) {
        this.idVaccine = idVaccine;
    }

    @Override
    public String toString() {
        return "Vaccine{" + "batch=" + batch + ", expirationDate=" + expirationDate + ", type=" + type + ", idVaccine=" + idVaccine + '}';
    }

}
