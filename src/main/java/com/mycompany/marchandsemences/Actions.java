/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.mycompany.marchandsemences;

/**
 *
 * @author nikolakisiov
 */
public enum Actions {
    //    actions possibles par le joueur
    ACHETER("+"),
    VENDRE("-"),
    ATTENDRE("t"),
    HISTORIQUE("h"),
    QUITTER("q");

    private String tag;

    Actions(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

}
