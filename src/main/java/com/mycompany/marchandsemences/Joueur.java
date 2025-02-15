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

    private double banque;

    Joueur(double montant) {
        banque = montant;
    }

    public double getBanque() {
        return this.banque;
    }

}
