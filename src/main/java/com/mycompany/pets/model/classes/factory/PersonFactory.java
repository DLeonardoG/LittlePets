package com.mycompany.pets.model.classes.factory;

import com.mycompany.pets.controller.ControllerType;
import com.mycompany.pets.controller.people.ControllerEmployee;
import com.mycompany.pets.controller.people.ControllerManufacturer;
import com.mycompany.pets.controller.people.ControllerOwner;
import com.mycompany.pets.controller.people.ControllerPerson;
import com.mycompany.pets.model.classes.people.Employee;
import com.mycompany.pets.model.classes.people.Manufacturer;
import com.mycompany.pets.model.classes.people.Owner;
import com.mycompany.pets.model.classes.superclasses.Person;
import com.mycompany.pets.model.classes.superclasses.Type;
import com.mycompany.pets.model.classes.utilities.Utility;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PersonFactory {

    public static Person registerPerson(Scanner scanner) {
        String name = Utility.getValidInput(scanner, "Enter person's name:");
        String phoneNumber = Utility.getValidInput(scanner, "Enter person's phone number:");
        String email = Utility.getValidInput(scanner, "Enter person's email:");
        int id = Utility.getMaxValue(ControllerPerson.list(), Person::getId) + 1;  // Adjust to the appropriate controller method
        String ID = Utility.getValidInput(scanner, "Enter person's ID:");
        Person person = new Person(id, ID, name, phoneNumber, email);
        return person;
    }

    public static Owner registerOwner(Scanner scanner) {
        String name = Utility.getValidInput(scanner, "Enter owner's name:");
        String address = Utility.getValidInput(scanner, "Enter owner's address:");
        String signature = Utility.getValidInput(scanner, "Enter owner's signature:");
        boolean havePoints = Utility.askYesNo(scanner, "Have points? ");
        int points = (havePoints) ? Utility.getValidInt(scanner, "How many?") : 0;
        boolean subscription = Utility.askYesNo(scanner, "Is the owner subscribed?:");
        String CUFE = Utility.getValidInput(scanner, "Enter CUFE:");
        String phoneNumber = Utility.getValidInput(scanner, "Enter owner's phone number:");
        String email = Utility.getValidInput(scanner, "Enter owner's email:");
        int id = Utility.getMaxValue(ControllerOwner.list(), Owner::getId) + 1;
        String ID = Utility.getValidInput(scanner, "Enter Owner's ID");
        System.out.println("--- Register Owner's Emergency Contact ---");
        Person person = registerPerson(scanner);
        ControllerPerson.add(person);
        Owner owner = new Owner(address, signature, points, subscription, CUFE, id, ID, name, phoneNumber, email,person);
        return owner;
    }

    public static Employee registerEmployee(Scanner scanner) {
        String name = Utility.getValidInput(scanner, "Enter employee's name:");
        String phoneNumber = Utility.getValidInput(scanner, "Enter employee's phone number:");
        String email = Utility.getValidInput(scanner, "Enter employee's email:");
        int id = Utility.getMaxValue(ControllerEmployee.list(), Employee::getId) + 1;
        Type type = ControllerType.assign(scanner, "Employee");
        String ID = Utility.getValidInput(scanner, "Enter employee's ID:");
        Employee employee = new Employee(id, ID, name, phoneNumber, email, type);
        return employee;
    }

    public static Manufacturer registerManufacturer(Scanner scanner) {
        String name = Utility.getValidInput(scanner, "Enter manufacturer's name:");
        String phoneNumber = Utility.getValidInput(scanner, "Enter manufacturer's phone number:");
        int id = Utility.getMaxValue(ControllerManufacturer.list(), Manufacturer::getIdManufacturer) + 1;
        Manufacturer manufacturer = new Manufacturer(id, name, phoneNumber);
        return manufacturer;
    }

}
