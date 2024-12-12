package com.mycompany.pets.view;

import com.mycompany.pets.controller.ControllerType;
import com.mycompany.pets.controller.animal.ControllerAnimal;
import com.mycompany.pets.controller.people.ControllerEmployee;
import com.mycompany.pets.controller.people.ControllerInvoice;
import com.mycompany.pets.controller.people.ControllerOwner;
import com.mycompany.pets.controller.people.ControllerPerson;
import com.mycompany.pets.controller.people.Invoice;
import com.mycompany.pets.controller.people.additional.Consultas;
import com.mycompany.pets.controller.people.additional.EmployeeConsultationCount;
import com.mycompany.pets.controller.people.additional.InvoiceInfo;
import com.mycompany.pets.controller.people.additional.MedicineInventory;
import com.mycompany.pets.controller.people.additional.PetVisitInfo;
import com.mycompany.pets.controller.people.additional.ServiceInfo;
import com.mycompany.pets.controller.services.ControllerAppointment;
import com.mycompany.pets.controller.services.ControllerConsultation;
import com.mycompany.pets.controller.services.ControllerGrooming;
import com.mycompany.pets.controller.services.ControllerService;
import com.mycompany.pets.controller.services.ControllerTraining;
import com.mycompany.pets.controller.supplies.ControllerSupply;
import com.mycompany.pets.controller.supplies.ControllerVaccine;
import com.mycompany.pets.model.classes.enumsandinterfaces.Facturable;
import com.mycompany.pets.model.classes.enumsandinterfaces.Status;
import com.mycompany.pets.model.classes.factory.PersonFactory;
import static com.mycompany.pets.model.classes.factory.PersonFactory.registerPerson;
import com.mycompany.pets.model.classes.factory.ServiceFactory;
import com.mycompany.pets.model.classes.factory.SupplyFactory;
import com.mycompany.pets.model.classes.people.Owner;
import com.mycompany.pets.model.classes.services.Consultation;
import com.mycompany.pets.model.classes.services.Grooming;
import com.mycompany.pets.model.classes.services.Training;
import com.mycompany.pets.model.classes.superclasses.Person;
import com.mycompany.pets.model.classes.superclasses.Service;
import com.mycompany.pets.model.classes.superclasses.Supply;
import com.mycompany.pets.model.classes.superclasses.Type;
import com.mycompany.pets.model.classes.supplies.Medicine;
import com.mycompany.pets.model.classes.supplies.Vaccine;
import com.mycompany.pets.model.classes.utilities.Utility;
import java.util.List;
import java.util.Scanner;

public class ViewMethod {

    public static void registrerOwner(Scanner scanner) {
        Owner owner = PersonFactory.registerOwner(scanner);
        ControllerOwner.add(owner);
    }

    public static void updateOwner(Scanner scanner) {
        Owner oldOwner = ControllerOwner.assign(scanner);
        System.out.println(oldOwner);
        oldOwner.setAdress(Utility.getValidInput(scanner, "Update owner's address:"));
        oldOwner.setSignature(Utility.getValidInput(scanner, "Update owner's signature:"));
        boolean havePoints = Utility.askYesNo(scanner, "Update points? ");
        int points = (havePoints) ? Utility.getValidInt(scanner, "How many more?") : 0;
        if (havePoints) {
            oldOwner.setPoints(points);
        }
        oldOwner.setSignature(Utility.getValidInput(scanner, "Update owner's signature:"));
        oldOwner.setSubscripcion(Utility.askYesNo(scanner, "Is the owner subscribed?:"));
        oldOwner.setCUFE(Utility.getValidInput(scanner, "Update CUFE:"));
        oldOwner.setPhoneNumber(Utility.getValidInput(scanner, "Update owner's phone number:"));
        oldOwner.setEmail(Utility.getValidInput(scanner, "Update owner's email:"));
        System.out.println("--- Update Owner's Emergency Contact ---");
        boolean newContact = Utility.askYesNo(scanner, "Update emergy contact?");
        if (newContact) {
            Person contact = ControllerPerson.search(oldOwner.getContact().getId());
            Person person = registerPerson(scanner);
            contact.setId(person.getId());
            ControllerPerson.update(person);
        }
        ControllerOwner.update(oldOwner);
    }

