/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pets.controller.people;

import com.mycompany.pets.model.classes.enumsandinterfaces.Createable;
import com.mycompany.pets.model.classes.enumsandinterfaces.Updateable;
import com.mycompany.pets.model.classes.people.Owner;
import com.mycompany.pets.model.classes.superclasses.Person;
import com.mycompany.pets.model.classes.utilities.Utility;
import com.mycompany.pets.model.persistence.CRUD;
import com.mycompany.pets.model.persistence.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class ControllerOwner implements Readable, Createable, Updateable {

    public static boolean add(Owner owner) {
        CRUD.setConnection(DBConnection.connectionDB());
        String insertion = """
                   INSERT INTO Owners (ID, name, adress, phoneNumber, email, signature, points, subscription, CUFE, IDContact) 
                   VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""";
        List<Object> parameters = new ArrayList<>();
        parameters.add(owner.getId());
        parameters.add(owner.getName());
        parameters.add(owner.getAdress());
        parameters.add(owner.getPhoneNumber());
        parameters.add(owner.getEmail());
        parameters.add(owner.getSignature());
        parameters.add(owner.getPoints());
        parameters.add(owner.isSubscripcion());
        parameters.add(owner.getCUFE());
        parameters.add(owner.getContact().getId());

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

    public static List<Owner> list() {
        CRUD.setConnection(DBConnection.connectionDB());
        List<Owner> listOwners = new ArrayList<>();
        String sql = "SELECT * FROM Owners";
        List<Object> parameters = new ArrayList<>();

        try {
            ResultSet rs = CRUD.consultDB(sql, parameters);
            while (rs != null && rs.next()) {
                Person contact = ControllerPerson.search(rs.getInt("IDContact"));
                Owner owner = new Owner(
                        rs.getString("adress"),
                        rs.getString("signature"),
                        rs.getInt("points"),
                        rs.getBoolean("subscription"),
                        rs.getString("CUFE"),
                        rs.getInt("IDOwner"),
                        rs.getString("ID"),
                        rs.getString("name"),
                        rs.getString("phoneNumber"),
                        rs.getString("email"),
                        contact
                );
                listOwners.add(owner);
            }
        } catch (SQLException ex) {
            System.out.println("Error listing Owners: " + ex.getMessage());
        } finally {
            CRUD.closeCon();
        }

        return listOwners;
    }

    public static Owner search(int id) {
        CRUD.setConnection(DBConnection.connectionDB());
        String query = """
               SELECT 
                   IDOwner, ID, name, adress, phoneNumber, email, signature, points, subscription, CUFE, IDContact
               FROM 
                   Owners
               WHERE 
                   IDOwner = ?""";
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);

        try {
            ResultSet rs = CRUD.consultDB(query, parameters);
            if (rs != null && rs.next()) {

                Person contact = ControllerPerson.search(rs.getInt("IDContact"));
                return new Owner(
                        rs.getString("adress"),
                        rs.getString("signature"),
                        rs.getInt("points"),
                        rs.getBoolean("subscription"),
                        rs.getString("CUFE"),
                        rs.getInt("IDOwner"),
                        rs.getString("ID"),
                        rs.getString("name"),
                        rs.getString("phoneNumber"),
                        rs.getString("email"),
                        contact
                );
            }
        } catch (SQLException e) {
            System.out.println("Error while searching for Owner: " + e.getMessage());
        } finally {
            CRUD.closeCon();
        }
        return null;
    }

    public static Owner assign(Scanner scanner) {
        Owner owner = null;
        while (owner == null) {
            try {
                System.out.println("\n--- Owners List ---");
                ControllerOwner.list().forEach(System.out::println);
                System.out.println("---------------------");
                int id = Utility.getValidInt(scanner, "Choose one by ID: ");
                owner = ControllerOwner.search(id);
                if (owner == null) {
                    System.out.println("Not valid, enter a valid value.");
                }
            } catch (Exception e) {
                System.out.println("Error: enter correct format.");
                scanner.nextLine();
            }
        }
        return owner;
    }

    public static boolean update(Owner owner) {
        CRUD.setConnection(DBConnection.connectionDB());
        String sql = "UPDATE Owners SET ID = ?, name = ?, adress = ?, phoneNumber = ?, email = ?, signature = ?, points = ?, subscription = ?, CUFE = ?, IDContact = ? WHERE IDOwner = ?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(owner.getId());
        parameters.add(owner.getName());
        parameters.add(owner.getAdress());
        parameters.add(owner.getPhoneNumber());
        parameters.add(owner.getEmail());
        parameters.add(owner.getSignature());
        parameters.add(owner.getPoints());
        parameters.add(owner.isSubscripcion());
        parameters.add(owner.getCUFE());
        parameters.add(owner.getContact());
        parameters.add(owner.getId());

        try {
            if (CRUD.setAutoCommitDB(false)) {
                boolean success = CRUD.updateDB(sql, parameters);
                if (success) {
                    CRUD.commitDB();
                    System.out.println("Owner updated successfully.");
                    return true;
                } else {
                    CRUD.rollbackDB();
                    System.out.println("No owner found to update.");
                    return false;
                }
            } else {
                System.out.println("Failed to disable auto-commit.");
                return false;
            }
        } catch (Exception e) {
            CRUD.rollbackDB();
            System.out.println("Error updating owner: " + e.getMessage());
            return false;
        } finally {
            CRUD.closeCon();
        }
    }

}
