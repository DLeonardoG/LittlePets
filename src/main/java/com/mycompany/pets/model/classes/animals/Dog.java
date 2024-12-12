/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pets.model.classes.animals;

import com.mycompany.pets.model.classes.people.Owner;
import com.mycompany.pets.model.classes.superclasses.Animal;

public class Dog extends Animal {

    public Dog(int id, String name, String birthDate, String sex, double weight, String conditions, String allergies, String isAvailable, Owner owner, Identifier identifier) {
        super(id, name, birthDate, sex, weight, conditions, allergies, isAvailable, owner, identifier);
        setIDSpecie(1);
        setUrlPhoto("Dog.jpg");
    }

    @Override
    public void initializeCharacteristics() {
        addCharacteristic(new Characteristic("Breed", ""));
        addCharacteristic(new Characteristic("Color", ""));
        addCharacteristic(new Characteristic("Feeding", ""));
        addCharacteristic(new Characteristic("Size", ""));

    }

}
