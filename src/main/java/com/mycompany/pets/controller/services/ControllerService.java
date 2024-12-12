package com.mycompany.pets.controller.services;

import com.mycompany.pets.controller.ControllerType;
import com.mycompany.pets.controller.people.ControllerEmployee;
import com.mycompany.pets.model.classes.people.Employee;
import com.mycompany.pets.model.classes.enumsandinterfaces.Status;
import com.mycompany.pets.model.classes.animals.Pet;
import com.mycompany.pets.model.classes.enumsandinterfaces.Createable;
import com.mycompany.pets.model.classes.enumsandinterfaces.Updateable;
import com.mycompany.pets.model.persistence.CRUD;
import com.mycompany.pets.model.persistence.DBConnection;
import com.mycompany.pets.model.classes.superclasses.Service;
import com.mycompany.pets.model.classes.superclasses.Type;
import com.mycompany.pets.model.classes.utilities.AnimalUtils;
import com.mycompany.pets.model.classes.utilities.Utility;
import com.mycompany.pets.model.classes.utilities.UtilityTime;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class ControllerService implements Readable, Createable, Updateable {

    public static boolean add(Service s1) {
        CRUD.setConnection(DBConnection.connectionDB());

        String insertion = """
                           INSERT INTO Services (IDService, dateService, IDEmployee, IDPet, IDTypeService, status,paid) VALUES
                           (? ,? ,? , ?, ?,?,?)""";
        List<Object> parameters = new ArrayList<>();
        parameters.add(s1.getIdService());
        parameters.add(s1.getDate());
        parameters.add(s1.getEmployee().getId());
        parameters.add(s1.getPet().getId());
        parameters.add(s1.getTypeService().getId());
        parameters.add(s1.getStatus() != null ? s1.getStatus().toString() : "SCHEDULED");
        parameters.add(s1.getPaid());

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

    public static List<Service> list() {
        CRUD.setConnection(DBConnection.connectionDB());
        List<Service> listServices = new ArrayList<>();
        String sql = "SELECT * FROM Services ";
        List<Object> parameters = new ArrayList<>();
        try {
            ResultSet rs = CRUD.consultDB(sql, parameters);
            while (rs != null && rs.next()) {
                Service s1 = new Service();
                s1.setIdService(rs.getInt("IDService"));
                s1.setEmployeee(ControllerEmployee.search(rs.getInt("IDEmployee")));
                s1.setDate(UtilityTime.changeSqlDate(rs.getString("dateService")));
                Type type = ControllerType.search(rs.getInt("IDTypeService"), "Employee");
                s1.setTypeService(type);
                listServices.add(s1);
            }
        } catch (SQLException ex) {
            System.out.println("Error listing Services: " + ex.getMessage());
        } finally {
            CRUD.closeCon();
        }

        return listServices;
    }

    public static Service search(int id) {
        CRUD.setConnection(DBConnection.connectionDB());
        String query = """
                   SELECT 
                       IDService, date, IDEmployee, IDPet, IDTypeService,status,paid
                   FROM 
                       Services
                   WHERE 
                       IDService = ?""";
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);

        try {
            ResultSet rs = CRUD.consultDB(query, parameters);
            if (rs != null && rs.next()) {
                Service service = new Service();
                service.setIdService(rs.getInt("IDService"));
                service.setDate(UtilityTime.changeSqlDate(rs.getString("date")));
                service.setEmployeee(ControllerEmployee.search(rs.getInt("IDEmployee")));
                service.setPet(AnimalUtils.());
                service.setTypeService(ControllerType.search(rs.getInt("IDTypeService"), "ServiceType"));
                String statusString = rs.getString("status");
                Status status = Status.valueOf(statusString.toUpperCase());
                service.setStatus(status);
                service.setPaid(rs.getBoolean("paid"));
                return service;
            }
        } catch (SQLException e) {
            System.out.println("Error while searching for Service: " + e.getMessage());
        } finally {
            CRUD.closeCon();
        }
        return null;
    }

    public static Service assign(Scanner scanner) {
        Service service = null;
        while (service == null) {
            try {
                System.out.println("\n--- Services List ---");
                ControllerService.list().forEach(System.out::println);
                System.out.println("---------------------");
                int id = Utility.getValidInt(scanner, "Choose one by ID: ");
                service = ControllerService.search(id);
                if (service == null) {
                    System.out.println("Not valid, enter a valid value.");
                }
            } catch (Exception e) {
                System.out.println("Error: enter correct format.");
                scanner.nextLine();
            }
            return service;
        }
        return service;
    }

    public static boolean update(Service s1) {
        CRUD.setConnection(DBConnection.connectionDB());

        String updateQuery = """
        UPDATE Services
        SET dateService = ?, IDEmployee = ?, IDPet = ?, IDTypeService = ?, status = ? ,paid =?
        WHERE IDService = ?
    """;

        List<Object> parameters = new ArrayList<>();
        parameters.add(s1.getDate());
        parameters.add(s1.getEmployee().getId());
        parameters.add(s1.getPet().getId());
        parameters.add(s1.getTypeService().getId());
        parameters.add(s1.getStatus().toString());
        parameters.add(s1.getPaid());
        parameters.add(s1.getIdService());

        try {
            if (CRUD.setAutoCommitDB(false)) {
                boolean success = CRUD.updateDB(updateQuery, parameters);
                if (success) {
                    CRUD.commitDB();
                    System.out.println("Service updated successfully.");
                    return true;
                } else {
                    CRUD.rollbackDB();
                    System.out.println("No service found to update.");
                    return false;
                }
            } else {
                System.out.println("Failed to disable auto-commit.");
                return false;
            }
        } catch (Exception e) {
            CRUD.rollbackDB();
            System.out.println("Error updating service: " + e.getMessage());
            return false;
        } finally {
            CRUD.closeCon();
        }
    }

}
