/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pets.controller.animal;

import com.mycompany.pets.controller.people.ControllerOwner;
import com.mycompany.pets.model.classes.animals.Bird;
import com.mycompany.pets.model.classes.animals.Cat;
import com.mycompany.pets.model.classes.animals.Characteristic;
import com.mycompany.pets.model.classes.animals.Dog;
import com.mycompany.pets.model.classes.animals.Identifier;
import com.mycompany.pets.model.classes.people.Owner;
import com.mycompany.pets.model.classes.superclasses.Animal;
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
public class ControllerAnimal {

    public static boolean insertAnimal(Animal animal) {
        try {
            CRUD.setConnection(DBConnection.connectionDB());
            CRUD.setAutoCommitDB(false);

            // Paso 1: Insertar información básica del animal
            int animalID = insertBasicAnimalData(animal);

            // Paso 2: Insertar características específicas del animal
            insertAnimalCharacteristics(animalID, animal.getCharacteristics());

            // Paso 3: Insertar identificador (si aplica)
            if (animal.getIdentifier() != null) {
                insertAnimalIdentifier(animalID, animal.getIdentifier());
            }

            CRUD.commitDB(); // Confirmar transacción
            return true;

        } catch (SQLException e) {
            CRUD.rollbackDB(); // Revertir transacción en caso de error
            System.out.println("Error al insertar el animal: " + e.getMessage());
            return false;
        } finally {
            CRUD.closeCon(); // Cerrar conexión
        }
    }

