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

    private int changementsConsecutifs(int jour, ArrayList<Double> histoPrix) {
//        on observe le prix de chaque jour précédent à partir du jour courant
//        on stocke le changement de prix dans un tableau

//       s'il n'y a pas plus qu'un prix dans l'historique, le prix n'a pas pu changer
        if (histoPrix.size() < 2) {
            return 0;
        }

        int consecutif = 0;
        double changement;
        int[] signes = new int[histoPrix.size()]; // size of this array should have 2 options depending on if current day is past day 6

//        on parcours l'historique de prix à partir du jours courant et on stocke le signe du changement de prix dans un tableau
        for (int i = histoPrix.size(); i > 1; i--) {
            changement = (histoPrix.get(i) - histoPrix.get(i--));
            signes[i] = (int) (changement / Math.abs(changement));
        }

//      la dernière position de la liste contient le changement de prix prix le plus récent
        int signe = signes[signes.length - 1];
//          on parcours le tableaux de signes. si les signes des changements sont identiques au plus récent, on incrémente consécutif en conséquence
        for (int j = signes.length - 2; j > 0; j--) {
            boolean egal = (j == signe);
            if (egal) {
                consecutif += signe;
            } else {
                return consecutif;
            }
        }

        System.out.println("Erreur dans le compte de changements consecutifs");
        return 0;
    }

    private int chanceChangement(int nbChangements) {
        switch (nbChangements) {
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
}
