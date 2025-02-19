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
    ACHETER("+", "Acheter"),
    VENDRE("-", "Vendre"),
    ATTENDRE("t", "Attendre"),
    HISTORIQUE("h", "Historique"),
    QUITTER("q", "Quitter");

    private String tag;
    private String name;

    Actions(String tag, String name) {
        this.tag = tag;
        this.name = name;
    }

    public String getTag() {
        return tag;
    }
    
    public String toString(){
        return name;
    }

}
