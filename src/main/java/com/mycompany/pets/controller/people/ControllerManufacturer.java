package com.mycompany.pets.controller.people;

import com.mycompany.pets.model.classes.enumsandinterfaces.Createable;
import com.mycompany.pets.model.classes.enumsandinterfaces.Updateable;
import com.mycompany.pets.model.classes.people.Manufacturer;
import com.mycompany.pets.model.classes.utilities.Utility;
import com.mycompany.pets.model.persistence.CRUD;
import com.mycompany.pets.model.persistence.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class ControllerManufacturer implements Readable, Createable, Updateable {

    public static boolean add(Manufacturer manufacturer) {
        CRUD.setConnection(DBConnection.connectionDB());
        String insertion = """
                       INSERT INTO Manufacturers (name, phoneNumber) 
                       VALUES (?, ?)""";
        List<Object> parameters = new ArrayList<>();
        parameters.add(manufacturer.getName());
        parameters.add(manufacturer.getPhoneNumber());

        try {
            if (CRUD.setAutoCommitDB(false)) {
                boolean success = CRUD.insertDB(insertion, parameters);
                if (success) {
                    CRUD.commitDB();
                    return true;
                } else {
                    CRUD.rollbackDB();
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            CRUD.rollbackDB();
            throw e;
        } finally {
            CRUD.closeCon();
        }
    }

    public static List<Manufacturer> list() {
        CRUD.setConnection(DBConnection.connectionDB());
        List<Manufacturer> listManufacturers = new ArrayList<>();
        String sql = "SELECT * FROM Manufacturers";
        List<Object> parameters = new ArrayList<>();

        try {
            ResultSet rs = CRUD.consultDB(sql, parameters);
            while (rs != null && rs.next()) {
                Manufacturer manufacturer = new Manufacturer(
                        rs.getInt("IDManufacturer"),
                        rs.getString("name"),
                        rs.getString("phoneNumber")
                );
                listManufacturers.add(manufacturer);
            }
        } catch (SQLException ex) {
            System.out.println("Error listing Manufacturers: " + ex.getMessage());
        } finally {
            CRUD.closeCon();
        }

        return listManufacturers;
    }

    public static Manufacturer search(int id) {
        CRUD.setConnection(DBConnection.connectionDB());
        String query = """
                   SELECT 
                       IDManufacturer, name, phoneNumber
                   FROM 
                       Manufacturers
                   WHERE 
                       IDManufacturer = ?""";
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);

        try {
            ResultSet rs = CRUD.consultDB(query, parameters);
            if (rs != null && rs.next()) {
                return new Manufacturer(
                        rs.getInt("IDManufacturer"),
                        rs.getString("name"),
                        rs.getString("phoneNumber")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error while searching for Manufacturer: " + e.getMessage());
        } finally {
            CRUD.closeCon();
        }
        return null;
    }

    public static Manufacturer assign(Scanner scanner) {
        Manufacturer manufacturer = null;
        while (manufacturer == null) {
            try {
                System.out.println("\n--- Manufacturers List ---");
                ControllerManufacturer.list().forEach(System.out::println);
                System.out.println("--------------------------");
                int id = Utility.getValidInt(scanner, "Choose one by ID: ");
                manufacturer = ControllerManufacturer.search(id);
                if (manufacturer == null) {
                    System.out.println("Not valid, enter a valid value.");
                }
            } catch (Exception e) {
                System.out.println("Error: enter correct format.");
                scanner.nextLine(); // Limpiar el buffer
            }
        }
        return manufacturer;
    }

    public static boolean update(Manufacturer manufacturer) {
        CRUD.setConnection(DBConnection.connectionDB());
        String sql = "UPDATE Manufacturers SET name = ?, phoneNumber = ? WHERE IDManufacturer = ?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(manufacturer.getName());
        parameters.add(manufacturer.getPhoneNumber());
        parameters.add(manufacturer.getIdManufacturer());

        try {
            if (CRUD.setAutoCommitDB(false)) {
                boolean success = CRUD.updateDB(sql, parameters); // updateDB para ejecutar actualizaciones
                if (success) {
                    CRUD.commitDB();
                    System.out.println("Manufacturer updated successfully.");
                    return true;
                } else {
                    CRUD.rollbackDB();
                    System.out.println("No manufacturer found to update.");
                    return false;
                }
            } else {
                System.out.println("Failed to disable auto-commit.");
                return false;
            }
        } catch (Exception e) {
            CRUD.rollbackDB();
            System.out.println("Error updating manufacturer: " + e.getMessage());
            return false;
        } finally {
            CRUD.closeCon();
        }
    }

}
