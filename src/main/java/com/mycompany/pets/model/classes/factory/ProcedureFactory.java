/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pets.model.classes.factory;

import com.mycompany.pets.model.classes.enumsandinterfaces.Status;
import com.mycompany.pets.model.classes.services.PostProcedure;
import com.mycompany.pets.model.classes.services.PreProcedure;
import com.mycompany.pets.model.classes.services.Procedure;

//public class ProcedureFactory {
//    public static Procedure createNormalProcedure(String name, double price) {
//        return new Procedure(name, price, Status.SCHEDULED);
//    }
//
//    public static Procedure createPreProcedure(String name, double price, PreProcedure preProcedure) {
//        Procedure procedure = new Procedure(name, price, Status.SCHEDULED);
//        procedure.setPreProcedure(preProcedure);
//        return procedure;
//    }
//
//    public static Procedure createPostProcedure(String name, double price, PreProcedure preProcedure, PostProcedure postProcedure) {
//        Procedure procedure = new Procedure(name, price, Status.SCHEDULED);
//        procedure.setPreProcedure(preProcedure);
//        procedure.setPostProcedure(postProcedure);
//        return procedure;
//    }
//}