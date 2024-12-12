
package com.mycompany.pets.model.classes.services;

import com.mycompany.pets.controller.services.ControllerGrooming;
import com.mycompany.pets.model.classes.TypePrice;
import com.mycompany.pets.model.classes.enumsandinterfaces.Facturable;
import com.mycompany.pets.model.classes.enumsandinterfaces.Status;
import com.mycompany.pets.model.classes.superclasses.Service;
import com.mycompany.pets.model.classes.superclasses.Type;
import java.time.LocalDateTime;
import java.util.Optional;



public class Grooming extends Service implements Facturable{
    private int idGrooming;
    private TypePrice typeGrooming;
    private String comments;
    
    
    public Grooming() {
    }
    
    public Grooming(Service service, int idGrooming, TypePrice typeGrooming, String comments) {
        super(service.getIdService(), service.getDate(), service.getPet(),
              service.getEmployee(), service.getTypeService(), service.getStatus(), service.getPaid());
        this.idGrooming = idGrooming;
        this.typeGrooming = typeGrooming;
        this.comments = comments;
    }
    public Grooming(Service service) {
        super(service.getIdService(), service.getDate(), service.getPet(),
              service.getEmployee(), service.getTypeService(), service.getStatus(), service.getPaid());
    }

    // Getters y setters
    public int getIdGrooming() {
        return idGrooming;
    }

    public void setIdGrooming(int idGrooming) {
        this.idGrooming = idGrooming;
    }

    public TypePrice getTypeGrooming() {
        return typeGrooming;
    }

    public void setTypeGrooming(TypePrice typeGrooming) {
        this.typeGrooming = typeGrooming;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
    
    @Override
    public String toString() {
        return super.toString()+"typeGrooming: " + typeGrooming + ", idGrooming: " + idGrooming + ", comments:" + comments;
    }

    @Override
    public void calcular() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
