package com.mycompany.pets.model.classes.services;

import com.mycompany.pets.model.classes.people.Employee;
import com.mycompany.pets.model.classes.animals.Pet;
import com.mycompany.pets.model.classes.TypePrice;
import com.mycompany.pets.model.classes.TypePrice;
import com.mycompany.pets.model.classes.enumsandinterfaces.Status;
import com.mycompany.pets.model.classes.superclasses.Animal;
import com.mycompany.pets.model.classes.superclasses.Service;
import com.mycompany.pets.model.classes.superclasses.Type;
import java.time.LocalDateTime;

public class Training extends Service {

    private String estimatedTime;
    private String idTraining;
    private TypePrice typeBreed;
    private TypePrice typeBehavior;
    private double totalPrice;

    public Training(String estimatedTime, String idTraining, TypePrice typeBreed, TypePrice typeBehavior, double totalPrice, int idService, LocalDateTime date, Animal pet, Employee employee, Type typeService, Status status, boolean paid) {
        super(idService, date, pet, employee, typeService, status, paid);
        this.estimatedTime = estimatedTime;
        this.idTraining = idTraining;
        this.typeBreed = typeBreed;
        this.typeBehavior = typeBehavior;
        this.totalPrice = totalPrice;
    }

    public Training(Service service, String estimatedTime, String idTraining, TypePrice typeBreed, TypePrice typeBehavior, double totalPrice) {
        super(service.getIdService(), service.getDate(), service.getPet(),
                service.getEmployee(), service.getTypeService(), service.getStatus(), service.getPaid());
        this.estimatedTime = estimatedTime;
        this.idTraining = idTraining;
        this.typeBreed = typeBreed;
        this.typeBehavior = typeBehavior;
        this.totalPrice = totalPrice;
    }

    public Training() {
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getIdTraining() {
        return idTraining;
    }

    public void setIdTraining(String idTraining) {
        this.idTraining = idTraining;
    }

    public TypePrice getTypeBreed() {
        return typeBreed;
    }

    public void setTypeBreed(TypePrice typeBreed) {
        this.typeBreed = typeBreed;
    }

    public TypePrice getTypeBehavior() {
        return typeBehavior;
    }

    public void setTypeBehavior(TypePrice typeBehavior) {
        this.typeBehavior = typeBehavior;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Training{" + "estimatedTime=" + estimatedTime + ", idTraining=" + idTraining + ", typeBreed=" + typeBreed + ", typeBehavior=" + typeBehavior + ", totalPrice=" + totalPrice + '}';
    }

}
