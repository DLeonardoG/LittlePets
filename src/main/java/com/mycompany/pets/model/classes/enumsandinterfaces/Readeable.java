/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.pets.model.classes.enumsandinterfaces;

import com.mycompany.pets.model.classes.people.Owner;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author DELL
 */
public interface Readeable {
    List<Object> list();
    Object search(int id);
    Object assign(Scanner scanner);
}
