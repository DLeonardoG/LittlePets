/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pets.model.classes.supplies;

import com.mycompany.pets.model.classes.superclasses.Supply;
import com.mycompany.pets.model.classes.superclasses.Type;
import java.time.LocalDate;
import java.util.Date;

public class Medicine extends Supply {

    private Type typeMedicine;
    private LocalDate expirationDate;
    private int idMedicine;

    public Medicine(Supply supply, Type typeMedicine, LocalDate expirationDate, int idMedicine) {
        super(supply.getIdSupply(), supply.getName(), supply.getStock(), supply.getTypeSupply(), supply.getPrice());
        this.typeMedicine = typeMedicine;
        this.expirationDate = expirationDate;
        this.idMedicine = idMedicine;
    }

    public Type getTypeMedicine() {
        return typeMedicine;
    }

    public void setTypeMedicine(Type typeMedicine) {
        this.typeMedicine = typeMedicine;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getIdMedicine() {
        return idMedicine;
    }

    public void setIdMedicine(int idMedicine) {
        this.idMedicine = idMedicine;
    }

    @Override
    public String toString() {
        return "Medicine{" + "typeMedicine=" + typeMedicine + ", expirationDate=" + expirationDate + ", idMedicine=" + idMedicine + '}';
    }

}
