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
    private int augmentations;
    private double[] histoPrix = new double[15];
    

    Semences(String tag, int plancher, int plafond, int fluct) {
        this.tag = tag;
        PLANCHER = plancher;
        PLAFOND = plafond;
        FLUCT = fluct;
        stock = 0;
        prix = (double) rng.nextInt(plancher, plafond) / 100;
        augmentations = 0;
        histoPrix[0] = prix;
    }

    public double getPrix() {
        return this.prix;
    }

    public int augmentationsConsecutives(int jour, double[] histoPrix) {
        int nbAugmentations = 0;
        
        for (int i = (jour < 7)? jour : jour % 6; i < jour - 1; i++) {
            if (histoPrix[i++] > histoPrix[i]) {
                nbAugmentations++;
            } else {
                nbAugmentations = 0;
            }
        }
        return nbAugmentations;
    }

    public int chanceAugmentation(int nbAugmentations) {
        switch (nbAugmentations) {
            case 0:
                return 50;
            case 1:
                return 55;
            case 2:
                return 60;
            case 3:
                return 65;
            case 4:
                return 70;
            case 5:
                return 75;
            default:
                return 80;
        }
    }

    public boolean prixAugmente(int jour, int chanceAugmentation) {
            int chance = chanceAugmentation(jour)
    }

    public double prixDuJour() {
        double fluctuation;
//        calculate the price fluctuation of a given semence
//         call a method that checks whether or not price increases
//        add or subtract amount with a Math.min or Math.max

        for (Semences semence : Semences.values()) {
            if (prixAugmente) {
                semence.prix = Math.min(semence.prix + fluctuation, semence.PLAFOND);
            } else {
                semence.prix = Math.max(semence.prix - fluctuation, semence.PLANCHER);
            }

        }

    }
}
