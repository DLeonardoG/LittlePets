/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pets.controller.people.additional;

public class EmployeeConsultationCount {
    private String employeeName;
    private int consultationCount;

    public EmployeeConsultationCount(String employeeName, int consultationCount) {
        this.employeeName = employeeName;
        this.consultationCount = consultationCount;
    }

    // Getters and Setters
    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public int getConsultationCount() {
        return consultationCount;
    }

    public void setConsultationCount(int consultationCount) {
        this.consultationCount = consultationCount;
    }
}
