/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.marchandsemences;

import java.util.Random;
import java.util.ArrayList;

/**
 * @author nikolakisiov
 */
public enum Semences {

   // RIZ("r", 23, 150, 10),
   // CITROUILLE("c", 50, 400, 15),
    MAIS("m", 100, 1200, 20);

    private final int PLANCHER;
    private final int PLAFOND;
    private final int FLUCT;  // toutes les semences fluctuent de 0 à valeur. On n'a que besoin de savoir le max de la fluctuation

    private String tag;
    private int stock;
    private double prix;
    private int augmentations;
    private ArrayList<Double> histoPrix = new ArrayList<Double>();

    Semences(String tag, int plancher, int plafond, int fluct) {
        Random rng = new Random();
        this.tag = tag;
        PLANCHER = plancher;
        PLAFOND = plafond;
        FLUCT = fluct;
        stock = 0;
        prix = (double) rng.nextInt(plancher, plafond) / 100;
        augmentations = 0;
        histoPrix.add(prix);
    }

    public ArrayList<Double> getHistoPrix() {
        return this.histoPrix;
    }

    public void sauverPrix() {
        this.histoPrix.add(prix);
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public double getPrix() {
        return this.prix;
    }

    public int getPlafond() {
        return this.PLAFOND;
    }

    public int getPlancher() {
        return this.PLANCHER;
    }

    public int getFluct() {
        return this.FLUCT;
    }
}
