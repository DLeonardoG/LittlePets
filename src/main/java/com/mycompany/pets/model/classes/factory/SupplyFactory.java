package com.mycompany.pets.model.classes.factory;

import com.mycompany.pets.controller.ControllerType;
import com.mycompany.pets.controller.supplies.ControllerMedicine;
import com.mycompany.pets.controller.supplies.ControllerSupply;
import com.mycompany.pets.controller.supplies.ControllerVaccine;
import com.mycompany.pets.model.classes.superclasses.Supply;
import com.mycompany.pets.model.classes.superclasses.Type;
import com.mycompany.pets.model.classes.supplies.Medicine;
import com.mycompany.pets.model.classes.supplies.Vaccine;
import com.mycompany.pets.model.classes.utilities.Utility;
import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;

public class SupplyFactory {

    public static Supply registerSupply(Scanner scanner, Type typeSupply) {
        String name = Utility.getValidInput(scanner, "Enter supply name:");
        int stock = Utility.getValidInt(scanner, "Enter supply stock:");
        double price = Utility.getValidDouble(scanner, "Enter supply price:");
        System.out.println("Select the type of supply:");
        int id = Utility.getMaxValue(ControllerSupply.list(), Supply::getIdSupply) + 1;
        Supply supply = new Supply(id, name, stock, typeSupply, price);
        return supply;
    }

    public static Medicine registerMedicine(Scanner scanner, Type typeSupply) {
        String name = Utility.getValidInput(scanner, "Enter medicine name:");
        int stock = Utility.getValidInt(scanner, "Enter medicine stock:");
        double price = Utility.getValidDouble(scanner, "Enter medicine price:");
        System.out.println("Select the type of supply:");
        int idSupply = Utility.getMaxValue(ControllerSupply.list(), Supply::getIdSupply) + 1;
        Supply supply = new Supply(idSupply, name, stock, typeSupply, price);
        System.out.println("Select the type of medicine:");
        Type typeMedicine = ControllerType.assign(scanner, "Medicine");
        LocalDate expirationDate = Utility.getValidDate(scanner, "Enter expiration date (YYYY-MM-DD):");
        int idMedicine = Utility.getMaxValue(ControllerMedicine.list(), Medicine::getIdMedicine) + 1;
        return new Medicine(supply, typeMedicine, expirationDate, idMedicine);
    }

    public static Vaccine registerVaccine(Scanner scanner, Type typeSupply) {
        String name = Utility.getValidInput(scanner, "Enter vaccine name:");
        int stock = Utility.getValidInt(scanner, "Enter vaccine stock:");
        double price = Utility.getValidDouble(scanner, "Enter vaccine price:");
        System.out.println("Select the type of supply:");
        int idSupply = Utility.getMaxValue(ControllerSupply.list(), Supply::getIdSupply) + 1;
        Supply supply = new Supply(idSupply, name, stock, typeSupply, price);
        int batch = Utility.getValidInt(scanner, "Enter batch number:");
        LocalDate expirationDate = Utility.getValidDate(scanner, "Enter expiration date (YYYY-MM-DD):");
        System.out.println("Select the type of vaccine:");
        Type type = ControllerType.assign(scanner, "Vaccine");
        int idVaccine = Utility.getMaxValue(ControllerVaccine.list(), Vaccine::getIdVaccine) + 1;
        return new Vaccine(supply, batch, expirationDate, type, idVaccine);
    }

}
