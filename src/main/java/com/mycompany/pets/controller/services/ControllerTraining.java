package com.mycompany.pets.controller.services;

import com.mycompany.pets.controller.ControllerType;
import com.mycompany.pets.controller.ControllerTypePrice;
import com.mycompany.pets.controller.animal.ControllerAnimal;
import com.mycompany.pets.controller.people.ControllerEmployee;
import com.mycompany.pets.model.classes.animals.Pet;
import com.mycompany.pets.model.classes.enumsandinterfaces.Createable;
import com.mycompany.pets.model.classes.enumsandinterfaces.Status;
import com.mycompany.pets.model.classes.enumsandinterfaces.Updateable;
import com.mycompany.pets.model.classes.services.Training;
import com.mycompany.pets.model.classes.superclasses.Animal;
import com.mycompany.pets.model.classes.utilities.Utility;
import com.mycompany.pets.model.classes.utilities.UtilityTime;
import com.mycompany.pets.model.persistence.CRUD;
import com.mycompany.pets.model.persistence.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class ControllerTraining implements Readable, Createable, Updateable {

    public static boolean add(Training training) {
        CRUD.setConnection(DBConnection.connectionDB());

        String insertion = """
                       INSERT INTO Training 
                       (IDTraining, estimatedTime, IDService, totalPrice, IDBehavior, IDBreed) 
                       VALUES (?, ?, ?, ?, ?, ?)""";

        List<Object> parameters = new ArrayList<>();
        parameters.add(training.getIdTraining());
        parameters.add(training.getEstimatedTime());
        parameters.add(training.getIdService());
        parameters.add(training.getTotalPrice());
        parameters.add(training.getTypeBehavior().getId());
        parameters.add(training.getTypeBreed().getId());

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

    public static List<Training> list() {
        CRUD.setConnection(DBConnection.connectionDB());
        List<Training> listTrainings = new ArrayList<>();
        String sql = "SELECT * FROM Training";
        List<Object> parameters = new ArrayList<>();

        try {
            ResultSet rs = CRUD.consultDB(sql, parameters);
            while (rs != null && rs.next()) {
                Animal h = ControllerAnimal.search(rs.getInt("IDPet"));
                Training training = new Training(
                        rs.getString("estimatedTime"),
                        rs.getString("IDTraining"),
                        ControllerTypePrice.search(rs.getInt("IDBreed"), "Breed"),
                        ControllerTypePrice.search(rs.getInt("IDBehavior"), "Behavior"),
                        rs.getDouble("totalPrice"),
                        rs.getInt("IDService"),
                        UtilityTime.changeSqlDate(rs.getString("date")),
                        // todo lo pet no funciona
                        h,// esto hay que modularlo y actualizarzo
                        ControllerEmployee.search(rs.getInt("IDEmployee")),
                        ControllerType.search(rs.getInt("IDTypeService"), "ServiceType"),
                        Status.valueOf(rs.getString("status")),
                        rs.getBoolean("paid")
                );
                listTrainings.add(training);
            }
        } catch (SQLException ex) {
            System.out.println("Error listing Trainings: " + ex.getMessage());
        } finally {
            CRUD.closeCon();
        }

        return listTrainings;
    }

    public static Training search(int id) {
        CRUD.setConnection(DBConnection.connectionDB());
        String query = """
                   SELECT * 
                   FROM Training 
                   WHERE IDTraining = ?""";
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);

        try {
            ResultSet rs = CRUD.consultDB(query, parameters);
            if (rs != null && rs.next()) {
                Animal h = ControllerAnimal.search(rs.getInt("IDPet"));
                return new Training(
                        rs.getString("estimatedTime"),
                        rs.getString("IDTraining"),
                        ControllerTypePrice.search(rs.getInt("IDBreed"), "Breed"),
                        ControllerTypePrice.search(rs.getInt("IDBehavior"), "Behavior"),
                        rs.getDouble("totalPrice"),
                        rs.getInt("IDService"),
                        UtilityTime.changeSqlDate(rs.getString("date")),
                        h,
                        ControllerEmployee.search(rs.getInt("IDEmployee")),
                        ControllerType.search(rs.getInt("IDTypeService"), "ServiceType"),
                        Status.valueOf(rs.getString("status")),
                        rs.getBoolean("paid")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error while searching for Training: " + e.getMessage());
        } finally {
            CRUD.closeCon();
        }
        return null;
    }

    public static Training assign(Scanner scanner) {
        Training training = null;
        while (training == null) {
            try {
                System.out.println("\n--- Trainings List ---");
                ControllerTraining.list().forEach(System.out::println);
                System.out.println("---------------------");
                int id = Utility.getValidInt(scanner, "Choose one by ID: ");
                training = ControllerTraining.search(id);
                if (training == null) {
                    System.out.println("Not valid, enter a valid value.");
                }
            } catch (Exception e) {
                System.out.println("Error: enter correct format.");
                scanner.nextLine();
            }
        }
        return training;
    }

}
