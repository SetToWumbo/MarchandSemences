/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.marchandsemences;

import java.util.Random;
import java.util.ArrayList;

/**
 *
 * @author nikolakisiov
 */
public enum Semences {

    RIZ("r","Riz", 23, 150, 10, 0),
    CITROUILLE("c","Citrouille", 50, 400, 15, 1),
    MAIS("m","Mais", 100, 1200, 20, 2);

    Random rng = new Random();
    private String tag;
    private String name;
    private final int PLANCHER;
    private final int PLAFOND;
    private final int FLUCT;  // toutes les semences fluctuent de 0 à valeur. On n'a que besoin de savoir le max de la fluctuation
    private int id;
   
    
    private int stock;
    
    private float prix;
    private int augmentations;
    private ArrayList<Float> histoPrix = new ArrayList<Float>();

    Semences(String tag, String name, int plancher, int plafond, int fluct, int id) {
        this.tag = tag;
        this.name = name;
        PLANCHER = plancher;
        PLAFOND = plafond;
        FLUCT = fluct;
        stock = 0;
        prix = (float) rng.nextInt(plancher, plafond) / 100;
        augmentations = 0;
        histoPrix.add(prix);
        this.id = id;
    }

    public void sauverPrix(float prix) {
        if (histoPrix.size() > 14) {
            histoPrix.remove(0);
            histoPrix.add(prix);
        } else {
            histoPrix.add(prix);
        }
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public float getPlafond() {
        return (float) PLAFOND / 100;
    }

    public float getPlancher() {
        return (float) PLANCHER / 100;
    }

    public int getFluct() {
        return FLUCT;
    }

    public float getPrix() {
        return (float) prix;
    }

    public ArrayList<Float> getHistoPrix() {
        return histoPrix;
    }

    public String getTag() {
        return tag;
    }
    public int getId(){
        return id;
    }

    public void afficherHistoPrix() {
        for (int i = 0; i < histoPrix.size(); i++) {
            System.out.printf("$%.2f ", histoPrix.get(i));
        }
        System.out.println();
    }
    public String toString(){
        return name;
    }

//    public double changerPrix(){}
}
