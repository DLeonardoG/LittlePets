/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pets.model.classes.superclasses;

import com.mycompany.pets.controller.ControllerType;
import java.util.Scanner;

public class Type {

    private int id;
    private String type;

    public Type(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public Type() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return id + ". " + type;
    }
}
