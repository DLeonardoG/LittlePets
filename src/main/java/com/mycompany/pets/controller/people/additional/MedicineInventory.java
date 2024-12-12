/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pets.controller.people.additional;
import java.sql.Date;

public class MedicineInventory {
    private String medicineName;
    private int usageCount;
    private Date expirationDate;
    private String expirationStatus;

    public MedicineInventory(String medicineName, int usageCount, Date expirationDate, String expirationStatus) {
        this.medicineName = medicineName;
        this.usageCount = usageCount;
        this.expirationDate = expirationDate;
        this.expirationStatus = expirationStatus;
    }

    // Getters and Setters
    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public int getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(int usageCount) {
        this.usageCount = usageCount;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getExpirationStatus() {
        return expirationStatus;
    }

    public void setExpirationStatus(String expirationStatus) {
        this.expirationStatus = expirationStatus;
    }
}
