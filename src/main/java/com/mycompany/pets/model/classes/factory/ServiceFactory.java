package com.mycompany.pets.model.classes.factory;

import com.mycompany.pets.controller.services.ControllerAppointment;
import com.mycompany.pets.controller.services.ControllerConsultation;
import com.mycompany.pets.controller.people.ControllerEmployee;
import com.mycompany.pets.controller.services.ControllerGrooming;
import com.mycompany.pets.controller.services.ControllerService;
import com.mycompany.pets.controller.ControllerType;
import com.mycompany.pets.controller.ControllerTypePrice;
import com.mycompany.pets.model.classes.services.Consultation;
import com.mycompany.pets.model.classes.people.Employee;
import com.mycompany.pets.model.classes.services.Grooming;
import com.mycompany.pets.model.classes.enumsandinterfaces.Status;
import com.mycompany.pets.model.classes.animals.Pet;
import com.mycompany.pets.model.classes.TypePrice;
import com.mycompany.pets.model.classes.services.Training;
import com.mycompany.pets.model.classes.superclasses.Animal;
import com.mycompany.pets.model.classes.superclasses.Service;
import com.mycompany.pets.model.classes.superclasses.Type;
import com.mycompany.pets.model.classes.utilities.AnimalUtils;
import com.mycompany.pets.model.classes.utilities.Utility;
import com.mycompany.pets.model.classes.utilities.UtilityTime;
import java.time.LocalDateTime;
import java.util.Scanner;
import javax.swing.text.Utilities;

public class ServiceFactory {

    public static Service registerService(Scanner scanner, Type type) {
        LocalDateTime appoinment = UtilityTime.getValidatedDate(scanner);
        Animal pet = AnimalUtils.displayAndSelectAnimal();
        Employee employee = ControllerEmployee.assign(scanner);
        int max = Utility.getMaxValue(ControllerService.list(), Service::getIdService) + 1;
        return new Service(max, appoinment, pet, employee, type, Status.SCHEDULED, false);
    }

    public static Consultation registerAppointment(Scanner scanner, Type type) {
        Service baseService = ServiceFactory.registerService(scanner, type);
        int id = Utility.getMaxValue(ControllerAppointment.list(), Consultation::getIdConsultation)+1;
        Type typeR = ControllerType.assign(scanner, "Reason");
        boolean isControl = Utility.askYesNo(scanner, "Is this consultation a control checkup?");
        Consultation consultation = new Consultation.Builder(baseService)
                .setIdConsultation(id)
                .setReason(typeR)
                .setIsControl(isControl)
                .setPrice(50.0)
                .setPaid(false)
                .build();
        return consultation;
    }

    public static Consultation registerConsultation(Scanner scanner) {
        Consultation appointment = ControllerAppointment.assign(scanner);
        String recommendations = Utility.getValidInput(scanner, "Enter recommendations:");
        String diagnostic = Utility.getValidInput(scanner, "Enter diagnostic:");
        String services = Utility.getValidInput(scanner, "Enter services provided:");
        String supplies = Utility.getValidInput(scanner, "Enter supplies used:");
        Consultation newConsultation = new Consultation.Builder(appointment)
                .setRecommendations(recommendations)
                .setDiagnostic(diagnostic)
                .setServices(services)
                .setSupplies(supplies)
                .setStatus(Status.COMPLETED)
                .build();
        return newConsultation;
    }

    public static Grooming registerGrooming(Scanner scanner, Type type) {
        Service baseService = ServiceFactory.registerService(scanner, type);
        TypePrice typeG = ControllerTypePrice.assign(scanner, "Grooming");
        String comments = Utility.getValidInput(scanner, "Enter comments or observations for the grooming:");
        int idGrooming = Utility.getMaxValue(ControllerGrooming.list(), Grooming::getIdGrooming);
        return new Grooming(baseService, idGrooming, typeG, comments);
    }

    public static Training registerTraining(Scanner scanner, Type type) {
        Service baseService = ServiceFactory.registerService(scanner, type);
        String estimatedTime = Utility.getValidInput(scanner, "Enter the estimated time for the training: ");
        String idTraining = Utility.getValidInput(scanner, "Enter the training ID: ");
        TypePrice typeBreed = ControllerTypePrice.assign(scanner, "Breed");
        TypePrice typeBehavior = ControllerTypePrice.assign(scanner, "Behavior");
        double totalPrice = Utility.getValidDouble(scanner, "Enter the total price: ");
        return new Training(baseService, estimatedTime, idTraining, typeBreed, typeBehavior, totalPrice);
    }

}
