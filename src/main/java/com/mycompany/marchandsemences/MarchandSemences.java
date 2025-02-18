package com.mycompany.marchandsemences;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * @author nikolakisiov
 */
public class MarchandSemences {

    private static final int MONTANT_DEPART = 25000;
    private static final int COUT_OPERATION = 25;
    public static int jour = 0;

    public static void main(String[] args) {
        double banque = MONTANT_DEPART;
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
                    banque -= jours * COUT_OPERATION;
                    break;
                case HISTORIQUE:
                    break;
                case QUITTER:
                    System.out.println("Au revoir.");
                    break;
            }

        } while (choix != Actions.QUITTER || banque > 0);

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

    public void prixDuJour(Semences semence) {
        Random random = new Random();
        double prix = semence.getPrix();

        int changement = changementsConsecutifs(jour, semence.getHistoPrix());
        int chanceContinue = chanceChangement(Math.abs(changement));

        double montantFluct = (double) random.nextInt(semence.getFluct()) / 100;
//		if the chance of it continuing fails, reverses direction of change. Otherwise, proceed with same sign
//		random.nextInt(100+1)<chanceContinue verifie si el signe de

        if (!(random.nextInt(100 + 1) < chanceContinue)) {
            changement *= -1;
        }
        montantFluct *= ((changement / Math.abs(changement)));

        if (montantFluct >= 0) {
            semence.setPrix(Math.max(prix + montantFluct, semence.getPlafond()));
        } else {
            semence.setPrix(Math.min(prix + montantFluct, semence.getPlancher()));
        }

    }

    /**
     * observe l'historique des prix d'une semence et retourne le nombre de
     * changements consécutifs
     *
     * @param jour      jour courant du programme
     * @param histoPrix historique des prix d'une semence
     * @return montant de changements consecutifds et leur signe
     */
    public int changementsConsecutifs(int jour, ArrayList<Double> histoPrix) {
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