    // Metodo para insertar información basica del animal a la base de datos (recibe un animal)
    private static int insertBasicAnimalData(Animal animal) throws SQLException {
        String insertAnimalSQL = """
            INSERT INTO Pets (name, dateBirth, sex, weight, conditions, allergies, isAvailable, urlPhoto, IDTypeSpecies, IDOwner)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        String conditionsValue = (animal.getConditions() != null && !animal.getConditions().isEmpty())
                ? animal.getConditions()
                : "";
        String allergiesValue = (animal.getAllergies() != null && !animal.getAllergies().isEmpty())
                ? animal.getAllergies()
                : "";

        List<Object> animalParams = List.of(
                animal.getName(),
                animal.getBirthDate(),
                animal.getSex(),
                animal.getWeight(),
                conditionsValue,
                allergiesValue,
                animal.getIsAvailable(),
                animal.getUrlPhoto(),
                animal.getIDSpecie(),
                animal.getOwner().getId()
        );

        if (CRUD.insertDB(insertAnimalSQL, animalParams)) {
            ResultSet rs = CRUD.consultDB("SELECT LAST_INSERT_ID()", List.of());
            if (rs != null && rs.next()) {
                return rs.getInt(1); // Retorna el ID generado
            }
        }
        throw new SQLException("Error al insertar datos básicos del animal.");
    }

    // Método para insertar las caracteristicas del animal en la base de datos (recibe el id de animal y la lista de caracteristicas) 
    private static void insertAnimalCharacteristics(int animalID, List<Characteristic> characteristics) throws SQLException {
        String insertCharacteristicSQL = """
            INSERT INTO PetsFeaturesPets (IDPet, IDFeaturePet, value)
            VALUES (?, ?, ?)
        """;

        for (Characteristic characteristic : characteristics) {
            int featureID = getFeatureID(characteristic.getName());
            List<Object> params = List.of(animalID, featureID, characteristic.getValue());
            if (!CRUD.insertDB(insertCharacteristicSQL, params)) {
                throw new SQLException("Error al insertar característica: " + characteristic.getName());
            }
        }
    }

    // Metodo para obtener el ID de una caracteristica (recibe el nombre de la caracteristica)
    private static int getFeatureID(String featureName) throws SQLException {
        String findFeatureSQL = "SELECT IDFeaturePet FROM FeaturesPets WHERE feature = ?";
        ResultSet rs = CRUD.consultDB(findFeatureSQL, List.of(featureName));
        if (rs != null && rs.next()) {
            return rs.getInt("IDFeaturePet");
        }
        throw new SQLException("Característica no encontrada: " + featureName);
    }

    // Método para insertar la identificación del animal a la base de datos (recibe el id del animal y objeto de identificador)
    private static void insertAnimalIdentifier(int animalID, Identifier identifier) throws SQLException {
        String insertIdentifierSQL = """
            INSERT INTO Identifiers (number, IDPet, IDTypeIdentifier)
            VALUES (?, ?, ?)
        """;

        List<Object> params = List.of(
                identifier.getNumber(),
                animalID,
                identifier.getIdTypeIdentifier()
        );

        if (!CRUD.insertDB(insertIdentifierSQL, params)) {
            throw new SQLException("Error al insertar identificador del animal.");
        }
    }
    
    public static Animal search(int id) {
    CRUD.setConnection(DBConnection.connectionDB());
    String query = """
                   SELECT 
                       IDPet, name, dateBirth, sex, weight, conditions, allergies, 
                       isAvailable, urlPhoto, IDTypeSpecies, IDOwner
                   FROM 
                       Pets
                   WHERE 
                       IDPet = ?""";
    List<Object> parameters = new ArrayList<>();
    parameters.add(id);

    try {
        ResultSet rs = CRUD.consultDB(query, parameters);
        if (rs != null && rs.next()) {
            // Obtener datos básicos del animal
            int animalId = rs.getInt("IDPet");
            int speciesId = rs.getInt("IDTypeSpecies");
            Owner owner = ControllerOwner.search(rs.getInt("IDOwner"));

            // Obtener el identificador del animal
            Identifier identifier = getIdentifierForAnimal(animalId);

            // Crear el animal utilizando la especie y el identificador
            Animal animal = createAnimalBySpeciesId(speciesId, rs, owner, identifier);

            // Asignar características al animal
            animal.setCharacteristics(getCharacteristicsForAnimal(animalId));

            return animal;
        }
    } catch (SQLException e) {
        System.out.println("Error while searching for Animal: " + e.getMessage());
    } finally {
        CRUD.closeCon();
    }
    return null;
}


    // Method to list all animals
    public static List<Animal> listAnimals() {
        CRUD.setConnection(DBConnection.connectionDB());
        List<Animal> animals = new ArrayList<>();
        String query = "SELECT * FROM Pets";
        List<Object> parameters = new ArrayList<>();

        try {
            ResultSet rs = CRUD.consultDB(query, parameters);
            while (rs != null && rs.next()) {
                int animalId = rs.getInt("IDPet");
                int speciesId = rs.getInt("IDTypeSpecies");
                Owner owner = ControllerOwner
                        .search(rs.getInt("IDOwner"));

                // Get the identifier for the animal
                Identifier identifier = getIdentifierForAnimal(animalId);

                // Create the animal with the identifier
                Animal animal = createAnimalBySpeciesId(speciesId, rs, owner, identifier);

                // Assign characteristics to the animal
                animal.setCharacteristics(getCharacteristicsForAnimal(animalId));

                // Add the animal to the list
                animals.add(animal);
            }
        } catch (Exception e) { // Changed SQLException to Exception
            System.out.println("Error listing animals: " + e.getMessage());
        } finally {
            CRUD.closeCon();
        }

        return animals;
    }

    // Crear animal según el tipo de especie
    public static Animal createAnimalBySpeciesId(int speciesId, ResultSet rs, Owner owner, Identifier identifier) throws SQLException {
        switch (speciesId) {
            case 1: // Perro
                return new Dog(
                        rs.getInt("IDPet"),
                        rs.getString("name"),
                        rs.getString("dateBirth"),
                        rs.getString("sex"),
                        rs.getDouble("weight"),
                        rs.getString("conditions"),
                        rs.getString("allergies"),
                        rs.getString("isAvailable"),
                        owner,
                        identifier // Se pasa el identificador al crear el animal
                );
            case 2: // Gato
                return new Cat(
                        rs.getInt("IDPet"),
                        rs.getString("name"),
                        rs.getString("dateBirth"),
                        rs.getString("sex"),
                        rs.getDouble("weight"),
                        rs.getString("conditions"),
                        rs.getString("allergies"),
                        rs.getString("isAvailable"),
                        owner,
                        identifier // Se pasa el identificador al crear el animal
                );
            case 3: // Pájaro
                return new Bird(
                        rs.getInt("IDPet"),
                        rs.getString("name"),
                        rs.getString("dateBirth"),
                        rs.getString("sex"),
                        rs.getDouble("weight"),
                        rs.getString("conditions"),
                        rs.getString("allergies"),
                        rs.getString("isAvailable"),
                        owner,
                        identifier // Se pasa el identificador al crear el animal
                );
            default:
                throw new IllegalArgumentException("ID de especie desconocido: " + speciesId);
        }
    }

    // Method to get characteristics for an animal
    public static List<Characteristic> getCharacteristicsForAnimal(int animalId) {
        List<Characteristic> characteristics = new ArrayList<>();
        String query = "SELECT f.feature, pf.value FROM FeaturesPets f JOIN PetsFeaturesPets pf ON f.IDFeaturePet = pf.IDFeaturePet WHERE pf.IDPet = ?";

        try {
            CRUD.setConnection(DBConnection.connectionDB());
            List<Object> parameters = new ArrayList<>();
            parameters.add(animalId);
            ResultSet rs = CRUD.consultDB(query, parameters);

            while (rs != null && rs.next()) {
                String featureName = rs.getString("feature");
                String featureValue = rs.getString("value");
                characteristics.add(new Characteristic(featureName, featureValue));
            }
        } catch (Exception e) { // Changed SQLException to Exception
            System.out.println("Error getting characteristics: " + e.getMessage());
        } finally {
            CRUD.closeCon();
        }

        return characteristics;
    }

    // Method to get the identifier for an animal
    public static Identifier getIdentifierForAnimal(int animalId) {
        String query = "SELECT number, IDTypeIdentifier FROM Identifiers WHERE IDPet = ?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(animalId);

        try {
            CRUD.setConnection(DBConnection.connectionDB());
            ResultSet rs = CRUD.consultDB(query, parameters);

            if (rs != null && rs.next()) {
                String identifierNumber = rs.getString("number");
                int identifierTypeId = rs.getInt("IDTypeIdentifier");
                return new Identifier(identifierNumber, identifierTypeId, animalId);
            }
        } catch (Exception e) { // Changed SQLException to Exception
            System.out.println("Error getting identifier: " + e.getMessage());
        } finally {
            CRUD.closeCon();
        }

        return null;
    }

    public static boolean updateAnimal(Animal animal) {
        CRUD.setConnection(DBConnection.connectionDB());
        String updateSQL = """
        UPDATE Pets
        SET name = ?, dateBirth = ?, sex = ?, weight = ?, conditions = ?, allergies = ?, isAvailable = ?, urlPhoto = ?, IDTypeSpecies = ?, IDOwner = ?
        WHERE IDPet = ?
    """;

        String conditionsValue = (animal.getConditions() != null && !animal.getConditions().isEmpty())
                ? animal.getConditions()
                : "";
        String allergiesValue = (animal.getAllergies() != null && !animal.getAllergies().isEmpty())
                ? animal.getAllergies()
                : "";

        List<Object> parameters = new ArrayList<>();
        parameters.add(animal.getName());
        parameters.add(animal.getBirthDate());
        parameters.add(animal.getSex());
        parameters.add(animal.getWeight());
        parameters.add(conditionsValue);
        parameters.add(allergiesValue);
        parameters.add(animal.getIsAvailable());
        parameters.add(animal.getUrlPhoto());
        parameters.add(animal.getIDSpecie());
        parameters.add(animal.getOwner().getId());
        parameters.add(animal.getId()); // El ID del animal para identificar la fila a actualizar

        try {
            if (CRUD.setAutoCommitDB(false)) {
                boolean success = CRUD.updateDB(updateSQL, parameters);
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
            System.out.println("Error al actualizar la información del animal: " + e.getMessage());
            return false;
        } finally {
            CRUD.closeCon();
        }
    }

// Método para actualizar las características del animal en la base de datos
    public static boolean updateAnimalCharacteristics(int animalID, List<Characteristic> characteristics) {
        CRUD.setConnection(DBConnection.connectionDB());

        // SQL para eliminar las características anteriores
        String deleteOldCharacteristicsSQL = "DELETE FROM PetsFeaturesPets WHERE IDPet = ?";
        List<Object> deleteParams = List.of(animalID);

        try {
            if (CRUD.setAutoCommitDB(false)) { // Iniciar transacción
                // Eliminar características anteriores
                boolean successDelete = CRUD.deleteDB(deleteOldCharacteristicsSQL, deleteParams);
                if (!successDelete) {
                    CRUD.rollbackDB(); // Revertir en caso de error
                    return false;
                }

                // Insertar nuevas características
                insertAnimalCharacteristics(animalID, characteristics);

                CRUD.commitDB(); // Confirmar transacción
                return true; // Retornar true si la actualización fue exitosa
            } else {
                return false; // Retornar false si no se pudo establecer auto-commit
            }
        } catch (Exception e) {
            CRUD.rollbackDB(); // Revertir transacción en caso de excepción
            System.out.println("Error al actualizar características del animal: " + e.getMessage());
            return false; // Retornar false en caso de error
        } finally {
            CRUD.closeCon(); // Cerrar la conexión
        }
    }

// Método para actualizar la identificación del animal en la base de datos
    private static boolean updateAnimalIdentifier(int animalID, Identifier identifier) {
        CRUD.setConnection(DBConnection.connectionDB());

        // SQL para actualizar la identificación del animal
        String updateIdentifierSQL = """
        UPDATE Identifiers
        SET number = ?, IDTypeIdentifier = ?
        WHERE IDPet = ?
    """;

        List<Object> params = List.of(
                identifier.getNumber(),
                identifier.getIdTypeIdentifier(),
                animalID
        );

        try {
            if (CRUD.setAutoCommitDB(false)) { // Iniciar transacción
                boolean successUpdate = CRUD.updateDB(updateIdentifierSQL, params);
                if (successUpdate) {
                    CRUD.commitDB(); // Confirmar transacción
                    return true; // Retornar true si la actualización fue exitosa
                } else {
                    CRUD.rollbackDB(); // Revertir transacción en caso de error
                    return false;
                }
            } else {
                return false; // Retornar false si no se pudo establecer auto-commit
            }
        } catch (Exception e) {
            CRUD.rollbackDB(); // Revertir transacción en caso de error
            System.out.println("Error al actualizar identificador del animal: " + e.getMessage());
            return false; // Retornar false en caso de error
        } finally {
            CRUD.closeCon(); // Cerrar la conexión
        }
    }

}
