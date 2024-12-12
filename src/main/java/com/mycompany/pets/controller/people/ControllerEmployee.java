package com.mycompany.pets.controller.people;

import com.mycompany.pets.controller.ControllerType;
import com.mycompany.pets.model.classes.enumsandinterfaces.Createable;
import com.mycompany.pets.model.classes.enumsandinterfaces.Updateable;
import com.mycompany.pets.model.classes.people.Employee;
import com.mycompany.pets.model.classes.superclasses.Type;
import com.mycompany.pets.model.classes.utilities.Utility;
import com.mycompany.pets.model.persistence.CRUD;
import com.mycompany.pets.model.persistence.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class ControllerEmployee implements Readable, Createable, Updateable {

    public static boolean add(Employee employee) {
        CRUD.setConnection(DBConnection.connectionDB());
        String insertion = """
                       INSERT INTO Employees (ID, name, phoneNumber, email, IDTypeEmployee) 
                       VALUES (?, ?, ?, ?, ?)""";
        List<Object> parameters = new ArrayList<>();
        parameters.add(employee.getId());
        parameters.add(employee.getName());
        parameters.add(employee.getPhoneNumber());
        parameters.add(employee.getEmail());
        parameters.add(employee.getType().getId());

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

    public static List<Employee> list() {
        CRUD.setConnection(DBConnection.connectionDB());
        List<Employee> listEmployees = new ArrayList<>();
        String sql = "SELECT * FROM Employees";
        List<Object> parameters = new ArrayList<>();

        try {
            ResultSet rs = CRUD.consultDB(sql, parameters);

            while (rs != null && rs.next()) {
                Employee e1 = new Employee();
                e1.setId(rs.getInt("IDEmployee"));
                e1.setID(rs.getString("ID"));
                e1.setName(rs.getString("name"));
                e1.setPhoneNumber(rs.getString("phoneNumber"));
                e1.setEmail(rs.getString("email"));
                Type type = ControllerType.search(rs.getInt("IDTypeEmployee"), "Employee");
                e1.setType(type);
                listEmployees.add(e1);
            }

        } catch (SQLException ex) {
            System.out.println("Error listing Employees: " + ex.getMessage());
        } finally {
            CRUD.closeCon();
        }

        return listEmployees;
    }

    public static Employee search(int id) {
        CRUD.setConnection(DBConnection.connectionDB());
        String consult = "SELECT * FROM Employees WHERE IDEmployee= ?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);
        Employee employee = null;

        try (ResultSet rs = CRUD.consultDB(consult, parameters)) {
            if (rs != null && rs.next()) { // Verify if there are results
                employee = new Employee();
                employee.setId(rs.getInt("IDEmployee"));
                employee.setID(rs.getString("ID"));
                employee.setName(rs.getString("name"));
                employee.setPhoneNumber(rs.getString("phoneNumber"));
                employee.setEmail(rs.getString("email"));
                Type type = ControllerType.search(rs.getInt("IDTypeEmployee"), "Employee");
                employee.setType(type);
            } else {
                System.out.println("No Employee has been found with the id: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error searching employee: " + e.getMessage());
        } finally {
            CRUD.closeCon();
        }

        return employee;
    }

    public static Employee assign(Scanner scanner) {
        Employee employee = null;
        while (employee == null) {
            try {
                System.out.println("\n--- Employee List ---");
                ControllerEmployee.list().forEach(System.out::println);
                System.out.println("---------------------");
                int id = Utility.getValidInt(scanner, "Choose one by ID: ");
                employee = ControllerEmployee.search(id);
                if (employee == null) {
                    System.out.println("No valid, enter a valid value");
                }
            } catch (Exception e) {
                System.out.println("Error: enter correct format");
                scanner.nextLine();
            }
        }
        return employee;
    }

    public static boolean update(Employee employee) {
        CRUD.setConnection(DBConnection.connectionDB());
        String sql = "UPDATE Employees SET ID = ?, name = ?, phoneNumber = ?, email = ?, IDTypeEmployee = ? WHERE IDEmployee = ?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(employee.getID());
        parameters.add(employee.getName());
        parameters.add(employee.getPhoneNumber());
        parameters.add(employee.getEmail());
        parameters.add(employee.getType().getId());
        parameters.add(employee.getId());

        try {
            if (CRUD.setAutoCommitDB(false)) {
                boolean success = CRUD.updateDB(sql, parameters); // updateDB para ejecutar actualizaciones
                if (success) {
                    CRUD.commitDB();
                    System.out.println("Employee updated successfully.");
                    return true;
                } else {
                    CRUD.rollbackDB();
                    System.out.println("No employee found to update.");
                    return false;
                }
            } else {
                System.out.println("Failed to disable auto-commit.");
                return false;
            }
        } catch (Exception e) {
            CRUD.rollbackDB();
            System.out.println("Error updating employee: " + e.getMessage());
            return false;
        } finally {
            CRUD.closeCon();
        }
    }

}
