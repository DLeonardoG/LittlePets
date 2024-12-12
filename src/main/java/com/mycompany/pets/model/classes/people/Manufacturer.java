
package com.mycompany.pets.model.classes.people;

import com.mycompany.pets.model.classes.superclasses.Person;

public class Manufacturer {
    private int idManufacturer;
    private String name;
    private String phoneNumber;

    public Manufacturer(int idManufacturer, String name, String phoneNumber) {
        this.idManufacturer = idManufacturer;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
    public Manufacturer( String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public int getIdManufacturer() {
        return idManufacturer;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setIdManufacturer(int idManufacturer) {
        this.idManufacturer = idManufacturer;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Manufacturer{idManufacturer=" + idManufacturer + 
               ", name='" + name + '\'' + 
               ", phoneNumber='" + phoneNumber + '\'' + '}';
    }
}
