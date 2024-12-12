/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pets.controller.people.additional;

import com.mycompany.pets.model.classes.animals.Pet;
import com.mycompany.pets.model.persistence.DBConnection;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class TransfersT {

    private static Scanner scanner = new Scanner(System.in);

    // Change Animal Status
    public static boolean changeAnimalStatusInDB(int petId, String newStatus) {
        String sql = "UPDATE Pets SET status = ? WHERE id = ?";
        try (Connection conn = DBConnection.connectionDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, petId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            System.out.println("Error changing animal status: " + ex.getMessage());
            return false;
        }
    }

    // Animal Adoption Process
    public static boolean processAnimalAdoption(int petId, int adopterId) {
        String sql = "UPDATE Pets SET status = 'Adopted', adopter_id = ? WHERE id = ?";
        try (Connection conn = DBConnection.connectionDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, adopterId);
            ps.setInt(2, petId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            System.out.println("Error processing animal adoption: " + ex.getMessage());
            return false;
        }
    }

    // Sell Pet
    public static boolean sellPet(int petId, double price) {
        String sql = "UPDATE Pets SET status = 'Sold', sale_price = ? WHERE id = ?";
        try (Connection conn = DBConnection.connectionDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, price);
            ps.setInt(2, petId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            System.out.println("Error selling pet: " + ex.getMessage());
            return false;
        }
    }

    // Create Control Visit
    public static boolean createControlVisit(int petId, String visitDate) {
        String sql = "INSERT INTO ControlVisits (pet_id, visit_date) VALUES (?, ?)";
        try (Connection conn = DBConnection.connectionDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, petId);
            ps.setString(2, visitDate);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            System.out.println("Error creating control visit: " + ex.getMessage());
            return false;
        }
    }

    // Update Control Visit
    public static boolean updateControlVisit(int visitId, String newVisitDate) {
        String sql = "UPDATE ControlVisits SET visit_date = ? WHERE id = ?";
        try (Connection conn = DBConnection.connectionDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newVisitDate);
            ps.setInt(2, visitId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            System.out.println("Error updating control visit: " + ex.getMessage());
            return false;
        }
    }

    // View Pets for Adoption
    public static List<Pet> viewPetsForAdoption() {
        List<Pet> petsForAdoption = new ArrayList<>();
        String sql = "SELECT * FROM Pets WHERE status = 'Available for Adoption'";
        try (Connection conn = DBConnection.connectionDB();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Pet pet = new Pet(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("status")
                );
                petsForAdoption.add(pet);
            }
        } catch (SQLException ex) {
            System.out.println("Error viewing pets for adoption: " + ex.getMessage());
        }
        return petsForAdoption;
    }

    // View Pets for Sale
    public static List<Pet> viewPetsForSale() {
        List<Pet> petsForSale = new ArrayList<>();
        String sql = "SELECT * FROM Pets WHERE status = 'Available for Sale'";
        try (Connection conn = DBConnection.connectionDB();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Pet pet = new Pet(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("status"),
                        rs.getDouble("sale_price")
                );
                petsForSale.add(pet);
            }
        } catch (SQLException ex) {
            System.out.println("Error viewing pets for sale: " + ex.getMessage());
        }
        return petsForSale;
    }

    // Generate Adoption Contract
    public static boolean generateAdoptionContract(int petId, int adopterId) {
        String sql = "INSERT INTO AdoptionContracts (pet_id, adopter_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.connectionDB();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, petId);
            ps.setInt(2, adopterId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            System.out.println("Error generating adoption contract: " + ex.getMessage());
            return false;
        }
    }

    // Method to handle user inputs and transfer control
    public static void handleTransfers() {
        int option;
        do {
            System.out.println("\n--- Transfers ---");
            System.out.println("1. Change Animal Status");
            System.out.println("2. Animal Adoption Process");
            System.out.println("3. Sell Pet");
            System.out.println("4. Create Control Visit");
            System.out.println("5. Update Control Visit");
            System.out.println("6. View Pets for Adoption");
            System.out.println("7. View Pets for Sale");
            System.out.println("8. Generate Adoption Contract");
            System.out.println("9. Return to Main Menu");
            System.out.print("Select an option: ");
            option = scanner.nextInt();  // Assuming Utility.getIntFromUser(scanner) works similarly.

            switch (option) {
                case 1 -> {
                    System.out.print("Enter the pet ID: ");
                    int petId = scanner.nextInt();
                    System.out.print("Enter the new status (Available, Adopted, Sold, etc.): ");
                    String newStatus = scanner.next();
                    if (changeAnimalStatusInDB(petId, newStatus)) {
                        System.out.println("Animal status updated successfully.");
                    }
                }
                case 2 -> {
                    System.out.print("Enter the pet ID for adoption: ");
                    int petId = scanner.nextInt();
                    System.out.print("Enter the adopter's ID: ");
                    int adopterId = scanner.nextInt();
                    if (processAnimalAdoption(petId, adopterId)) {
                        System.out.println("Animal adoption processed successfully.");
                    }
                }
                case 3 -> {
                    System.out.print("Enter the pet ID to sell: ");
                    int petId = scanner.nextInt();
                    System.out.print("Enter the sale price: ");
                    double price = scanner.nextDouble();
                    if (sellPet(petId, price)) {
                        System.out.println("Pet sold successfully.");
                    }
                }
                case 4 -> {
                    System.out.print("Enter the pet ID for the control visit: ");
                    int petId = scanner.nextInt();
                    System.out.print("Enter the date for the control visit (yyyy-mm-dd): ");
                    String visitDate = scanner.next();
                    if (createControlVisit(petId, visitDate)) {
                        System.out.println("Control visit created successfully.");
                    }
                }
                case 5 -> {
                    System.out.print("Enter the control visit ID to update: ");
                    int visitId = scanner.nextInt();
                    System.out.print("Enter the new date for the control visit (yyyy-mm-dd): ");
                    String newVisitDate = scanner.next();
                    if (updateControlVisit(visitId, newVisitDate)) {
                        System.out.println("Control visit updated successfully.");
                    }
                }
                case 6 -> {
                    List<Pet> petsForAdoption = viewPetsForAdoption();
                    for (Pet pet : petsForAdoption) {
                        System.out.println(pet);
                    }
                }
                case 7 -> {
                    List<Pet> petsForSale = viewPetsForSale();
                    for (Pet pet : petsForSale) {
                        System.out.println(pet);
                    }
                }
                case 8 -> {
                    System.out.print("Enter the pet ID for adoption contract: ");
                    int petId = scanner.nextInt();
                    System.out.print("Enter the adopter's ID: ");
                    int adopterId = scanner.nextInt();
                    if (generateAdoptionContract(petId, adopterId)) {
                        System.out.println("Adoption contract generated successfully.");
                    }
                }
                case 9 -> System.out.println("Returning to Main Menu...");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (option != 9);
    }
}

