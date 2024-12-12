package com.mycompany.pets.model.classes.utilities;

import com.mycompany.pets.model.classes.enumsandinterfaces.Status;
import com.mycompany.pets.model.persistence.CRUD;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public abstract class Utility {

    public static boolean askYesNo(Scanner scanner, String question) {
        int response;
        while (true) {
            System.out.println(question + "\n1. Yes, 2. No: ");
            try {
                response = Integer.parseInt(scanner.nextLine().trim());
                if (response == 1) {
                    return true;
                } else if (response == 2) {
                    return false;
                } else {
                    System.out.println("Invalid input. Please enter '1' for Yes or '2' for No.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number ('1' or '2').");
            }
        }
    }

    
    
    public static int getIntFromUser(Scanner scanner) {
        while (true) {
            try {
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("No es un número entero válido. Intenta nuevamente:");
            }
        }
    }

    public static int getValidInt(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("It's not a valid integer. Please try again:");
            }
        }
    }

    public static double getValidDouble(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                String input = scanner.nextLine();
                double number = Double.parseDouble(input);
                if (number > 0) {
                    return number;
                } else {
                    System.out.println("The number must be greater than zero. Please try again:");
                }
            } catch (NumberFormatException e) {
                System.out.println("It's not a valid number. Please try again:");
            }
        }
    }

    public static String getValidInput(Scanner scanner, String prompt) {
        String input;
        while (true) {
            System.out.println(prompt);
            input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("La entrada no puede estar vacía. Intenta nuevamente.");
        }
    }

    public static <T, R extends Comparable<R>> R getMaxValue(List<T> list, Function<T, R> mapper) {
        return list.stream()
                .map(mapper)
                .max(Comparator.naturalOrder())
                .orElseThrow(() -> new IllegalArgumentException("List is empty or no maximum value found!"));
    }
    public static int getMaxID(String typeOf) {
        return CRUD.queryInt("SELECT COALESCE(MAX(IDService), 0) + 1 FROM "+typeOf,null);
    }

    public static LocalDate getValidDate(Scanner scanner, String message) {
        LocalDate date = null;
        LocalDate today = LocalDate.now();
        LocalDate oneWeekLater = today.plusWeeks(1); // Calcula la fecha que es una semana después de hoy

        while (date == null) {
            System.out.print(message + " ");
            try {
                String input = scanner.nextLine();
                date = LocalDate.parse(input);
                if (date.isBefore(today)) {
                    System.out.println("The date cannot be before today.");
                    date = null;
                } else if (date.isAfter(oneWeekLater)) {
                    System.out.println("The date cannot be more than one week after today.");
                    date = null;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
        return date;
    }

    public static Status statusValid(Status status) {
        if (status == null) {
            status = Status.SCHEDULED;  
        } else {
            status = status;
        }
        return status;
    }

//  public static Status intoStatus(String status) {
//            try {
//                
//                
//                Status.status;
//                if (response == 1) {
//                    return true; 
//                } else if (response == 2) {
//                    return false;
//                } else {
//                    System.out.println("Invalid input. Please enter '1' for Yes or '2' for No.");
//                }
//            } catch (NumberFormatException e) {
//                System.out.println("Invalid input. Please enter a valid number ('1' or '2').");
//            }
//        }
//    }
    
        // Utility methods for user input
    public static String promptString(Scanner scanner, String prompt) {
        System.out.print(prompt + ": ");
        String input = scanner.nextLine();

        // Si el scanner ha leído un salto de línea no deseado, lo consumimos
        if (input.isEmpty()) {
            input = scanner.nextLine(); // Consume el salto de línea residual
        }

        return input;
    }

    public static int promptInt(Scanner scanner, String prompt) {
        int value = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print(prompt + ": ");
                value = Integer.parseInt(scanner.nextLine());  // Intenta parsear el valor ingresado como un entero
                validInput = true;  // Si no ocurre excepción, el valor es válido
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingresa un número entero válido.");
            }
        }

        return value;
    }

    public static double promptDouble(Scanner scanner, String prompt) {
        double value = 0.0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print(prompt + ": ");
                value = Double.parseDouble(scanner.nextLine());  // Intenta parsear el valor ingresado como un double
                validInput = true;  // Si no ocurre excepción, el valor es válido
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingresa un número decimal válido.");
            }
        }

        return value;
    }

    public static String promptOption(Scanner scanner, String prompt, List<String> validOptions) {
        String input = "";
        boolean valid = false;

        // Mostrar la pregunta
        System.out.print(prompt + " (" + String.join("/", validOptions) + "): ");

        // Mientras no sea una opción válida, seguir pidiendo la entrada
        while (!valid) {
            input = scanner.nextLine().toUpperCase(); // Convertimos la entrada a mayúsculas para evitar errores de caso
            if (validOptions.contains(input)) {
                valid = true; // Si la opción es válida, salimos del bucle
            } else {
                System.out.println("Invalid option. Please enter one of the following: " + String.join("/", validOptions));
            }
        }

        return input.toUpperCase();
    }
}
