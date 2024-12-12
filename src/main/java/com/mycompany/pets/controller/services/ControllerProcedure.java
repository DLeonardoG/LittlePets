/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pets.controller.services;

import com.mycompany.pets.controller.ControllerType;
import com.mycompany.pets.model.classes.enumsandinterfaces.Createable;
import com.mycompany.pets.model.classes.enumsandinterfaces.Status;
import com.mycompany.pets.model.classes.enumsandinterfaces.Updateable;
import com.mycompany.pets.model.classes.services.Procedure;
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
public abstract class ControllerProcedure implements Readable, Createable, Updateable{
    public static boolean addProcedure(Procedure p1) {
    CRUD.setConnection(DBConnection.connectionDB());

    String insertion = """
        INSERT INTO Procedures (name, price, status, IDTypeProcedure, IDService) VALUES
        (?, ?, ?, ?, ?)""";

    List<Object> parameters = new ArrayList<>();
    parameters.add(p1.getName());
    parameters.add(p1.getPrice());
    parameters.add(p1.getStatus().toString());
    parameters.add(p1.getTypeProcedure().getId());
    parameters.add(p1.getService().getIdService());

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

public static List<Procedure> listProcedures() {
    CRUD.setConnection(DBConnection.connectionDB());
    List<Procedure> listProcedures = new ArrayList<>();
    String sql = "SELECT * FROM Procedures";
    List<Object> parameters = new ArrayList<>();

    try {
        ResultSet rs = CRUD.consultDB(sql, parameters);
        while (rs != null && rs.next()) {
            Procedure p1 = new Procedure();
            p1.setIdProcedure(rs.getInt("IDProcedure"));
            p1.setName(rs.getString("name"));
            p1.setPrice(rs.getDouble("price"));
            p1.setStatus(Status.valueOf(rs.getString("status")));
            p1.setTypeProcedure(ControllerType.search(rs.getInt("IDTypeProcedure"),"Procedure"));
            p1.setService(ControllerService.search(rs.getInt("IDService")));
            listProcedures.add(p1);
        }
    } catch (SQLException ex) {
        System.out.println("Error listing Procedures: " + ex.getMessage());
    } finally {
        CRUD.closeCon();
    }

    return listProcedures;
}

public static Procedure searchProcedure(int id) {
    CRUD.setConnection(DBConnection.connectionDB());
    String query = """
        SELECT IDProcedure, name, price, status, IDTypeProcedure, IDService
        FROM Procedures WHERE IDProcedure = ?""";

    List<Object> parameters = new ArrayList<>();
    parameters.add(id);

    try {
        ResultSet rs = CRUD.consultDB(query, parameters);
        if (rs != null && rs.next()) {
            Procedure procedure = new Procedure();
            procedure.setIdProcedure(rs.getInt("IDProcedure"));
            procedure.setName(rs.getString("name"));
            procedure.setPrice(rs.getDouble("price"));
            procedure.setStatus(Status.valueOf(rs.getString("status")));
            procedure.setTypeProcedure(ControllerType.search(rs.getInt("IDTypeProcedure"),"Procedure"));
            procedure.setService(ControllerService.search(rs.getInt("IDService")));
            return procedure;
        }
    } catch (SQLException e) {
        System.out.println("Error while searching for Procedure: " + e.getMessage());
    } finally {
        CRUD.closeCon();
    }
    return null;
}

public static boolean updateProcedure(Procedure p1) {
    CRUD.setConnection(DBConnection.connectionDB());

    String updateQuery = """
        UPDATE Procedures
        SET name = ?, price = ?, status = ?, IDTypeProcedure = ?, IDService = ?
        WHERE IDProcedure = ?""";

    List<Object> parameters = new ArrayList<>();
    parameters.add(p1.getName());
    parameters.add(p1.getPrice());
    parameters.add(p1.getStatus().toString());
    parameters.add(p1.getTypeProcedure().getId());
    parameters.add(p1.getService().getIdService());
    parameters.add(p1.getIdProcedure());

    try {
        if (CRUD.setAutoCommitDB(false)) {
            boolean success = CRUD.updateDB(updateQuery, parameters);
            if (success) {
                CRUD.commitDB();
                System.out.println("Procedure updated successfully.");
                return true;
            } else {
                CRUD.rollbackDB();
                System.out.println("No procedure found to update.");
                return false;
            }
        } else {
            System.out.println("Failed to disable auto-commit.");
            return false;
        }
    } catch (Exception e) {
        CRUD.rollbackDB();
        System.out.println("Error updating procedure: " + e.getMessage());
        return false;
    } finally {
        CRUD.closeCon();
    }
}

}