    public static void registrerSupplies(Scanner scanner) {
        Type typeSupply = ControllerType.assign(scanner, "Supplies");
        Supply supply;
        switch (typeSupply.getType()) {
            case "Medical Supply":
                supply = SupplyFactory.registerSupply(scanner, typeSupply);
                ControllerSupply.add(supply);
                System.out.println("Finally Succesfully :)");
                break;
            case "Grooming Product":
                supply = SupplyFactory.registerSupply(scanner, typeSupply);
                ControllerSupply.add(supply);
                System.out.println("Finally Succesfully :)");

                break;
            case "Vaccine":
                Vaccine vaccine = SupplyFactory.registerVaccine(scanner, typeSupply);
                ControllerSupply.add(vaccine);
                ControllerVaccine.add(vaccine);
                System.out.println("Finally Succesfully :)");

                break;
            case "Medicine":
                Medicine medicine = SupplyFactory.registerMedicine(scanner, typeSupply);
                ControllerSupply.add(medicine);
                ControllerSupply.add(medicine);

                break;
            default:
                System.out.println("Invalid");
                ;
        }
    }

    public static void viewSupplies(Scanner scanner) {
        ControllerSupply.list().forEach(System.out::println);
    }

    public static void updateStock(Scanner scanner) {
        Supply supply = ControllerSupply.assign(scanner);
        supply.setStock(Utility.getValidInt(scanner, "-- Updsate Stock --\n" + supply + "New Stock: "));
        supply.setPrice(Utility.getValidInt(scanner, "New Price: "));
        ControllerSupply.update(supply);
    }

    public static void registrerServices(Scanner scanner) {
        Type typeServicie = ControllerType.assign(scanner, "Service");
        Facturable service;
        switch (typeServicie.getType()) {
            case "Consultations":
                Consultation consultation = ServiceFactory.registerAppointment(scanner, typeServicie);
                ControllerService.add(consultation);
                ControllerAppointment.add(consultation);
                int idIn = Utility.getMaxID("Invoices");
                Invoice inv = new Invoice(idIn + 1, consultation.getPet().getOwner(), 100.00, typeServicie);
                ControllerInvoice.add(inv);
                System.out.println("Finally Succesfully :)");
                System.out.println(inv);
                break;
            case "Grooming":
                Grooming grooming = ServiceFactory.registerGrooming(scanner, typeServicie);
                ControllerService.add(grooming);
                ControllerGrooming.add(grooming);
                idIn = Utility.getMaxID("Invoices");
                inv = new Invoice(idIn + 1, grooming.getPet().getOwner(), 200.00, typeServicie);
                ControllerInvoice.add(inv);
                System.out.println(inv);
                System.out.println("Finally Succesfully :)");
                break;
            case "Training":
                Training training = ServiceFactory.registerTraining(scanner, typeServicie);
                ControllerService.add(training);
                ControllerTraining.add(training);
                idIn = Utility.getMaxID("Invoices");
                inv = new Invoice(idIn + 1, training.getPet().getOwner(), training.getTotalPrice(), typeServicie);
                ControllerInvoice.add(inv);
                System.out.println(inv);
                System.out.println("Finally Succesfully :)");
                break;
            case "Pharmacy":
                Service pha = ServiceFactory.registerService(scanner, typeServicie);
                ControllerService.add(pha);
                idIn = Utility.getMaxID("Invoices");
                inv = new Invoice(idIn + 1, pha.getPet().getOwner(), 90, typeServicie);
                ControllerInvoice.add(inv);
                System.out.println(inv);
                System.out.println("Finally Succesfully :)");
                break;
            case "Procedures":
                Service tra = ServiceFactory.registerService(scanner, typeServicie);
                ControllerService.add(tra);
                idIn = Utility.getMaxID("Invoices");
                inv = new Invoice(idIn + 1, tra.getPet().getOwner(), 990, typeServicie);
                ControllerInvoice.add(inv);
                System.out.println(inv);
                System.out.println("Finally Succesfully :)");
                break;
            case "Daycare":
                Service day = ServiceFactory.registerService(scanner, typeServicie);
                ControllerService.add(day);
                idIn = Utility.getMaxID("Invoices");
                inv = new Invoice(idIn + 1, day.getPet().getOwner(), 95.0, typeServicie);
                ControllerInvoice.add(inv);
                System.out.println(inv);
                System.out.println("Finally Succesfully :)");
                break;
            case "Deworming":
                Service dew = ServiceFactory.registerService(scanner, typeServicie);
                ControllerService.add(dew);
                idIn = Utility.getMaxID("Invoices");
                inv = new Invoice(idIn + 1, dew.getPet().getOwner(), 90, typeServicie);
                ControllerInvoice.add(inv);
                System.out.println(inv);
                System.out.println("Finally Succesfully :)");
                break;
            case "Vaccination":
                Service vac = ServiceFactory.registerService(scanner, typeServicie);
                ControllerService.add(vac);
                idIn = Utility.getMaxID("Invoices");
                inv = new Invoice(idIn + 1, vac.getPet().getOwner(), 150.0, typeServicie);
                ControllerInvoice.add(inv);
                System.out.println(inv);
                System.out.println("Finally Succesfully :)");
                break;
            default:
                System.out.println("Invalid");
        }

    }

