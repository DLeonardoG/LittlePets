/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pets.controller.services;

import com.mycompany.pets.controller.ControllerType;
import com.mycompany.pets.controller.animal.ControllerAnimal;
import com.mycompany.pets.controller.people.ControllerEmployee;
import com.mycompany.pets.model.classes.services.Grooming;
import com.mycompany.pets.model.classes.animals.Pet;
import com.mycompany.pets.model.classes.TypePrice;
import com.mycompany.pets.model.classes.enumsandinterfaces.Createable;
import com.mycompany.pets.model.classes.enumsandinterfaces.Updateable;
import com.mycompany.pets.model.classes.superclasses.Animal;
import com.mycompany.pets.model.classes.superclasses.Service;
import com.mycompany.pets.model.classes.superclasses.Type;
import com.mycompany.pets.model.classes.utilities.Utility;
import com.mycompany.pets.model.classes.utilities.UtilityTime;
import com.mycompany.pets.model.persistence.CRUD;
import com.mycompany.pets.model.persistence.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author DELL
 */
public abstract class ControllerGrooming implements Readable, Createable, Updateable {
    public static boolean add(Grooming grooming) {
    CRUD.setConnection(DBConnection.connectionDB());

    String insertion = """
        INSERT INTO GroomingServices (IDGroomingServices, IDTypeGrooming, IDService, comments)
        VALUES (?, ?, ?, ?)""";
    List<Object> parameters = new ArrayList<>();
    parameters.add(grooming.getIdGrooming());
    parameters.add(grooming.getTypeGrooming().getId());
    parameters.add(grooming.getIdService());
    parameters.add(grooming.getComments());

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
    
public static List<Grooming> list() {
    CRUD.setConnection(DBConnection.connectionDB());
    List<Grooming> listGroomings = new ArrayList<>();
    String sql = """
        SELECT 
            g.IDGroomingServices, 
            g.IDService, 
            g.IDTypeGrooming, 
            g.comments, 
            t.name AS typeName, 
            t.price AS typePrice, 
            s.dateService, 
            s.IDEmployee, 
            s.IDPet, 
            s.IDTypeService
        FROM GroomingServices g
        JOIN TypeGrooming t ON g.IDTypeGrooming = t.IDTypeGrooming
        JOIN Services s ON g.IDService = s.IDService
    """;

    try {
        ResultSet rs = CRUD.consultDB(sql, new ArrayList<>());

        while (rs != null && rs.next()) {
            // Crear servicio base
            Animal h = ControllerAnimal.search(rs.getInt("IDPet"));
            Service baseService = new Service();
                    baseService.setIdService(rs.getInt("IDService"));
                    baseService.setDate(UtilityTime.changeSqlDate(rs.getString("dateService")));
                    baseService.setEmployeee(ControllerEmployee.search(rs.getInt("IDEmployee")));
                    baseService.setPet(h);
                    baseService.setTypeService(ControllerType.search(rs.getInt("IDTypeService"), "Service"));

            // Crear tipo de grooming
            TypePrice type = new TypePrice();
            type.setId(rs.getInt("IDTypeGrooming"));
            type.setType(rs.getString("typeName"));
            type.setPrice(rs.getDouble("typePrice"));

            // Crear grooming usando el Builder
            Grooming grooming = new Grooming(baseService);
                    grooming.setIdGrooming(rs.getInt("IDGroomingServices"));
                    grooming.setTypeGrooming(type);
                    grooming.setComments(rs.getString("comments"));
            listGroomings.add(grooming);
        }
    } catch (SQLException ex) {
        System.out.println("Error listing Groomings: " + ex.getMessage());
    } finally {
        CRUD.closeCon();
    }

    return listGroomings;
}

public static Grooming search(int id) {
    CRUD.setConnection(DBConnection.connectionDB());
    String query = """
        SELECT 
            g.IDGroomingServices, 
            g.IDService, 
            g.IDTypeGrooming, 
            g.comments, 
            t.name AS typeName, 
            t.price AS typePrice, 
            s.dateService, 
            s.IDEmployee, 
            s.IDPet, 
            s.IDTypeService
        FROM GroomingServices g
        JOIN TypeGrooming t ON g.IDTypeGrooming = t.IDTypeGrooming
        JOIN Services s ON g.IDService = s.IDService
        WHERE g.IDGroomingServices = ?
    """;
    
    List<Object> parameters = new ArrayList<>();
    parameters.add(id);

    try {
        ResultSet rs = CRUD.consultDB(query, parameters);
        if (rs != null && rs.next()) {
            // Crear servicio base
            Animal h = ControllerAnimal.search(rs.getInt("IDPet"));
            Service baseService = new Service();
            baseService.setIdService(rs.getInt("IDService"));
            baseService.setDate(UtilityTime.changeSqlDate(rs.getString("dateService")));
            baseService.setEmployeee(ControllerEmployee.search(rs.getInt("IDEmployee")));
            baseService.setPet(h);
            baseService.setTypeService(ControllerType.search(rs.getInt("IDTypeService"), "Service"));
            TypePrice type = new TypePrice();
            type.setId(rs.getInt("IDTypeGrooming"));
            type.setType(rs.getString("typeName"));
            type.setPrice(rs.getDouble("typePrice"));
            Grooming grooming = new Grooming(baseService);
            grooming.setIdGrooming(rs.getInt("IDGroomingServices"));
            grooming.setTypeGrooming(type);
            grooming.setComments(rs.getString("comments"));

            return grooming;
        }
    } catch (SQLException ex) {
        System.out.println("Error searching for Grooming: " + ex.getMessage());
    } finally {
        CRUD.closeCon();
    }
    return null;
}

public static Grooming assign(Scanner scanner) {
    Grooming grooming = null;
    while (grooming == null) {
        try {
            System.out.println("\n--- Grooming List ---");
            list().forEach(System.out::println);
            System.out.println("---------------------------");
            int id = Utility.getValidInt(scanner, "Choose one by ID: ");
            grooming = search(id);
            if (grooming == null) {
                System.out.println("Invalid ID. Please enter a valid grooming ID.");
            }
        } catch (Exception e) {
            System.out.println("Error: Please enter a valid number.");
        }
    }
    return grooming;
}

public static boolean update(Grooming grooming) {
    CRUD.setConnection(DBConnection.connectionDB());

    String updateQuery = """
        UPDATE GroomingServices
        SET IDTypeGrooming = ?, comments = ?
        WHERE IDGroomingServices = ?
    """;
    List<Object> parameters = new ArrayList<>();
    parameters.add(grooming.getTypeGrooming().getId());
    parameters.add(grooming.getComments());
    parameters.add(grooming.getIdGrooming());

    try {
        if (CRUD.setAutoCommitDB(false)) {
            boolean success = CRUD.updateDB(updateQuery, parameters);
            if (success) {
                CRUD.commitDB();
                System.out.println("Grooming updated successfully.");
                return true;
            } else {
                CRUD.rollbackDB();
                System.out.println("No grooming found to update.");
                return false;
            }
        } else {
            System.out.println("Failed to disable auto-commit.");
            return false;
        }
    } catch (Exception e) {
        CRUD.rollbackDB();
        System.out.println("Error updating Grooming: " + e.getMessage());
        return false;
    } finally {
        CRUD.closeCon();
    }
}




}
