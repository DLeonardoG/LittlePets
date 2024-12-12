package com.mycompany.pets.controller.services;

import com.mycompany.pets.controller.ControllerType;
import com.mycompany.pets.controller.people.ControllerEmployee;
import com.mycompany.pets.model.classes.services.Consultation;
import com.mycompany.pets.model.classes.animals.Pet;
import com.mycompany.pets.model.classes.enumsandinterfaces.Createable;
import com.mycompany.pets.model.classes.enumsandinterfaces.Status;
import com.mycompany.pets.model.classes.enumsandinterfaces.Updateable;
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

public abstract class ControllerAppointment implements Readable, Createable, Updateable {

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
                       Services.IDEmployee,
                       Services.IDPet,
                       Services.IDTypeService,
                       Services.status,
                       Services.paid
                   FROM 
                       Consultations
                   JOIN 
                       Services
                   ON 
                       Consultations.IDService = Services.IDService
                   WHERE 
                       Consultations.IDConsultation = ?""";
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);

        try {
            ResultSet rs = CRUD.consultDB(query, parameters);
            if (rs != null && rs.next()) {
                Service baseService = new Service(
                        rs.getInt("IDService"),
                        UtilityTime.changeSqlDate(rs.getString("dateService")),
                        new Pet(rs.getInt("IDPet")),
                        ControllerEmployee.search(rs.getInt("IDEmployee")),
                        ControllerType.search(rs.getInt("IDTypeService"), "Service"),
                        Status.valueOf(rs.getString("status")),
                        rs.getBoolean("paid")
                );

                Consultation consultation = new Consultation.Builder(baseService)
                        .setIdConsultation(rs.getInt("IDConsultation"))
                        .setReason(ControllerType.search(rs.getInt("IDTypeReason"), "Reason"))
                        .setIsControl(rs.getBoolean("isControl"))
                        .setPrice(rs.getDouble("price"))
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
                ControllerAppointment.list().forEach(System.out::println);
                System.out.println("---------------------------");
                int id = Utility.getValidInt(scanner, "Choose one by ID: ");
                consultation = ControllerAppointment.search(id);
                if (consultation == null) {
                    System.out.println("Invalid ID. Please enter a valid consultation ID.");
                }
            } catch (Exception e) {
                System.out.println("Error: Please enter a valid number.");
                scanner.nextLine();
            }
        }
        return consultation;
    }

    public static boolean add(Consultation consultation) {
        CRUD.setConnection(DBConnection.connectionDB());
        String insertion = """
                       INSERT INTO Consultations (IDConsultation, IDTypeReason, isControl, price, IDService) VALUES
                       (? ,? ,? , ?, ?)""";
        List<Object> parameters = new ArrayList<>();
        parameters.add(consultation.getIdConsultation());
        parameters.add(consultation.getReason().getId());
        parameters.add(consultation.getIsControl());
        parameters.add(consultation.getPrice());
        parameters.add(consultation.getIdService());

        try {
            if (CRUD.setAutoCommitDB(false)) {
                boolean success = CRUD.insertDB(insertion, parameters);
                if (success) {
                    CRUD.commitDB();
                    System.out.println("Consultation added successfully.");
                    return true;
                } else {
                    CRUD.rollbackDB();
                    System.out.println("Failed to add the consultation.");
                    return false;
                }
            } else {
                System.out.println("Could not disable auto-commit.");
                return false;
            }
        } catch (Exception e) {
            CRUD.rollbackDB();
            System.out.println("Error adding consultation: " + e.getMessage());
            return false;
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
            Services.status,
            Services.paid
        FROM 
            Consultations
        JOIN 
            Services
        ON 
            Consultations.IDService = Services.IDService
    """;

        List<Object> parameters = new ArrayList<>();
        try {
            ResultSet rs = CRUD.consultDB(sql, parameters);
            while (rs != null && rs.next()) {
                String statusString = rs.getString("status");
                Status status = Utility.statusValid(Status.valueOf(statusString));
                Service baseService;
                baseService = new Service(
                        rs.getInt("IDService"),
                        UtilityTime.changeSqlDate(rs.getString("dateService")),
                        new Pet(rs.getInt("IDPet")),
                        ControllerEmployee.search(rs.getInt("IDEmployee")),
                        ControllerType.search(rs.getInt("IDTypeService"), "Service"),
                        status,
                        rs.getBoolean("paid")
                );

                Consultation consultation = new Consultation.Builder(baseService)
                        .setIdConsultation(rs.getInt("IDConsultation"))
                        .setReason(ControllerType.search(rs.getInt("IDTypeReason"), "Reason"))
                        .setIsControl(rs.getBoolean("isControl"))
                        .setPrice(rs.getDouble("price"))
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

    public static List<Consultation> listSpecial(Status statusQuo) {
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
            Services.status,
            Services.paid
        FROM 
            Consultations
        JOIN 
            Services
        ON 
            Consultations.IDService = Services.IDService
                     where Services.status != ?
    """;

        List<Object> parameters = new ArrayList<>();
        parameters.add(statusQuo.toString());
        try {
            ResultSet rs = CRUD.consultDB(sql, parameters);
            while (rs != null && rs.next()) {
                Status statusS = Status.valueOf(rs.getString("status"));
                Service baseService;
                baseService = new Service(
                        rs.getInt("IDService"),
                        UtilityTime.changeSqlDate(rs.getString("dateService")),
                        new Pet(rs.getInt("IDPet")),
                        ControllerEmployee.search(rs.getInt("IDEmployee")),
                        ControllerType.search(rs.getInt("IDTypeService"), "Service"),
                        statusS,
                        rs.getBoolean("paid")
                );

                Consultation consultation = new Consultation.Builder(baseService)
                        .setIdConsultation(rs.getInt("IDConsultation"))
                        .setReason(ControllerType.search(rs.getInt("IDTypeReason"), "Reason"))
                        .setIsControl(rs.getBoolean("isControl"))
                        .setPrice(rs.getDouble("price"))
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

    public static boolean update(Consultation consultation) {
        CRUD.setConnection(DBConnection.connectionDB());

        String updateQuery = """
        UPDATE Consultations
        SET 
            IDTypeReason = ?, 
            isControl = ?, 
            price = ?, 
            IDService = ?
        WHERE 
            IDConsultation = ?
    """;

        List<Object> parameters = new ArrayList<>();
        parameters.add(consultation.getReason().getId());
        parameters.add(consultation.getIsControl());
        parameters.add(consultation.getPrice());
        parameters.add(consultation.getIdService());
        parameters.add(consultation.getIdConsultation());

        try {
            if (CRUD.setAutoCommitDB(false)) {
                boolean success = CRUD.updateDB(updateQuery, parameters);
                if (success) {
                    CRUD.commitDB();
                    System.out.println("Consultation updated successfully.");
                    return true;
                } else {
                    CRUD.rollbackDB();
                    System.out.println("No consultation found to update.");
                    return false;
                }
            } else {
                System.out.println("Failed to disable auto-commit.");
                return false;
            }
        } catch (Exception e) {
            CRUD.rollbackDB();
            System.out.println("Error updating consultation: " + e.getMessage());
            return false;
        } finally {
            CRUD.closeCon();
        }
    }
}
