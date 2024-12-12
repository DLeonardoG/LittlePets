/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pets.model.classes.animals;


public class Identifier {

    private int id;
    private String number;
    private int idTypeIdentifier;
    private int idPet;

    // Constructor
    public Identifier(String number, int idTypeIdentifier, int idPet) {
        this.number = number;
        this.idTypeIdentifier = idTypeIdentifier;
        this.idPet = idPet;
    }

    public Identifier() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getIdTypeIdentifier() {
        return idTypeIdentifier;
    }

    public void setIdTypeIdentifier(int idTypeIdentifier) {
        this.idTypeIdentifier = idTypeIdentifier;
    }

    public int getIdPet() {
        return idPet;
    }

    public void setIdPet(int idPet) {
        this.idPet = idPet;
    }

}
