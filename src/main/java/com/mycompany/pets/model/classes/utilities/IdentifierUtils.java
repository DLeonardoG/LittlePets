package com.mycompany.pets.model.classes.utilities;

import com.mycompany.pets.controller.ControllerType;
import com.mycompany.pets.model.classes.animals.Identifier;
import com.mycompany.pets.model.classes.superclasses.Type;
import java.util.Scanner;

public class IdentifierUtils {

    public static Identifier promptIdentifier(Scanner scanner, int petId) {
        System.out.println("Enter the identifier details (optional):");
        String number = Utility.promptString(scanner, "Identifier Number (leave blank if not applicable)");

        // Si el número está vacío, devolvemos null para indicar que no se proporcionó un identificador
        if (number.isEmpty()) {
            return null;
        }

        // Usamos la función assignType para obtener el tipo de identificador desde la base de datos
        Type type = ControllerType.assign(scanner, "Identifier");

        // Usamos el petId que se pasa como parámetro al método para asignarlo a idPet
        return new Identifier(number, type.getId(), petId);  // Asumimos que 'type.getId()' devuelve el ID del tipo
    }
}
