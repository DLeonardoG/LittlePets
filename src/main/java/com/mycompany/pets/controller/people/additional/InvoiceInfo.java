/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pets.controller.people.additional;

public class InvoiceInfo {
    private double totalRevenue;
    private int invoiceMonth;
    private int invoiceYear;

    public InvoiceInfo(double totalRevenue, int invoiceMonth, int invoiceYear) {
        this.totalRevenue = totalRevenue;
        this.invoiceMonth = invoiceMonth;
        this.invoiceYear = invoiceYear;
    }

    // Getters and Setters
    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public int getInvoiceMonth() {
        return invoiceMonth;
    }

    public void setInvoiceMonth(int invoiceMonth) {
        this.invoiceMonth = invoiceMonth;
    }

    public int getInvoiceYear() {
        return invoiceYear;
    }

    public void setInvoiceYear(int invoiceYear) {
        this.invoiceYear = invoiceYear;
    }
}
