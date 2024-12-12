
package com.mycompany.pets.controller.people;

import com.mycompany.pets.controller.ControllerType;
import com.mycompany.pets.model.classes.people.Owner;
import com.mycompany.pets.model.classes.superclasses.Type;
import com.mycompany.pets.model.classes.utilities.Utility;
import com.mycompany.pets.model.persistence.CRUD;
import com.mycompany.pets.model.persistence.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ControllerInvoice {
     public static boolean add(Invoice invoice) {
        CRUD.setConnection(DBConnection.connectionDB());
        String insertion = """
                   INSERT INTO Invoice (dateInvoice, total, IDOwner, IDTypeService) 
                   VALUES (?, ?, ?, ?)""";
        List<Object> parameters = new ArrayList<>();
        parameters.add(invoice.getDate());
        parameters.add(invoice.getTotal());
        parameters.add(invoice.getOwner().getId());
        parameters.add(invoice.getTypeService().getId());

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

    public static List<Invoice> list() {
        CRUD.setConnection(DBConnection.connectionDB());
        List<Invoice> listInvoices = new ArrayList<>();
        String sql = "SELECT * FROM Invoice";
        List<Object> parameters = new ArrayList<>();

        try {
            ResultSet rs = CRUD.consultDB(sql, parameters);
            while (rs != null && rs.next()) {
                Owner owner = ControllerOwner.search(rs.getInt("IDOwner"));
                Type typeService = ControllerType.search(rs.getInt("IDTypeService"), "Service");
                Invoice invoice = new Invoice(
                        rs.getInt("IDInvoice"),
                        owner,
                        rs.getDouble("total"),
                        typeService
                );
                listInvoices.add(invoice);
            }
        } catch (SQLException ex) {
            System.out.println("Error listing Invoices: " + ex.getMessage());
        } finally {
            CRUD.closeCon();
        }

        return listInvoices;
    }

    public static Invoice search(int id) {
        CRUD.setConnection(DBConnection.connectionDB());
        String query = """
               SELECT 
                   IDInvoice, dateInvoice, total, IDOwner, IDTypeService
               FROM 
                   Invoice
               WHERE 
                   IDInvoice = ?""";
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);

        try {
            ResultSet rs = CRUD.consultDB(query, parameters);
            if (rs != null && rs.next()) {
                Owner owner = ControllerOwner.search(rs.getInt("IDOwner"));
                Type typeService = ControllerType.search(rs.getInt("IDTypeService"), "Service");
                return new Invoice(
                        rs.getInt("IDInvoice"),
                        owner,
                        rs.getDouble("total"),
                        typeService
                );
            }
        } catch (SQLException e) {
            System.out.println("Error while searching for Invoice: " + e.getMessage());
        } finally {
            CRUD.closeCon();
        }
        return null;
    }

    public static Invoice assign(Scanner scanner) {
        Invoice invoice = null;
        while (invoice == null) {
            try {
                System.out.println("\n--- Invoices List ---");
                ControllerInvoice.list().forEach(System.out::println);
                System.out.println("---------------------");
                int id = Utility.getValidInt(scanner, "Choose one by ID: ");
                invoice = ControllerInvoice.search(id);
                if (invoice == null) {
                    System.out.println("Not valid, enter a valid value.");
                }
            } catch (Exception e) {
                System.out.println("Error: enter correct format.");
                scanner.nextLine();
            }
        }
        return invoice;
    }
}
