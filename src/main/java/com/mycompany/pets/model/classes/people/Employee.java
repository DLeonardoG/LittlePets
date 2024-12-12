package com.mycompany.pets.model.classes.people;

import com.mycompany.pets.controller.people.ControllerEmployee;
import com.mycompany.pets.model.classes.superclasses.Person;
import com.mycompany.pets.model.classes.superclasses.Type;
import java.util.Scanner;

public class Employee extends Person {

    private Type type;

    public Employee(int id, String ID, String name, String phoneNumber, String email, Type type) {
        super(id, ID, name, phoneNumber, email);
        this.type = type;
    }

    public Employee(String name, String phoneNumber, String email, Type type) {
        super(name, phoneNumber, email);
        this.type = type;
    }

    public Employee() {
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDetails() {
        return "Employee {"
                + "\n  ID: " + getId()
                + "\n  Employee ID: '" + getID() + '\''
                + "\n  Name: '" + getName() + '\''
                + "\n  Phone Number: '" + getPhoneNumber() + '\''
                + "\n  Email: '" + getEmail() + '\''
                + "\n  Type: " + type
                + "\n}";
    }

    @Override
    public String toString() {
        return super.getId() + ". " + super.getName() + " - " + type.getType();
    }

}
