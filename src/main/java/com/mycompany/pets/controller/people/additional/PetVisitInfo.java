/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pets.controller.people.additional;

public class PetVisitInfo {
    private String petName;
    private int visitCount;
    private int procedureCount;
    private int vaccineCount;

    public PetVisitInfo(String petName, int visitCount, int procedureCount, int vaccineCount) {
        this.petName = petName;
        this.visitCount = visitCount;
        this.procedureCount = procedureCount;
        this.vaccineCount = vaccineCount;
    }

    // Getters and Setters
    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    public int getProcedureCount() {
        return procedureCount;
    }

    public void setProcedureCount(int procedureCount) {
        this.procedureCount = procedureCount;
    }

    public int getVaccineCount() {
        return vaccineCount;
    }

    public void setVaccineCount(int vaccineCount) {
        this.vaccineCount = vaccineCount;
    }
}
