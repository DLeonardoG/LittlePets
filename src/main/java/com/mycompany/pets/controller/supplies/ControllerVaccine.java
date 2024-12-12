package com.mycompany.pets.controller.supplies;

import com.mycompany.pets.controller.ControllerType;
import com.mycompany.pets.model.classes.enumsandinterfaces.Createable;
import com.mycompany.pets.model.classes.enumsandinterfaces.Updateable;
import com.mycompany.pets.model.classes.superclasses.Supply;
import com.mycompany.pets.model.classes.superclasses.Type;
import com.mycompany.pets.model.classes.supplies.Vaccine;
import com.mycompany.pets.model.classes.utilities.Utility;
import com.mycompany.pets.model.persistence.CRUD;
import com.mycompany.pets.model.persistence.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class ControllerVaccine implements Createable, Readable, Updateable{

    public static boolean add(Vaccine vaccine) {
        CRUD.setConnection(DBConnection.connectionDB());
        String insertion = """
        INSERT INTO Vaccines (IDTypeVaccine, IDSupplies, batch, expirationDate) 
        VALUES (?, ?, ?, ?)
    """;
        List<Object> parameters = new ArrayList<>();
        parameters.add(vaccine.getType().getId());
        parameters.add(vaccine.getIdSupply());
        parameters.add(vaccine.getBatch());
        parameters.add(vaccine.getExpirationDate());

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
            System.out.println("Error adding vaccine: " + e.getMessage());
            return false;
        } finally {
            CRUD.closeCon();
        }
    }

    public static List<Vaccine> list() {
        CRUD.setConnection(DBConnection.connectionDB());
        List<Vaccine> listVaccines = new ArrayList<>();
        String sql = """
        SELECT 
            v.IDVaccine, v.IDTypeVaccine, v.batch, v.expirationDate,
            s.IDSupplies, s.name, s.stock, s.price, s.IDTypeSupplies
        FROM Vaccines v
        JOIN Supplies s ON v.IDSupplies = s.IDSupplies
    """;

        try {
            ResultSet rs = CRUD.consultDB(sql, new ArrayList<>());
            while (rs != null && rs.next()) {
                Type typeVaccine = ControllerType.search(rs.getInt("IDTypeVaccine"), "Vaccine");
                Type typeSupply = ControllerType.search(rs.getInt("IDTypeSupplies"), "Supplies");

                Supply supply = new Supply(
                        rs.getInt("IDSupplies"),
                        rs.getString("name"),
                        rs.getInt("stock"),
                        typeSupply,
                        rs.getDouble("price")
                );
                Vaccine vaccine = new Vaccine(
                        supply,
                        rs.getInt("batch"),
                        rs.getDate("expirationDate").toLocalDate(),
                        typeVaccine,
                        rs.getInt("IDVaccine")
                );
                listVaccines.add(vaccine);
            }
        } catch (SQLException ex) {
            System.out.println("Error listing Vaccines: " + ex.getMessage());
        } finally {
            CRUD.closeCon();
        }

        return listVaccines;
    }

    public static Vaccine search(int id) {
        CRUD.setConnection(DBConnection.connectionDB());
        String query = """
        SELECT 
            v.IDVaccine, v.IDTypeVaccine, v.batch, v.expirationDate, 
            s.IDSupplies, s.name AS supplyName, s.stock AS supplyStock, s.price, s.IDTypeSupplies
        FROM Vaccines v
        JOIN Supplies s ON v.IDSupplies = s.IDSupplies
        WHERE v.IDVaccine = ?
    """;
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);

        try {
            ResultSet rs = CRUD.consultDB(query, parameters);
            if (rs != null && rs.next()) {
                Type typeVaccine = ControllerType.search(rs.getInt("IDTypeVaccine"), "Vaccine");
                Type typeSupply = ControllerType.search(rs.getInt("IDTypeSupplies"), "Supplies");

                Supply supply = new Supply(
                        rs.getInt("IDSupplies"),
                        rs.getString("supplyName"),
                        rs.getInt("supplyStock"),
                        typeSupply,
                        rs.getDouble("price")
                );
                return new Vaccine(
                        supply,
                        rs.getInt("batch"),
                        rs.getDate("expirationDate").toLocalDate(),
                        typeVaccine,
                        rs.getInt("IDVaccine")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error while searching for Vaccine: " + e.getMessage());
        } finally {
            CRUD.closeCon();
        }
        return null;
    }

    public static boolean update(Vaccine vaccine) {
        CRUD.setConnection(DBConnection.connectionDB());

        String updateVaccineSQL = """
        UPDATE Vaccines 
        SET IDTypeVaccine = ?, batch = ?, expirationDate = ? 
        WHERE IDVaccine = ?
    """;

        String updateSupplySQL = """
        UPDATE Supplies 
        SET name = ?, stock = ?, price = ?, IDTypeSupplies = ? 
        WHERE IDSupplies = ?
    """;

        List<Object> vaccineParameters = new ArrayList<>();
        vaccineParameters.add(vaccine.getType().getId());
        vaccineParameters.add(vaccine.getBatch());
        vaccineParameters.add(vaccine.getExpirationDate());
        vaccineParameters.add(vaccine.getIdVaccine());

        List<Object> supplyParameters = new ArrayList<>();
        supplyParameters.add(vaccine.getName());
        supplyParameters.add(vaccine.getStock());
        supplyParameters.add(vaccine.getPrice());
        supplyParameters.add(vaccine.getTypeSupply().getId());
        supplyParameters.add(vaccine.getIdSupply());

        try {
            if (CRUD.setAutoCommitDB(false)) {
                boolean supplyUpdated = CRUD.updateDB(updateSupplySQL, supplyParameters);
                boolean vaccineUpdated = CRUD.updateDB(updateVaccineSQL, vaccineParameters);

                if (supplyUpdated && vaccineUpdated) {
                    CRUD.commitDB();
                    System.out.println("Vaccine and related supply updated successfully.");
                    return true;
                } else {
                    CRUD.rollbackDB();
                    System.out.println("Update failed: Vaccine or supply not found.");
                    return false;
                }
            } else {
                System.out.println("Failed to disable auto-commit.");
                return false;
            }
        } catch (Exception e) {
            CRUD.rollbackDB();
            System.out.println("Error updating vaccine and supply: " + e.getMessage());
            return false;
        } finally {
            CRUD.closeCon();
        }
    }

    public static Vaccine assign(Scanner scanner) {
        Vaccine vaccine = null;
        while (vaccine == null) {
            try {
                System.out.println("\n--- Vaccines List ---");
                ControllerVaccine.list().forEach(System.out::println);
                System.out.println("---------------------");
                int id = Utility.getValidInt(scanner, "Choose one by ID: ");
                vaccine = ControllerVaccine.search(id);
                if (vaccine == null) {
                    System.out.println("Not valid, enter a valid value.");
                }
            } catch (Exception e) {
                System.out.println("Error: enter correct format.");
                scanner.nextLine();
            }
        }
        return vaccine;
    }

}
