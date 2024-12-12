
package com.mycompany.pets.controller;

import com.mycompany.pets.model.classes.TypePrice;
import com.mycompany.pets.model.persistence.CRUD;
import com.mycompany.pets.model.persistence.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class ControllerTypePrice implements Readable{
    public static List<TypePrice> list(String typeOfType) {
    CRUD.setConnection(DBConnection.connectionDB());
    List<TypePrice> listTypes = new ArrayList<>();
    String sql = "SELECT * FROM Type" + typeOfType; // Asegúrate de que las tablas tienen el campo `price`
    List<Object> parameters = new ArrayList<>();

    try {
        ResultSet rs = CRUD.consultDB(sql, parameters);

        while (rs != null && rs.next()) {
            TypePrice t1 = new TypePrice();
            t1.setId(rs.getInt("IDType" + typeOfType));
            t1.setType(rs.getString("name"));
            t1.setPrice(rs.getDouble("price")); // Asignamos el campo `price`
            listTypes.add(t1);
        }
    } catch (SQLException ex) {
        System.out.println("Error listing types: " + ex.getMessage());
    } finally {
        CRUD.closeCon();
    }

    return listTypes;
}
    public static TypePrice search(int id, String typeOfType) {
    CRUD.setConnection(DBConnection.connectionDB());
    String consult = "SELECT * FROM Type" + typeOfType + " WHERE IDType" + typeOfType + "= ?";
    List<Object> parameters = new ArrayList<>();
    parameters.add(id);
    TypePrice type = null;

    try (ResultSet rs = CRUD.consultDB(consult, parameters)) {
        if (rs != null && rs.next()) { 
            type = new TypePrice();
            type.setId(rs.getInt("IDType" + typeOfType));
            type.setType(rs.getString("name"));
            type.setPrice(rs.getDouble("price")); 
        } else {
            System.out.println("No Type has been found with the id: " + id);
        }
    } catch (SQLException e) {
        System.out.println("Error searching for type: " + e.getMessage());
    } finally {
        CRUD.closeCon();
    }

    return type;
}
    
    public static TypePrice assign(Scanner scanner, String typeOfType) {
    TypePrice type = null;
    while (type == null) {
        try {
            System.out.println("\n--- Types List ---");
            ControllerTypePrice.list(typeOfType).forEach(System.out::println);
            System.out.println("---------------------");
            System.out.print("Choose one: ");
            int id = scanner.nextInt();
            type = ControllerTypePrice.search(id, typeOfType);
            if (type == null) {
                System.out.println("Not valid, enter a valid value.");
            }
        } catch (Exception e) {
            System.out.println("Error: enter correct format.");
            scanner.nextLine();
        }
    }
    return type;
}
     public static boolean update(TypePrice typePrice, String typeOfType) {
        CRUD.setConnection(DBConnection.connectionDB());
        String sql = "UPDATE Type" + typeOfType + " price = ? WHERE IDType" + typeOfType + " = ?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(typePrice.getPrice());
        parameters.add(typePrice.getId());

       try {
            if (CRUD.setAutoCommitDB(false)) {
                boolean success = CRUD.insertDB(sql, parameters);
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
