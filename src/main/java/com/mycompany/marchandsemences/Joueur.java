/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.marchandsemences;

/**
 *
 * @author nikolakisiov
 */
//    contient les infos du joueur
public class Joueur {

    private int jour;
    private float banque;
    private int[] stocks = new int[Semences.values().length];

    Joueur(int jourDepart, float montantDepart) {
        jour = jourDepart;
        banque = montantDepart;
    }

 

    public void avancerJour() {
        jour++;
    }

    public int getJour() {
        return jour;
    }

    public double getBanque() {
        return this.banque;
    }

    public int[] getStocks() {
        return stocks;
    }

    public void comptabiliser(Actions action, float montant) {
        if (action == Actions.ACHETER) {
            montant *= -1;
        }
        banque += montant;
    }
       public void changerStocks(Actions action, Semences choixSemence, int montant) {
        if (action == Actions.VENDRE) {
            montant *= -1;
        }
        stocks[choixSemence.getId()] += montant;
    }

}
