/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pets.controller.people;

import com.mycompany.pets.model.classes.enumsandinterfaces.Createable;
import com.mycompany.pets.model.classes.superclasses.Person;
import com.mycompany.pets.model.persistence.CRUD;
import com.mycompany.pets.model.persistence.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public abstract class ControllerPerson implements Createable {

    public static boolean add(Person person) {
        CRUD.setConnection(DBConnection.connectionDB());
        String insertion = """
                   INSERT INTO Contacts (ID, name, phoneNumber, email) 
                   VALUES (?, ?, ?, ?)""";
        List<Object> parameters = new ArrayList<>();
        parameters.add(person.getID());
        parameters.add(person.getName());
        parameters.add(person.getPhoneNumber());
        parameters.add(person.getEmail());

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

    public static List<Person> list() {
        CRUD.setConnection(DBConnection.connectionDB());
        List<Person> listPersons = new ArrayList<>();
        String sql = "SELECT * FROM Contacts"; // Adjust to the correct table for Person if different
        List<Object> parameters = new ArrayList<>();

        try {
            ResultSet rs = CRUD.consultDB(sql, parameters);
            while (rs != null && rs.next()) {
                Person person = new Person(
                        rs.getInt("IDContact"), // Assuming IDContact is the primary key for Person
                        rs.getString("ID"),
                        rs.getString("name"),
                        rs.getString("phoneNumber"),
                        rs.getString("email")
                );
                listPersons.add(person);
            }
        } catch (SQLException ex) {
            System.out.println("Error listing Persons: " + ex.getMessage());
        } finally {
            CRUD.closeCon();
        }

        return listPersons;
    }

    public static Person search(int id) {
        CRUD.setConnection(DBConnection.connectionDB());
        String query = """
               SELECT 
                   IDContact, ID, name, phoneNumber, email
               FROM 
                   Contacts
               WHERE 
                   IDContact = ?"""; // Assuming IDContact is the primary key for Person
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);

        try {
            ResultSet rs = CRUD.consultDB(query, parameters);
            if (rs != null && rs.next()) {
                return new Person(
                        rs.getInt("IDContact"), // Assuming IDContact is the primary key for Person
                        rs.getString("ID"),
                        rs.getString("name"),
                        rs.getString("phoneNumber"),
                        rs.getString("email")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error while searching for Person: " + e.getMessage());
        } finally {
            CRUD.closeCon();
        }
        return null;
    }

    public static boolean update(Person person) {
        CRUD.setConnection(DBConnection.connectionDB());
        String updateQuery = """
            UPDATE Contacts
            SET ID = ?, name = ?, phoneNumber = ?, email = ?
            WHERE IDContact = ?""";
        List<Object> parameters = new ArrayList<>();
        parameters.add(person.getID());
        parameters.add(person.getName());
        parameters.add(person.getPhoneNumber());
        parameters.add(person.getEmail());
        parameters.add(person.getId());

        try {
            if (CRUD.setAutoCommitDB(false)) {
                boolean success = CRUD.updateDB(updateQuery, parameters);
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

}
