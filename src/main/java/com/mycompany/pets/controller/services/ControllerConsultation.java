/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pets.controller.services;

import com.mycompany.pets.controller.ControllerType;
import com.mycompany.pets.controller.people.ControllerEmployee;
import com.mycompany.pets.model.classes.services.Consultation;
import com.mycompany.pets.model.classes.animals.Pet;
import com.mycompany.pets.model.classes.enumsandinterfaces.Status;
import com.mycompany.pets.model.classes.superclasses.Service;
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
public abstract class ControllerConsultation {

  public static boolean add(Consultation c1) {
    CRUD.setConnection(DBConnection.connectionDB());
    String insertion = """
                   INSERT INTO Prescriptions (IDConsultation, recomendations, services, supplies, diagnostic) 
                   VALUES (?, ?, ?, ?, ?)
                   """;
    List<Object> parameters = new ArrayList<>();
    parameters.add(c1.getIdConsultation());
    parameters.add(c1.getRecommendations());
    parameters.add(c1.getServices());
    parameters.add(c1.getSupplies());
    parameters.add(c1.getDiagnostic());

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

public static List<Consultation> list() {
    CRUD.setConnection(DBConnection.connectionDB());
    List<Consultation> listConsultations = new ArrayList<>();
    String sql = """
            SELECT 
                Consultations.IDConsultation,
                Consultations.IDTypeReason,
                Consultations.isControl,
                Consultations.price,
                Consultations.IDService,
                Services.dateService,
                Services.IDEmployee,
                Services.IDPet,
                Services.IDTypeService,
                Services.status AS serviceStatus, -- Alias para evitar ambigüedad
                Services.paid,
                Prescriptions.recomendations,
                Prescriptions.diagnostic,
                Prescriptions.services,
                Prescriptions.supplies
            FROM 
                Consultations
            JOIN 
                Services ON Consultations.IDService = Services.IDService
            LEFT JOIN 
                Prescriptions ON Consultations.IDConsultation = Prescriptions.IDConsultation;
            """;

    List<Object> parameters = new ArrayList<>();
    try {
        ResultSet rs = CRUD.consultDB(sql, parameters);
        while (rs != null && rs.next()) {
            Service service = new Service(
                    rs.getInt("IDService"),
                    UtilityTime.changeSqlDate(rs.getString("dateService")),
                    new Pet(rs.getInt("IDPet")),
                    ControllerEmployee.search(rs.getInt("IDEmployee")),
                    ControllerType.search(rs.getInt("IDTypeService"), "Service"),
                    Status.valueOf(rs.getString("serviceStatus")), // Usar alias
                    rs.getBoolean("paid"));
            Consultation consultation = new Consultation.Builder(service)
                    .setIdConsultation(rs.getInt("IDConsultation"))
                    .setReason(ControllerType.search(rs.getInt("IDTypeReason"), "Reason"))
                    .setIsControl(rs.getBoolean("isControl"))
                    .setPrice(rs.getDouble("price"))
                    .setRecommendations(rs.getString("recomendations"))
                    .setDiagnostic(rs.getString("diagnostic"))
                    .setServices(rs.getString("services"))
                    .setSupplies(rs.getString("supplies"))
                    .build();
            listConsultations.add(consultation);
        }
    } catch (SQLException ex) {
        System.out.println("Error listing Consultations: " + ex.getMessage());
    } finally {
        CRUD.closeCon();
    }

    return listConsultations;
}

public static Consultation search(int id) {
    CRUD.setConnection(DBConnection.connectionDB());
    String query = """
               SELECT 
                   Consultations.IDConsultation,
                   Consultations.IDTypeReason,
                   Consultations.isControl,
                   Consultations.price,
                   Consultations.IDService,
                   Services.dateService,
                   Services.paid,
                   Services.status AS serviceStatus, -- Alias para evitar ambigüedad
                   Services.IDEmployee,
                   Services.IDPet,
                   Services.IDTypeService,
                   Prescriptions.recomendations,
                   Prescriptions.diagnostic,
                   Prescriptions.services,
                   Prescriptions.supplies
               FROM 
                   Consultations
               JOIN 
                   Services ON Consultations.IDService = Services.IDService
               LEFT JOIN 
                   Prescriptions ON Consultations.IDConsultation = Prescriptions.IDConsultation
               WHERE 
                   Consultations.IDConsultation = ?""";
    List<Object> parameters = new ArrayList<>();
    parameters.add(id);

    try {
        ResultSet rs = CRUD.consultDB(query, parameters);
        if (rs != null && rs.next()) {
            Service baseService = new Service();
            baseService.setIdService(rs.getInt("IDService"));
            baseService.setDate(UtilityTime.changeSqlDate(rs.getString("dateService")));
            baseService.setEmployeee(ControllerEmployee.search(rs.getInt("IDEmployee")));
            baseService.setPet(new Pet(rs.getInt("IDPet")));
            baseService.setTypeService(ControllerType.search(rs.getInt("IDTypeService"), "Service"));

            Consultation consultation = new Consultation.Builder(baseService)
                    .setIdConsultation(rs.getInt("IDConsultation"))
                    .setReason(ControllerType.search(rs.getInt("IDTypeReason"), "Reason"))
                    .setIsControl(rs.getBoolean("isControl"))
                    .setStatus(Status.valueOf(rs.getString("serviceStatus"))) // Usar alias
                    .setPrice(rs.getDouble("price"))
                    .setRecommendations(rs.getString("recomendations"))
                    .setDiagnostic(rs.getString("diagnostic"))
                    .setServices(rs.getString("services"))
                    .setSupplies(rs.getString("supplies"))
                    .build();

            return consultation;
        }
    } catch (SQLException e) {
        System.out.println("Error while searching for Consultation: " + e.getMessage());
    } finally {
        CRUD.closeCon();
    }
    return null;
}

public static Consultation assign(Scanner scanner) {
    Consultation consultation = null;
    while (consultation == null) {
        try {
            System.out.println("\n--- Consultations List ---");
            ControllerConsultation.list().forEach(System.out::println);
            System.out.println("--------------------------");
            int id = Utility.getValidInt(scanner, "Choose one by ID: ");
            consultation = ControllerConsultation.search(id);
            if (consultation == null) {
                System.out.println("Not valid, enter a valid value.");
            }
        } catch (Exception e) {
            System.out.println("Error: enter correct format.");
            scanner.nextLine();
        }
    }
    return consultation;
}

public static boolean update(Consultation c1) {
    CRUD.setConnection(DBConnection.connectionDB());
    String updateQuery = """
        UPDATE Consultations
        SET IDTypeReason = ?, isControl = ?, price = ?, IDService = ?
        WHERE IDConsultation = ?
        """;
    List<Object> parameters = new ArrayList<>();
    parameters.add(c1.getReason().getId());
    parameters.add(c1.getIsControl());
    parameters.add(c1.getPrice());
    parameters.add(c1.getIdService());
    parameters.add(c1.getIdConsultation());

    String updatePrescriptionQuery = """
        UPDATE Prescriptions
        SET recomendations = ?, services = ?, supplies = ?, diagnostic = ?
        WHERE IDConsultation = ?
        """;
    List<Object> prescriptionParameters = new ArrayList<>();
    prescriptionParameters.add(c1.getRecommendations());
    prescriptionParameters.add(c1.getServices());
    prescriptionParameters.add(c1.getSupplies());
    prescriptionParameters.add(c1.getDiagnostic());
    prescriptionParameters.add(c1.getIdConsultation());

    try {
        if (CRUD.setAutoCommitDB(false)) {
            boolean consultationUpdated = CRUD.updateDB(updateQuery, parameters);
            boolean prescriptionUpdated = CRUD.updateDB(updatePrescriptionQuery, prescriptionParameters);

            if (consultationUpdated && prescriptionUpdated) {
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
