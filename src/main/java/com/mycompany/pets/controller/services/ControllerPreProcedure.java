/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pets.controller.services;

import com.mycompany.pets.model.classes.services.PreProcedure;
import com.mycompany.pets.model.persistence.CRUD;
import com.mycompany.pets.model.persistence.DBConnection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author camper
 */
public class ControllerPreProcedure {
//    public static boolean addPreProcedure(PreProcedure prep1) {
//    CRUD.setConnection(DBConnection.connectionDB());
//
//    String insertion = """
//        INSERT INTO PreProcedure (analysis, expectedTime, IDProcedure) VALUES
//        (?, ?, ?)""";
//
//    List<Object> parameters = new ArrayList<>();
//    parameters.add(prep1.getAnalysis());
//    parameters.add(prep1.getExpectedTime());
//    parameters.add(prep1.getProcedure().getIdProcedure());
//
//    try {
//        if (CRUD.setAutoCommitDB(false)) {
//            boolean success = CRUD.insertDB(insertion, parameters);
//            if (success) {
//                CRUD.commitDB();
//                return true;
//            } else {
//                CRUD.rollbackDB();
//                return false;
//            }
//        } else {
//            return false;
//        }
//    } catch (Exception e) {
//        CRUD.rollbackDB();
//        throw e;
//    } finally {
//        CRUD.closeCon();
//    }
//}
//
//public static List<PreProcedure> listPreProcedures() {
//    CRUD.setConnection(DBConnection.connectionDB());
//    List<PreProcedure> listPreProcedures = new ArrayList<>();
//    String sql = "SELECT * FROM PreProcedure";
//    List<Object> parameters = new ArrayList<>();
//
//    try {
//        ResultSet rs = CRUD.consultDB(sql, parameters);
//        while (rs != null && rs.next()) {
//            PreProcedure prep1 = new PreProcedure();
//            prep1.setIdPreProcedure(rs.getInt("IDPreProcedure"));
//            prep1.setAnalysis(rs.getString("analysis"));
//            prep1.setExpectedTime(rs.getString("expectedTime"));
//            prep1.setProcedure(ControllerProcedure.searchProcedure(rs.getInt("IDProcedure")));
//            listPreProcedures.add(prep1);
//        }
//    } catch (SQLException ex) {
//        System.out.println("Error listing PreProcedures: " + ex.getMessage());
//    } finally {
//        CRUD.closeCon();
//    }
//
//    return listPreProcedures;
//}
//
//public static PreProcedure searchPreProcedure(int id) {
//    CRUD.setConnection(DBConnection.connectionDB());
//    String query = """
//        SELECT IDPreProcedure, analysis, expectedTime, IDProcedure
//        FROM PreProcedure WHERE IDPreProcedure = ?""";
//
//    List<Object> parameters = new ArrayList<>();
//    parameters.add(id);
//
//    try {
//        ResultSet rs = CRUD.consultDB(query, parameters);
//        if (rs != null && rs.next()) {
//            PreProcedure preProcedure = new PreProcedure();
//            preProcedure.setIdPreProcedure(rs.getInt("IDPreProcedure"));
//            preProcedure.setAnalysis(rs.getString("analysis"));
//            preProcedure.setExpectedTime(rs.getString("expectedTime"));
//            preProcedure.setProcedure(ControllerProcedure.searchProcedure(rs.getInt("IDProcedure")));
//            return preProcedure;
//        }
//    } catch (SQLException e) {
//        System.out.println("Error while searching for PreProcedure: " + e.getMessage());
//    } finally {
//        CRUD.closeCon();
//    }
//    return null;
//}
//
//public static boolean updatePreProcedure(PreProcedure prep1) {
//    CRUD.setConnection(DBConnection.connectionDB());
//
//    String updateQuery = """
//        UPDATE PreProcedure
//        SET analysis = ?, expectedTime = ?, IDProcedure = ?
//        WHERE IDPreProcedure = ?""";
//
//    List<Object> parameters = new ArrayList<>();
//    parameters.add(prep1.getAnalysis());
//    parameters.add(prep1.getExpectedTime());
//    parameters.add(prep1.getProcedure().getIdProcedure());
//    parameters.add(prep1.getIdPreProcedure());
//
//    try {
//        if (CRUD.setAutoCommitDB(false)) {
//            boolean success = CRUD.updateDB(updateQuery, parameters);
//            if (success) {
//                CRUD.commitDB();
//                System.out.println("PreProcedure updated successfully.");
//                return true;
//            } else {
//                CRUD.rollbackDB();
//                System.out.println("No PreProcedure found to update.");
//                return false;
//            }
//        } else {
//            System.out.println("Failed to disable auto-commit.");
//            return false;
//        }
//    } catch (Exception e) {
//        CRUD.rollbackDB();
//        System.out.println("Error updating PreProcedure: " + e.getMessage());
//        return false;
//    } finally {
//        CRUD.closeCon();
//    }
//}

}
