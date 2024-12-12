/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pets.model.classes.utilities;

import com.mycompany.pets.controller.ControllerType;
import com.mycompany.pets.controller.animal.ControllerAnimal;
import com.mycompany.pets.controller.people.ControllerOwner;
import com.mycompany.pets.model.classes.animals.Bird;
import com.mycompany.pets.model.classes.animals.Cat;
import com.mycompany.pets.model.classes.animals.Characteristic;
import com.mycompany.pets.model.classes.animals.Dog;
import com.mycompany.pets.model.classes.superclasses.Animal;
import com.mycompany.pets.model.classes.superclasses.Type;
import static com.mycompany.pets.model.classes.utilities.Utility.promptString;
import java.util.List;
import java.util.Scanner;

public class AnimalUtils {

    public static void animalRegistration(Scanner scanner) {
        System.out.println("Welcome to the animal registration: ");
        Animal animal = createAnimal(scanner);

        if (animal != null) {
            boolean success = ControllerAnimal.insertAnimal(animal);
            if (success) {
                System.out.println("The animal was registered successfully!");
            } else {
                System.out.println("An error occurred while registering the animal.");
            }
        } else {
            System.out.println("The registration was canceled.");
        }
    }

    public static Animal createAnimal(Scanner scanner) {
        System.out.println("Select the type of animal:");

        // Obtener el tipo de animal desde la base de datos
        Type type = ControllerType.assign(scanner, "Species"); // Usamos el tipo "animal" para obtener los tipos disponibles

        // Aquí, asumiendo que el tipo es un objeto de tipo Animal
        Animal animal = null;

        // Dependiendo del tipo seleccionado, creamos el animal adecuado
        switch (type.getId()) {
            case 1: // Dog
                animal = createDog(scanner);
                break;
            case 2: // Cat
                animal = createCat(scanner);
                break;
            case 3: // Bird
                animal = createBird(scanner);
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }

        return animal;
    }

    public static Dog createDog(Scanner scanner) {
        Dog dog = new Dog(
                0,
                Utility.promptString(scanner, "Name"),
                UtilityTime.getValidBirthDate(scanner),
                Utility.promptOption(scanner, "Enter the sex of the dog", List.of("MALE", "FEMALE", "HERMAPHRODITE")),
                Utility.promptDouble(scanner, "Weight"),
                Utility.promptString(scanner, "Medical Conditions"),
                Utility.promptString(scanner, "Allergies"),
                Utility.promptOption(scanner, "Is the dog available?", List.of("ADOPTION", "FOR_SALE", "NONE")),
                ControllerOwner.assign(scanner),
                IdentifierUtils.promptIdentifier(scanner, 0)
        );
        // Llamamos al nuevo método para llenar las características del perro
        fillAnimalCharacteristics(scanner, dog);
        return dog;
    }

    public static Cat createCat(Scanner scanner) {
        Cat cat = new Cat(
                0,
                Utility.promptString(scanner, "Name"),
                UtilityTime.getValidBirthDate(scanner),
                Utility.promptOption(scanner, "Enter the sex of the cat", List.of("MALE", "FEMALE", "HERMAPHRODITE")),
                Utility.promptDouble(scanner, "Weight"),
                Utility.promptString(scanner, "Medical Conditions"),
                Utility.promptString(scanner, "Allergies"),
                Utility.promptOption(scanner, "Is the cat available?", List.of("ADOPTION", "FOR_SALE", "NONE")),
                ControllerOwner.assign(scanner),
                IdentifierUtils.promptIdentifier(scanner, 0)
        );
        // Llamamos al nuevo método para llenar las características del gato
        fillAnimalCharacteristics(scanner, cat);
        return cat;
    }

    public static Bird createBird(Scanner scanner) {
        Bird bird = new Bird(
                0,
                Utility.promptString(scanner, "Name"),
                UtilityTime.getValidBirthDate(scanner),
                Utility.promptOption(scanner, "Enter the sex of the bird", List.of("MALE", "FEMALE", "HERMAPHRODITE")),
                Utility.promptDouble(scanner, "Weight"),
                Utility.promptString(scanner, "Medical Conditions"),
                Utility.promptString(scanner, "Allergies"),
                Utility.promptOption(scanner, "Is the bird available?", List.of("ADOPTION", "FOR_SALE", "NONE")),
                ControllerOwner.assign(scanner),
                IdentifierUtils.promptIdentifier(scanner, 0)
        );
        // Llamamos al nuevo método para llenar las características del ave
        fillAnimalCharacteristics(scanner, bird);
        return bird;
    }

