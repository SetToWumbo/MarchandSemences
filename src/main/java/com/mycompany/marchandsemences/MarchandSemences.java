/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.marchandsemences;

import java.util.Scanner;

/**
 *
 * @author nikolakisiov
 */
public class MarchandSemences {

    private static final int MONTANT_DEPART = 25000;
    private static final int COUT_OPERATION = 25;
    public static int jour = 0;
//    public ArrayList<double> marche = new ArrayList<double>();

    public static void main(String[] args) {
        Joueur Player1 = new Joueur(MONTANT_DEPART);
        Actions choix;
        do {
            choix = saisirChoix();

            switch (choix) {
                case ACHETER:
                    break;
                case VENDRE:
                    break;
                case ATTENDRE:
                    int jours = 5;
                    jour += jours;
                    Player1.banque() -= jours * COUT_OPERATION;
                    break;
                case HISTORIQUE:
                    break;
                case QUITTER:
                    System.out.println("Au revoir.");
                    break;
            }

        } while (choix != Actions.QUITTER || Player1.getBanque() > 0);

    }

    private static Actions saisirChoix() {
        Scanner scanner = new Scanner(System.in);
        String saisie;
        boolean estValide = false;

        do {
            System.out.print("[+] Acheter, [-] Vendre, [t] Attendre, [h] Historique, [q] Quitter ?");
            saisie = scanner.nextLine().trim().toLowerCase();
            Actions actionValide;

            for (Actions action : Actions.values()) {
                if (saisie.equals(action.tag)) {

                    return action;
                }
            }
        } while (!estValide);
        return null;
    }

}
