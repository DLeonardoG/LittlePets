package com.mycompany.pets.view;

import com.mycompany.pets.controller.ControllerType;
import com.mycompany.pets.controller.people.ControllerOwner;
import com.mycompany.pets.controller.people.ControllerPerson;
import com.mycompany.pets.controller.services.ControllerAppointment;
import com.mycompany.pets.controller.services.ControllerConsultation;
import com.mycompany.pets.controller.services.ControllerGrooming;
import com.mycompany.pets.controller.services.ControllerService;
import com.mycompany.pets.controller.services.ControllerTraining;
import com.mycompany.pets.controller.supplies.ControllerSupply;
import com.mycompany.pets.controller.supplies.ControllerVaccine;
import com.mycompany.pets.model.classes.enumsandinterfaces.Facturable;
import com.mycompany.pets.model.classes.enumsandinterfaces.Status;
import com.mycompany.pets.model.classes.factory.PersonFactory;
import static com.mycompany.pets.model.classes.factory.PersonFactory.registerPerson;
import com.mycompany.pets.model.classes.factory.ServiceFactory;
import com.mycompany.pets.model.classes.factory.SupplyFactory;
import com.mycompany.pets.model.classes.people.Owner;
import com.mycompany.pets.model.classes.services.Consultation;
import com.mycompany.pets.model.classes.services.Grooming;
import com.mycompany.pets.model.classes.services.Training;
import com.mycompany.pets.model.classes.superclasses.Person;
import com.mycompany.pets.model.classes.superclasses.Service;
import com.mycompany.pets.model.classes.superclasses.Supply;
import com.mycompany.pets.model.classes.superclasses.Type;
import com.mycompany.pets.model.classes.supplies.Medicine;
import com.mycompany.pets.model.classes.supplies.Vaccine;
import com.mycompany.pets.model.classes.utilities.Utility;
import java.util.Scanner;

public class ViewMethod {

    public static void registrerOwner(Scanner scanner) {
        Owner owner = PersonFactory.registerOwner(scanner);
        ControllerOwner.add(owner);
    }

    public static void updateOwner(Scanner scanner) {
        Owner oldOwner = ControllerOwner.assign(scanner);
        System.out.println(oldOwner);
        oldOwner.setAdress(Utility.getValidInput(scanner, "Update owner's address:"));
        oldOwner.setSignature(Utility.getValidInput(scanner, "Update owner's signature:"));
        boolean havePoints = Utility.askYesNo(scanner, "Update points? ");
        int points = (havePoints) ? Utility.getValidInt(scanner, "How many more?") : 0;
        if (havePoints) {
            oldOwner.setPoints(points);
        }
        oldOwner.setSignature(Utility.getValidInput(scanner, "Update owner's signature:"));
        oldOwner.setSubscripcion(Utility.askYesNo(scanner, "Is the owner subscribed?:"));
        oldOwner.setCUFE(Utility.getValidInput(scanner, "Update CUFE:"));
        oldOwner.setPhoneNumber(Utility.getValidInput(scanner, "Update owner's phone number:"));
        oldOwner.setEmail(Utility.getValidInput(scanner, "Update owner's email:"));
        System.out.println("--- Update Owner's Emergency Contact ---");
        boolean newContact = Utility.askYesNo(scanner, "Update emergy contact?");
        if (newContact) {
            Person contact = ControllerPerson.search(oldOwner.getContact().getId());
            Person person = registerPerson(scanner);
            contact.setId(person.getId());
            ControllerPerson.update(person);
        }
        ControllerOwner.update(oldOwner);
    }

    public static void registrerSupplies(Scanner scanner) {
        Type typeSupply = ControllerType.assign(scanner, "Supplies");
        Supply supply;
        switch (typeSupply.getType()) {
            case "Medical Supply":
                supply = SupplyFactory.registerSupply(scanner, typeSupply);
                ControllerSupply.add(supply);
                System.out.println("Finally Succesfully :)");
                break;
            case "Grooming Product":
                supply = SupplyFactory.registerSupply(scanner, typeSupply);
                ControllerSupply.add(supply);
                System.out.println("Finally Succesfully :)");

                break;
            case "Vaccine":
                Vaccine vaccine = SupplyFactory.registerVaccine(scanner, typeSupply);
                ControllerSupply.add(vaccine);
                ControllerVaccine.add(vaccine);
                System.out.println("Finally Succesfully :)");

                break;
            case "Medicine":
                Medicine medicine = SupplyFactory.registerMedicine(scanner, typeSupply);
                ControllerSupply.add(medicine);
                ControllerSupply.add(medicine);

                break;
            default:
                System.out.println("Invalid");
                ;
        }
    }

    public static void viewSupplies(Scanner scanner) {
        ControllerSupply.list().forEach(System.out::println);
    }

    public static void updateStock(Scanner scanner) {
        Supply supply = ControllerSupply.assign(scanner);
        supply.setStock(Utility.getValidInt(scanner, "-- Updsate Stock --\n" + supply + "New Stock: "));
        supply.setPrice(Utility.getValidInt(scanner, "New Price: "));
        ControllerSupply.update(supply);
    }

    public static void registrerServices(Scanner scanner) {
        Type typeServicie = ControllerType.assign(scanner, "Service");
        Facturable service;
        switch (typeServicie.getType()) {
            case "Consultation":
                Consultation consultation = ServiceFactory.registerAppointment(scanner, typeServicie);
                ControllerService.add(consultation);
                ControllerAppointment.add(consultation);
                System.out.println("Finally Succesfully :)");
                break;
            case "Grooming":
                Grooming grooming = ServiceFactory.registerGrooming(scanner, typeServicie);
                ControllerService.add(grooming);
                ControllerGrooming.add(grooming);
                System.out.println("Finally Succesfully :)");
                break;
            case "Training":
                Training training = ServiceFactory.registerTraining(scanner, typeServicie);
                ControllerService.add(training);
                ControllerTraining.add(training);
                System.out.println("Finally Succesfully :)");
                break;
            default:
                System.out.println("Invalid");
        }

    }

    public static void viewVaccines(Scanner scanner) {
        ControllerVaccine.list().forEach(System.out::println);
    }

    public static void attendService(Scanner scanner) {
        Consultation ser3 = ServiceFactory.registerConsultation(scanner);
        ControllerConsultation.add(ser3);
        ControllerService.update(ser3);
    }

    public static void cancelService(Scanner scanner) {
        Service ser3 = ControllerService.assign(scanner);
        ser3.setStatus(Status.CANCELLED);
        ControllerService.update(ser3);
    }

    public static void viewCalendar(Scanner scanner) {
        ControllerAppointment.list().forEach(System.out::println);
    }
    public static void viewAppoinmentsFilter(Scanner scanner) {
        ControllerAppointment.listSpecial(Status.CANCELLED).forEach(System.out::println);
    }

}