    public static void viewVaccines(Scanner scanner) {
        ControllerVaccine.list().forEach(System.out::println);
    }
    public static void viewOwner(Scanner scanner) {
        ControllerOwner.list().forEach(System.out::println);
    }

    public static void attendService(Scanner scanner) {
        Consultation ser3 = ServiceFactory.registerConsultation(scanner);
        ControllerConsultation.add(ser3);
        ControllerService.update(ser3);
    }

    public static void cancelService(Scanner scanner) {
        Service ser3 = ControllerService.assign(scanner);
        ser3.setStatus(Status.CANCELLED);
        ControllerService.update(ser3);
    }

    public static void viewCalendar(Scanner scanner) {
        ControllerAppointment.list().forEach(System.out::println);
    }

    public static void viewInvoice(Scanner scanner) {
        ControllerInvoice.list().forEach(System.out::println);
    }
    public static void viewEmployees(Scanner scanner) {
        ControllerEmployee.list().forEach(System.out::println);
    }

    public static void viewAppoinmentsFilter(Scanner scanner) {
        ControllerAppointment.listSpecial(Status.CANCELLED).forEach(System.out::println);
    }

    public static void viewAnimals(Scanner scanner) {
        ControllerAnimal.listAnimals().forEach(System.out::println);
    }

    // Function for Pets Attended Report
    public static void petsAttendedReport() {
        List<PetVisitInfo> petVisitInfos = Consultas.petsAttendedReport();
        for (PetVisitInfo petVisit : petVisitInfos) {
            System.out.println("Pet Name: " + petVisit.getPetName());
            System.out.println("Number of Visits: " + petVisit.getVisitCount());
            System.out.println("Number of Procedures: " + petVisit.getProcedureCount());
            System.out.println("Number of Vaccines: " + petVisit.getVaccineCount());
            System.out.println("---");
        }
    }

// Function for Most Requested Services Report
    public static void mostRequestedServicesReport() {
        List<ServiceInfo> serviceInfos = Consultas.mostRequestedServicesReport();
        for (ServiceInfo service : serviceInfos) {
            System.out.println("Service Type: " + service.getServiceType());
            System.out.println("Service Count: " + service.getServiceCount());
            System.out.println("---");
        }
    }

// Function for Employee Performance Report
    public static void employeePerformanceReport() {
        List<EmployeeConsultationCount> employeeConsultationCounts = Consultas.employeePerformanceReport();
        for (EmployeeConsultationCount employee : employeeConsultationCounts) {
            System.out.println("Employee Name: " + employee.getEmployeeName());
            System.out.println("Number of Consultations: " + employee.getConsultationCount());
            System.out.println("---");
        }
    }

// Function for Billing Report
    public static void billingReport() {
        List<InvoiceInfo> invoiceInfos = Consultas.billingReport();
        for (InvoiceInfo invoice : invoiceInfos) {
            System.out.println("Total Revenue: " + invoice.getTotalRevenue());
            System.out.println("Month: " + invoice.getInvoiceMonth());
            System.out.println("Year: " + invoice.getInvoiceYear());
            System.out.println("---");
        }
    }

// Function for Supplies Usage Report
    public static void suppliesUsageReport() {
        List<MedicineInventory> medicineInventories = Consultas.suppliesUsageReport();
        for (MedicineInventory medicine : medicineInventories) {
            System.out.println("Medicine Name: " + medicine.getMedicineName());
            System.out.println("Usage Count: " + medicine.getUsageCount());
            System.out.println("Expiration Date: " + medicine.getExpirationDate());
            System.out.println("Expiration Status: " + medicine.getExpirationStatus());
            System.out.println("---");
        }
    }

}
