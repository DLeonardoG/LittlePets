package com.mycompany.pets.model.classes.superclasses;

import com.mycompany.pets.controller.people.ControllerEmployee;
import com.mycompany.pets.controller.services.ControllerService;
import com.mycompany.pets.controller.ControllerType;
import com.mycompany.pets.model.classes.people.Employee;
import com.mycompany.pets.model.classes.enumsandinterfaces.Status;
import com.mycompany.pets.model.classes.superclasses.Type;
import com.mycompany.pets.model.classes.utilities.UtilityTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;

public class Service {

    private int idService;
    private LocalDateTime date;
    private Animal  pet;
    private Employee employee;
    private Type typeService;
    private Status status;
    private boolean paid;

    public Service(int idService, LocalDateTime date, Animal pet, Employee employee, Type typeService, Status status, boolean paid) {
        this.idService = idService;
        this.date = date;
        this.pet = pet;
        this.employee = employee;
        this.typeService = typeService;
        this.status = status;
        this.paid = paid;
    }

    public Service() {
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean getPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
    
    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Animal getPet() {
        return pet;
    }

    public void setPet ( Animal pet) {
        this.pet = pet;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployeee(Employee employee) {
        this.employee = employee;
    }

    public Type getTypeService() {
        return typeService;
    }

    public void setTypeService(Type typeService) {
        this.typeService = typeService;
    }

    @Override
    public String toString() {
        return "\n"+"""
               ID Service: """ + idService + "\n"
                + "Date: " + UtilityTime.convertDateString(date) + "\n"
                + " : " + pet + "\n"
                + "Employee: " + employee + "\n"
                + "Paid: " + paid + "\n"
                + "Status: " + status + "\n"
                + "Type of Service: " + typeService + "\n";
    }

}
