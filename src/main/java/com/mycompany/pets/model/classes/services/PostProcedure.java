/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pets.model.classes.services;

import com.mycompany.pets.model.classes.enumsandinterfaces.Status;
import java.time.LocalDate;


public class PostProcedure {
    private LocalDate nextDate;
    private Status status;
    private String estimatedControl;
    private String comments;

    // Constructor, getters y setters
    public PostProcedure(LocalDate nextDate, Status status, String estimatedControl, String comments) {
        this.nextDate = nextDate;
        this.status = status;
        this.estimatedControl = estimatedControl;
        this.comments = comments;
    }
}
