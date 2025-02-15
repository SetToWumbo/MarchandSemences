/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.marchandsemences;

import java.util.Random;

/**
 *
 * @author nikolakisiov
 */
public enum Semences {
    
    RIZ("r", 23, 150, 10),
    CITROUILLE("c", 50, 400, 15),
    MAIS("m", 100, 1200, 20);
    Random rng = new Random();
    private final int PLANCHER;
    private final int PLAFOND;
    private final int FLUCT;
    private String tag;
    private int stock;
    private double prix;
    private double[] histoPrix = new double[15];
    Semences(String tag, int plancher, int plafond, int fluct) {
        this.tag = tag;
        PLANCHER = plancher;
        PLAFOND = plafond;
        FLUCT = fluct;
        stock = 0;
        prix = (double) rng.nextInt(plancher, plafond) / 100;
        histoPrix[0] = prix;
    }
    
    public double getPrix(){
        return this.prix;
    }
}
