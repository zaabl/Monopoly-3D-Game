/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Vector;
import net.java.games.input.Component;

/**
 *
 * @author dell
 */
public class Chest {

    String action, text;
    int number;

    public Chest(int number, String action, String text) {
        this.action = action;
        this.text = text;
        this.number = number;
    }

    public String getAction() {
        return action;
    }

    public String getText() {
        return text;
    }

    public int getNumber() {
        return number;
    }
    
    
    
}

