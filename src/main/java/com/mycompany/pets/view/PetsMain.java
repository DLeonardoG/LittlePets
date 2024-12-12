
package com.mycompany.pets.view;

import com.mycompany.pets.model.classes.utilities.AnimalUtils;
import java.sql.SQLException;
import java.util.Scanner;

public class PetsMain {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int option = 0;

        do {
            System.out.println("\n=== Veterinary Clinic Management ===");
            System.out.println("1. Pets and Owners Management");
            System.out.println("2. Inventory and Supplies Management");
            System.out.println("3. Veterinary Services");
            System.out.println("4. Billing and Finances");
            System.out.println("5. Special Activities");
            System.out.println("6. History");
            System.out.println("7. Reports");
            System.out.println("8. Transfer Pet");
            System.out.println("9. Alerts");
            System.out.println("10. Exit");
            System.out.print("Select an option: ");

            option = scanner.nextInt();

            switch (option) {
                case 1 -> PetsAndOwners(scanner);
                case 2 -> InventoryandSupplies(scanner);
                case 3 -> VeterinaryServices(scanner);
                case 4 -> Billing(scanner);
                case 5 -> SpecialActivities(scanner);
                case 6 -> History(scanner);
                case 7 -> Reports(scanner);
                case 8 -> Transfers(scanner);
                case 9 -> Alerts(scanner);
                case 10 -> System.out.println("Exiting the system....");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (option != 10);
    }

    private static void PetsAndOwners(Scanner scanner) throws SQLException {
        int option;
        do {
            System.out.println("\n--- Pets and Owners Management ---");
            System.out.println("1. Register Pet");
            System.out.println("2. Update Pet Information");
            System.out.println("3. Register Owner");
            System.out.println("4. Update Owner Information");
            System.out.println("5. Return to Main Menu");
            System.out.print("Select an option: ");
            option = scanner.nextInt();

            switch (option) {
                case 1 -> AnimalUtils.animalRegistration(scanner);
                case 2 -> AnimalUtils.updateBasicAnimalInfo(scanner);
                case 3 -> ViewMethod.registrerOwner(scanner);
                case 4 -> ViewMethod.updateOwner(scanner);
                case 5 -> System.out.println("Returning to Main Menu...");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (option != 5);
    }
    
    private static void InventoryandSupplies(Scanner scanner) {
        int option;
        do {
            System.out.println("\n--- Inventory and Supplies Management ---");
            System.out.println("1. View Supplies Inventory");
            System.out.println("2. Add New Product");
            System.out.println("3. Update Stock");
            System.out.println("4. Create Purchase Order");
            System.out.println("5. List Purchase Orders");
            System.out.println("6. Return to Main Menu");
            System.out.print("Select an option: ");
            option = scanner.nextInt();

            switch (option) {
                case 1 -> ViewMethod.viewSupplies(scanner);
                case 2 -> ViewMethod.registrerSupplies(scanner);
                case 3 -> ViewMethod.updateStock(scanner);
                case 4 -> System.out.println("Function: Create Purchase Order");
                case 5 -> System.out.println("Function: List Purchase Orders");
                case 6 -> System.out.println("Returning to Main Menu...");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (option != 6);
    }

    private static void VeterinaryServices(Scanner scanner) {
        int option;
        do {
            System.out.println("\n--- Veterinary Services ---");
            System.out.println("1. Create New Service");
            System.out.println("2. Attend to Service");
            System.out.println("3. Surgery Follow-up");
            System.out.println("4. View Appointment Calendar");
            System.out.println("5. Cancel Service");
            System.out.println("6. Return to Main Menu");
            System.out.print("Select an option: ");
            option = scanner.nextInt();

            switch (option) {
                case 1 -> ViewMethod.registrerServices(scanner);
                case 2 -> ViewMethod.attendService(scanner);
                case 3 -> ViewMethod.viewAppoinmentsFilter(scanner);
                case 4 -> ViewMethod.viewCalendar(scanner);
                case 5 -> ViewMethod.cancelService(scanner);
                case 6 -> System.out.println("Returning to Main Menu...");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (option != 6);
    }

    private static void Billing(Scanner scanner) {
        int option;
        do {
            System.out.println("\n--- Billing and Finance ---");
            System.out.println("1. Generate Invoice");
            System.out.println("2. View Invoice History");
            System.out.println("3. Financial Reports");
            System.out.println("4. Return to Main Menu");
            System.out.print("Select an option: ");
            option = scanner.nextInt();

            switch (option) {
                case 1 -> System.out.println("Function: Generate Invoice");
                case 2 -> System.out.println("Function: Register Transaction");
                case 3 -> System.out.println("Function: View Invoice History");
                case 4 -> System.out.println("Function: Financial Reports");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (option != 4);
    }

    private static void SpecialActivities(Scanner scanner) {
        int option;
        do {
            System.out.println("\n--- Special Activities ---");
            System.out.println("1. Create Event");
            System.out.println("2. Register Event Services");
            System.out.println("3. Generate Event Report");
            System.out.println("4. Frequent Pet Club");
            System.out.println("5. Return to Main Menu");
            System.out.print("Select an option: ");
            option = scanner.nextInt();

            switch (option) {
                case 1 -> System.out.println("Function: Adoption Event Management");
                case 2 -> System.out.println("Function: Vaccination/Spay-Neuter Events");
                case 3 -> System.out.println("Function: Frequent Pet Club");
                case 4 -> System.out.println("Returning to Main Menu...");
                case 5 -> System.out.println("Returning to Main Menu...");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (option != 5);
    }

    private static void History(Scanner scanner) {
        int option;
        do {
            System.out.println("\n--- History ---");
            System.out.println("1. View Vaccination History"); // toca revisar esto porque no se que es
            System.out.println("2. View Pet History");
            System.out.println("3. View Pet Medical History");
            System.out.println("4. View Owner History"); //aqui agregar lo de los points que tiene el owner
            System.out.println("5. Return to Main Menu");
            System.out.print("Select an option: ");
            option = scanner.nextInt();

            switch (option) {
                case 1 -> ViewMethod.viewVaccines(scanner);
                case 2 -> System.out.println("Function: Update Pet Information");
                case 3 -> System.out.println("Function: Register Owner");
                case 4 -> System.out.println("Returning to Main Menu...");
                case 5 -> System.out.println("Returning to Main Menu...");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (option != 5);
    }

    private static void Reports(Scanner scanner) {
        int option;
        do {
            System.out.println("\n--- Reports ---");
            System.out.println("1. Pets Attended");
            System.out.println("2. Most Requested Services");
            System.out.println("3. Employee Performance");
            System.out.println("4. Billing Report");
            System.out.println("5. Supplies Usage Report");
            System.out.println("6. Return to Main Menu");
            System.out.print("Select an option: ");
            option = scanner.nextInt();

            switch (option) {
                case 1 -> System.out.println("Function: Pets Attended");
                case 2 -> System.out.println("Function: Most Requested Services");
                case 3 -> System.out.println("Function: Employee Performance");
                case 4 -> System.out.println("Function: Billing Report");
                case 5 -> System.out.println("Function: Supplies Usage Report");
                case 6 -> System.out.println("Returning to Main Menu...");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (option != 6);
    }

    private static void Transfers(Scanner scanner) {
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
            option = scanner.nextInt();

            switch (option) {
                case 1 -> System.out.println("Function: Change Animal Status");
                case 2 -> System.out.println("Function: Animal Adoption Process");
                case 3 -> System.out.println("Function: Sell Pet");
                case 4 -> System.out.println("Function: Create Control Visit");
                case 5 -> System.out.println("Function: Update Control Visit");
                case 6 -> System.out.println("Function: View Pets for Adoption");
                case 7 -> System.out.println("Function: View Pets for Sale");
                case 8 -> System.out.println("Function: Generate Adoption Contract");
                case 9 -> System.out.println("Returning to Main Menu...");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (option != 9);
    }

    private static void Alerts(Scanner scanner) {
        int option;
        do {
            System.out.println("\n--- Alerts ---");
            System.out.println("1. Low Inventory Alerts");
            System.out.println("2. Outdate Inventory Alerts");
            System.out.println("3. Vaccination Alerts");
            System.out.println("4. Deworming Alerts");
            System.out.println("5. Return to Main Menu");
            System.out.print("Select an option: ");
            option = scanner.nextInt();

            switch (option) {
                case 1 -> System.out.println("Function: Pet Health Alerts");
                case 2 -> System.out.println("Function: Pet Activity Alerts");
                case 3 -> System.out.println("Function: Inventory Alerts");
                case 4 -> System.out.println("Function: Report Alerts");
                case 5 -> System.out.println("Returning to Main Menu...");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (option != 5);
    }


}