    public static void fillAnimalCharacteristics(Scanner scanner, Animal animal) {
        // Inicializamos las características específicas del tipo de animal
        animal.initializeCharacteristics();

        // Recorremos las características y pedimos al usuario que ingrese los valores
        for (Characteristic characteristic : animal.getCharacteristics()) {
            String value = promptString(scanner, "Enter " + characteristic.getName());
            characteristic.setValue(value); // Establecemos el valor proporcionado por el usuario
        }
    }

    // Method to display animals and select one
    public static Animal displayAndSelectAnimal() {
        List <Animal> animals;
        try {
            // Retrieve and save the list of animals directly via the Controller
            animals = ControllerAnimal.listAnimals();
        } catch (Exception e) { // Catch any exception to prevent crashes
            System.out.println("Error while retrieving animals: " + e.getMessage());
            return null;
        }

        if (animals == null || animals.isEmpty()) {
            System.out.println("No animals available.");
            return null;
        }

        System.out.println("Animal List:");
        for (int i = 0; i < animals.size(); i++) {
            Animal animal = animals.get(i);
            String ownerName = animal.getOwner() != null ? animal.getOwner().getName() : "No owner";
            System.out.printf("%d. %s (Owner: %s)%n", i + 1, animal.getName(), ownerName);
        }

        Scanner scanner = new Scanner(System.in);
        int selectedIndex = -1;
        while (true) {
            System.out.print("Select the number of the animal you want (0 to cancel): ");
            try {
                selectedIndex = Integer.parseInt(scanner.nextLine());
                if (selectedIndex == 0) {
                    System.out.println("Selection canceled.");
                    return null;
                }
                if (selectedIndex > 0 && selectedIndex <= animals.size()) {
                    break;
                } else {
                    System.out.println("Invalid number. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        Animal selectedAnimal = animals.get(selectedIndex - 1);
        System.out.printf("You have selected: %s (Owner: %s)%n",
                selectedAnimal.getName(),
                selectedAnimal.getOwner() != null ? selectedAnimal.getOwner().getName() : "No owner");

        return selectedAnimal;
    }

// Método para actualizar la información básica del animal
    public static void updateBasicAnimalInfo(Scanner scanner) {
        Animal selectedAnimal = displayAndSelectAnimal();
        if (selectedAnimal == null) {
            return; // Si no se selecciona un animal, cancelamos la operación
        }

        // Usamos los métodos auxiliares para obtener los nuevos valores de los atributos
        String newName = Utility.promptString(scanner, "Enter new name (current: " + selectedAnimal.getName() + ")");
        String newBirthDate = UtilityTime.getValidBirthDate(scanner);
        String newSex = Utility.promptOption(scanner, "Enter new sex (current: " + selectedAnimal.getSex() + ")", List.of("MALE", "FEMALE", "HERMAPHRODITE"));
        double newWeight = Utility.promptDouble(scanner, "Enter new weight (current: " + selectedAnimal.getWeight() + ")");

        // Actualizar los datos básicos del animal
        selectedAnimal.setName(newName.isEmpty() ? selectedAnimal.getName() : newName);
        selectedAnimal.setBirthDate(newBirthDate.isEmpty() ? selectedAnimal.getBirthDate() : newBirthDate);
        selectedAnimal.setSex(newSex.isEmpty() ? selectedAnimal.getSex() : newSex);
        selectedAnimal.setWeight(newWeight != 0 ? newWeight : selectedAnimal.getWeight());

        // Llamar al método de actualización en la base de datos
        if (ControllerAnimal.updateAnimal(selectedAnimal)) {
            System.out.println("Animal's basic information updated successfully!");
        } else {
            System.out.println("Error updating animal's basic information.");
        }
    }

    /*// Método para actualizar las características del animal
public static void updateAnimalCharacteristics() {
    Animal selectedAnimal = displayAndSelectAnimal();
    if (selectedAnimal == null) {
        return; // Si no se selecciona un animal, cancelamos la operación
    }

    Scanner scanner = new Scanner(System.in);
    System.out.println("Current characteristics: ");
    // Mostrar características actuales
    for (Characteristic characteristic : selectedAnimal.getCharacteristics()) {
        System.out.println("- " + characteristic.getName());
    }

    // Pedir al usuario las nuevas características
    System.out.print("Enter new characteristics (separate by commas): ");
    String newCharacteristics = scanner.nextLine();
    List<Characteristic> newCharacteristicList = new ArrayList<>();

    // Suponiendo que las características están separadas por comas
    for (String characteristicName : newCharacteristics.split(",")) {
        newCharacteristicList.add(new Characteristic(characteristicName.trim()));
    }

    // Actualizar características del animal en la base de datos
    if (updateAnimalCharacteristics(selectedAnimal.getId(), newCharacteristicList)) {
        System.out.println("Animal's characteristics updated successfully!");
    } else {
        System.out.println("Error updating animal's characteristics.");
    }
}*/
}
