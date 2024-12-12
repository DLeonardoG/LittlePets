/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pets.controller.supplies;

import com.mycompany.pets.controller.ControllerType;
import com.mycompany.pets.model.classes.enumsandinterfaces.Createable;
import com.mycompany.pets.model.classes.enumsandinterfaces.Updateable;
import com.mycompany.pets.model.classes.superclasses.Supply;
import com.mycompany.pets.model.classes.superclasses.Type;
import com.mycompany.pets.model.classes.supplies.Medicine;
import com.mycompany.pets.model.classes.utilities.Utility;
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
public abstract class ControllerMedicine implements Createable, Readable, Updateable {

    public static boolean add(Medicine medicine) {
        CRUD.setConnection(DBConnection.connectionDB());
        String insertion = """
        INSERT INTO Medicines (IDMedicine, IDTypeMedicine, IDSupplies, expirationDate) 
        VALUES (?, ?, ?, ?)""";
        List<Object> parameters = new ArrayList<>();
        parameters.add(medicine.getIdMedicine());
        parameters.add(medicine.getTypeMedicine().getId());
        parameters.add(medicine.getIdSupply());
        parameters.add(medicine.getExpirationDate());

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

    public static List<Medicine> list() {
        CRUD.setConnection(DBConnection.connectionDB());
        List<Medicine> listMedicines = new ArrayList<>();
        String sql = """
        SELECT 
            m.IDMedicine, m.IDTypeMedicine, m.expirationDate, 
            s.IDSupplies, s.name AS supplyName, s.stock AS supplyStock, s.price, s.IDTypeSupplies
        FROM Medicines m
        JOIN Supplies s ON m.IDSupplies = s.IDSupplies
    """;

        try {
            ResultSet rs = CRUD.consultDB(sql, new ArrayList<>());
            while (rs != null && rs.next()) {

                Type typeMedicine = ControllerType.search(rs.getInt("IDTypeMedicine"), "Medicine");
                Type typeSupply = ControllerType.search(rs.getInt("IDTypeSupplies"), "Supplies");

                Supply supply = new Supply(
                        rs.getInt("IDSupplies"),
                        rs.getString("supplyName"),
                        rs.getInt("supplyStock"),
                        typeSupply,
                        rs.getDouble("price")
                );
                Medicine medicine = new Medicine(
                        supply,
                        typeMedicine,
                        rs.getDate("expirationDate").toLocalDate(),
                        rs.getInt("IDMedicine")
                );
                listMedicines.add(medicine);
            }
        } catch (SQLException ex) {
            System.out.println("Error listing Medicines: " + ex.getMessage());
        } finally {
            CRUD.closeCon();
        }

        return listMedicines;
    }

    public static Medicine assign(Scanner scanner) {
        Medicine medicine = null;
        while (medicine == null) {
            try {
                System.out.println("\n--- Medicines List ---");
                ControllerMedicine.list().forEach(System.out::println);
                System.out.println("---------------------");
                int id = Utility.getValidInt(scanner, "Choose one by ID: ");
                medicine = ControllerMedicine.search(id);
                if (medicine == null) {
                    System.out.println("Not valid, enter a valid value.");
                }
            } catch (Exception e) {
                System.out.println("Error: enter correct format.");
                scanner.nextLine();
            }
        }
        return medicine;
    }

    public static Medicine search(int id) {
        CRUD.setConnection(DBConnection.connectionDB());
        String query = """
        SELECT 
            m.IDMedicine, m.IDTypeMedicine, m.expirationDate 
            s.IDSupplies, s.name AS supplyName, s.stock AS supplyStock, s.price, s.IDTypeSupplies
        FROM Medicines m
        JOIN Supplies s ON m.IDSupplies = s.IDSupplies
        WHERE m.IDMedicine = ?
    """;
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);

        try {
            ResultSet rs = CRUD.consultDB(query, parameters);
            if (rs != null && rs.next()) {
                Type typeMedicine = ControllerType.search(rs.getInt("IDTypeMedicine"), "Medicine");
                Type typeSupply = ControllerType.search(rs.getInt("IDTypeSupplies"), "Supplies");
                Supply supply = new Supply(
                        rs.getInt("IDSupplies"),
                        rs.getString("supplyName"),
                        rs.getInt("supplyStock"),
                        typeSupply,
                        rs.getDouble("price")
                );
                Medicine medicine = new Medicine(
                        supply,
                        typeMedicine,
                        rs.getDate("expirationDate").toLocalDate(),
                        rs.getInt("IDMedicine")
                );
                medicine.setName(rs.getString("medicineName"));
                medicine.setStock(rs.getInt("stock"));
                return medicine;
            }
        } catch (SQLException e) {
            System.out.println("Error while searching for Medicine: " + e.getMessage());
        } finally {
            CRUD.closeCon();
        }
        return null;
    }

    public static boolean update(Medicine medicine) {
        CRUD.setConnection(DBConnection.connectionDB());
        String updateMedicineSQL = """
        UPDATE Medicines 
        SET IDTypeMedicine = ?, expirationDate = ?
        WHERE IDMedicine = ?
    """;

        String updateSupplySQL = """
        UPDATE Supplies 
        SET name = ?, stock = ?, price = ?, IDTypeSupplies = ? 
        WHERE IDSupplies = ?
    """;
        List<Object> medicineParameters = new ArrayList<>();
        medicineParameters.add(medicine.getTypeMedicine().getId());
        medicineParameters.add(medicine.getExpirationDate());
        medicineParameters.add(medicine.getIdMedicine());

        List<Object> supplyParameters = new ArrayList<>();
        supplyParameters.add(medicine.getName());
        supplyParameters.add(medicine.getStock());
        supplyParameters.add(medicine.getPrice());
        supplyParameters.add(medicine.getTypeSupply().getId());
        supplyParameters.add(medicine.getIdSupply());

        try {
            if (CRUD.setAutoCommitDB(false)) {
                boolean supplyUpdated = CRUD.updateDB(updateSupplySQL, supplyParameters);
                boolean medicineUpdated = CRUD.updateDB(updateMedicineSQL, medicineParameters);

                if (supplyUpdated && medicineUpdated) {
                    CRUD.commitDB();
                    System.out.println("Medicine and related supply updated successfully.");
                    return true;
                } else {
                    CRUD.rollbackDB();
                    System.out.println("Update failed: Medicine or supply not found.");
                    return false;
                }
            } else {
                System.out.println("Failed to disable auto-commit.");
                return false;
            }
        } catch (Exception e) {
            CRUD.rollbackDB();
            System.out.println("Error updating medicine and supply: " + e.getMessage());
            return false;
        } finally {
            CRUD.closeCon();
        }
    }

}
