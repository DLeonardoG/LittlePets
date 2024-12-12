
package com.mycompany.pets.model.classes.people;

import com.mycompany.pets.controller.people.ControllerOwner;
import com.mycompany.pets.model.classes.superclasses.Person;
import java.util.Optional;

public class Owner extends Person{
    private String adress;
    private String signature;
    private int points;
    private boolean subscripcion;
    private String CUFE;
    private Person contact;

    public Owner(String adress, String signature, int points, boolean subscripcion, String CUFE, int id, String ID, String name, String phoneNumber, String email,Person contact) {
        super(id, ID, name, phoneNumber, email);
        this.adress = adress;
        this.signature = signature;
        this.points = points;
        this.subscripcion = subscripcion;
        this.CUFE = CUFE;
        this.contact = contact;
    }

    public Owner() {
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isSubscripcion() {
        return subscripcion;
    }

    public void setSubscripcion(boolean subscripcion) {
        this.subscripcion = subscripcion;
    }

    public String getCUFE() {
        return CUFE;
    }

    public void setCUFE(String CUFE) {
        this.CUFE = CUFE;
    }

    public Person getContact() {
        return contact;
    }

    public void setContact(Person contact) {
        this.contact = contact;
    }

    
    @Override
    public String toString() {
        return "Owner{" + "adress=" + adress + ", signature=" + signature + ", points=" + points + ", subscripcion=" + subscripcion + ", CUFE=" + CUFE + '}';
    }

    
    
}
