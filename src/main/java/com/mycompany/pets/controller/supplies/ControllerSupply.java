package com.mycompany.pets.controller.supplies;

import com.mycompany.pets.controller.ControllerType;
import com.mycompany.pets.model.classes.enumsandinterfaces.Createable;
import com.mycompany.pets.model.classes.enumsandinterfaces.Updateable;
import com.mycompany.pets.model.classes.superclasses.Supply;
import com.mycompany.pets.model.classes.superclasses.Type;
import com.mycompany.pets.model.classes.utilities.Utility;
import com.mycompany.pets.model.persistence.CRUD;
import com.mycompany.pets.model.persistence.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class ControllerSupply implements Createable, Readable, Updateable {

    public static boolean add(Supply supply) {
        CRUD.setConnection(DBConnection.connectionDB());
        String insertion = """
               INSERT INTO Supplies (name, stock, price, IDTypeSupplies) 
               VALUES (?, ?, ?, ?)""";
        List<Object> parameters = new ArrayList<>();
        parameters.add(supply.getName());
        parameters.add(supply.getStock());
        parameters.add(supply.getPrice());
        parameters.add(supply.getTypeSupply().getId());

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

    public static List<Supply> list() {
        CRUD.setConnection(DBConnection.connectionDB());
        List<Supply> listSupplies = new ArrayList<>();
        String sql = "SELECT * FROM Supplies";
        List<Object> parameters = new ArrayList<>();

        try {
            ResultSet rs = CRUD.consultDB(sql, parameters);
            while (rs != null && rs.next()) {
                Type typeSupply = ControllerType.search(rs.getInt("IDTypeSupplies"), "Supplies");
                Supply supply = new Supply(
                        rs.getInt("IDSupplies"),
                        rs.getString("name"),
                        rs.getInt("stock"),
                        typeSupply,
                        rs.getDouble("price")
                );
                listSupplies.add(supply);
            }
        } catch (SQLException ex) {
            System.out.println("Error listing Supplies: " + ex.getMessage());
        } finally {
            CRUD.closeCon();
        }

        return listSupplies;
    }

    public static Supply search(int id) {
        CRUD.setConnection(DBConnection.connectionDB());
        String query = """
               SELECT 
                   IDSupplies, name, stock, price, IDTypeSupplies
               FROM 
                   Supplies
               WHERE 
                   IDSupplies = ?""";
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);

        try {
            ResultSet rs = CRUD.consultDB(query, parameters);
            if (rs != null && rs.next()) {
                Type typeSupply = ControllerType.search(rs.getInt("IDTypeSupplies"), "Supplies");
                return new Supply(
                        rs.getInt("IDSupplies"),
                        rs.getString("name"),
                        rs.getInt("stock"),
                        typeSupply,
                        rs.getDouble("price")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error while searching for Supply: " + e.getMessage());
        } finally {
            CRUD.closeCon();
        }
        return null;
    }

    public static Supply assign(Scanner scanner) {
        Supply supply = null;
        while (supply == null) {
            try {
                System.out.println("\n--- Supplies List ---");
                ControllerSupply.list().forEach(System.out::println);
                System.out.println("---------------------");
                int id = Utility.getValidInt(scanner, "Choose one by ID: ");
                supply = ControllerSupply.search(id);
                if (supply == null) {
                    System.out.println("Not valid, enter a valid value.");
                }
            } catch (Exception e) {
                System.out.println("Error: enter correct format.");
                scanner.nextLine();
            }
        }
        return supply;
    }

    public static boolean update(Supply supply) {
        CRUD.setConnection(DBConnection.connectionDB());
        String sql = "UPDATE Supplies SET name = ?, stock = ?, price = ?, IDTypeSupplies = ? WHERE IDSupplies = ?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(supply.getName());
        parameters.add(supply.getStock());
        parameters.add(supply.getPrice());
        parameters.add(supply.getTypeSupply().getId());
        parameters.add(supply.getIdSupply());

        try {
            if (CRUD.setAutoCommitDB(false)) {
                boolean success = CRUD.updateDB(sql, parameters);
                if (success) {
                    CRUD.commitDB();
                    System.out.println("Supply updated successfully.");
                    return true;
                } else {
                    CRUD.rollbackDB();
                    System.out.println("No supply found to update.");
                    return false;
                }
            } else {
                System.out.println("Failed to disable auto-commit.");
                return false;
            }
        } catch (Exception e) {
            CRUD.rollbackDB();
            System.out.println("Error updating supply: " + e.getMessage());
            return false;
        } finally {
            CRUD.closeCon();
        }
    }

}
