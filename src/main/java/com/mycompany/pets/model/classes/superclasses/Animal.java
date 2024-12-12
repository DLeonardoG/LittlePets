
package com.mycompany.pets.model.classes.superclasses;

import com.mycompany.pets.model.classes.animals.Characteristic;
import com.mycompany.pets.model.classes.animals.Identifier;
import com.mycompany.pets.model.classes.people.Owner;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public abstract class Animal {
    
    private int id;
    private String name;
    private String birthDate;
    private String sex;
    private double weight;
    private String conditions;
    private String allergies;
    private String isAvailable;
    private String urlPhoto;
    private Owner owner;
    private List<Characteristic> characteristics;
    private Identifier identifier;
    private int IDSpecie;

    public Animal(int id, String name, String birthDate, String sex, double weight, String conditions, String allergies, String isAvailable, Owner owner, Identifier identifier) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.sex = sex;
        this.weight = weight;
        this.conditions = conditions;
        this.isAvailable = isAvailable;
        this.owner = owner;
        this.identifier = identifier;
        this.characteristics = new ArrayList<>();

    }

    public abstract void initializeCharacteristics();

    public void addCharacteristic(Characteristic characteristic) {
        characteristics.add(characteristic);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<Characteristic> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(List<Characteristic> characteristics) {
        this.characteristics = characteristics;
    }

    public int getIDSpecie() {
        return IDSpecie;
    }

    public void setIDSpecie(int IDSpecie) {
        this.IDSpecie = IDSpecie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }
    
}
