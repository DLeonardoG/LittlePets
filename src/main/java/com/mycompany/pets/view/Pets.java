package com.mycompany.pets.view;

import com.mycompany.pets.controller.people.ControllerEmployee;
import com.mycompany.pets.controller.people.ControllerManufacturer;
import com.mycompany.pets.controller.people.ControllerOwner;
import com.mycompany.pets.controller.services.ControllerAppointment;
import com.mycompany.pets.controller.services.ControllerConsultation;
import com.mycompany.pets.controller.services.ControllerService;
import com.mycompany.pets.controller.supplies.ControllerMedicine;
import com.mycompany.pets.controller.supplies.ControllerSupply;
import com.mycompany.pets.controller.supplies.ControllerVaccine;
import com.mycompany.pets.model.classes.factory.PersonFactory;
import com.mycompany.pets.model.classes.factory.ServiceFactory;
import com.mycompany.pets.model.classes.factory.SupplyFactory;
import com.mycompany.pets.model.classes.people.Employee;
import com.mycompany.pets.model.classes.people.Owner;
import com.mycompany.pets.model.classes.services.Consultation;
import com.mycompany.pets.model.classes.superclasses.Service;
import com.mycompany.pets.model.classes.superclasses.Supply;
import com.mycompany.pets.model.classes.supplies.Medicine;
import com.mycompany.pets.model.classes.supplies.Vaccine;
import java.util.Scanner;

public class Pets {

    public static void main(String[] args) {
//        String type = "Supplies";
//        ControllerType.listTypes(type).forEach(System.out::println);
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("A number");
//        int num = scanner.nextInt();
//        Type t1 = ControllerType.searchType(num, type);
//        System.out.println(t1);
//        ControllerEmployee.listEmployees().forEach(System.out::println);
//        for (Status status : Status.values()) {
//            System.out.println(status);
//        }
//        System.out.println(Service.getMaxServiceId());

        Scanner scanner = new Scanner(System.in);
//        System.out.println("A number");
//        int num = scanner.nextInt();
//        Employee e1 = ControllerEmployee.searchEmployee(1);
//        System.out.println(e1);
//        ControllerService.addService(date, pet, e1, typeService)

//            // Registrar nuevos servicios sencillos de la clase padre
//        Service service = Service.registerService(scanner);
//        ControllerService.addService(service);
//ControllerType.assignType(scanner, "Service");
//ControllerService.listServices().forEach(System.out::println);
        // Consultar consultas como appointments
//        ControllerConsultation.listConsultations().forEach(e->System.out.println(e.getDetailsAppointment()));
// ******************************************************       
// FORMA DE ACTUALIZAR
//        Employee NewEmpl = ControllerEmployee.assign(scanner);
//        System.out.println(NewEmpl.getDetails());
//        Employee person = PersonFactory.registerEmployee(scanner);
//        person.setId(NewEmpl.getId());
//        ControllerEmployee.update(person);
// ******************************************************       
// ******************************************************       
// FORMA DE CREAR
//        Owner owner = PersonFactory.registerOwner(scanner);
//        ControllerOwner.add(owner); 
// ******************************************************       
// ******************************************************     
// FORMA DE LISTAR
//        ControllerManufacturer.list().forEach(System.out::println);
// ******************************************************       
// ****************************************************** 
// FORMA DE ESCOGER UNO SOLO
//        Employee employee = ControllerEmployee.assign(scanner);
//        System.out.println(employee);
// ******************************************************       
// ****************************************************** 
// FORMA DE ESCOGER UNO POR EL ID DE LA DB
//        Owner owner = ControllerOwner.search(1);
//        System.out.println(owner);
// ******************************************************       
//
//        Medicine supply = SupplyFactory.registerMedicine(scanner);
//        ControllerMedicine.add(supply);
// **********************************************
// AÑADIR UN NUEVO SUPPLIES
//        Vaccine supply = SupplyFactory.registerVaccine(scanner);
//        ControllerSupply.add(supply);
//        ControllerVaccine.add(supply);
// **********************************************

//        Supply supply = SupplyFactory.registerSupply(scanner);
//        ControllerSupply.add(supply);
// **********************************************
// ACTUALIZAR INFO EN SUPPLIES
//            System.out.println("Update Vaccine");
//            Vaccine vaccine1 = ControllerVaccine.assign(scanner);
//            System.out.println("Enter the new info");
//            Vaccine vaccine2 = SupplyFactory.registerVaccine(scanner);
//            vaccine2.setIdSupply(vaccine1.getIdSupply());
//            vaccine2.setIdVaccine(vaccine1.getIdVaccine());
//            
//            ControllerVaccine.update(vaccine2);
// **********************************************
// REGISTRAR UNA CITA
//        Consultation ser = ServiceFactory.registerAppointment(scanner);
//        ControllerService.add(ser);
//        ControllerAppointment.add(ser);

//       // TERMINAR CONSULTAAA 
//        Consultation ser3 = ServiceFactory.registerConsultation(scanner);
//        ControllerConsultation.add(ser3);
//        ControllerService.update(ser3);
//        
    }
}
