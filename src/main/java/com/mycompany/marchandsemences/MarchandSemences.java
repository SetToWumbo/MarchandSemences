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
        Semences semences;

        int jours = 2;
        for (Semences semence : Semences.values()){
            System.out.print(semence + "Prix de depart: " + semence.getPrix() + "\n");
        }
        for (int i = 0; i < jours; i++) {
            System.out.println("Jour : " + jour);
            for (Semences semence : Semences.values()){
                System.out.print(semence + " Prix du jour: " + semence.getPrix() + "\n");
            }

            for (Semences semence : Semences.values()) {
                System.out.print(semence.toString() + " ");
                prixDuJour(jour, semence);
                semence.sauverPrix();
                System.out.println("fin semence " + semence.toString());
            }
            System.out.println("Jour termine: " + jour);
            jour++;
        }


//        do {
//            choix = saisirChoix();
//
//            switch (choix) {
//                case ACHETER:
//                    break;
//                case VENDRE:
//                    break;
//                case ATTENDRE:
//
//                    break;
//                case HISTORIQUE:
//                    break;
//                case QUITTER:
//                    System.out.println("Au revoir.");
//                    break;
//            }
//
//        } while (choix != Actions.QUITTER || banque > 0);

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

//START OF DAY
//generate amount to fluctuate by
//if we're before day 3:
//    50% chance of either increase or decrease.
//    fluctuate price by amount
//    return price
//else:
//    DO CUMULATIVE FLUCTUATION TEST:
//        got through price history
//        check for cumulative changes
//        return amount of cumulative changes in either direction
//    USE RETURNED AMOUNT TO GIVE CHANCE OF MAINTAINING DIRECTION
//
//returned amout chance of either increase or decrease.
//fluctuate price by amount;
//return price;


//    new day starts
//    GENERATE NEW PRICE:
//    check previous days: if there's more than 1 day (current day > 1), check for price trends.
//    else, generate random price with 50/50 chance

    public static void prixDuJour(int jour, Semences semence) {
        Random random = new Random();
        double prix = semence.getPrix();
        double montantFluct = (double) random.nextInt(semence.getFluct() + 1) / 100;
        // on compte le nombre de changements de prix consecutifs dans le passe
        int changement = changementsConsecutifs(jour, semence.getHistoPrix());
        // on genere le montant duquel du changement du prix
        System.out.println("Montant de fluctuation de base: " + montantFluct);
//		if the chance of it continuing fails, reverses direction of change. Otherwise, proceed with same sign
//		random.nextInt(100+1)<chanceContinue verifie si el signe de
        if (!(random.nextInt(100 + 1) < chanceChangement(Math.abs(changement)))) {
            changement *= -1;
            System.out.println("Signe du changement: " + changement);
        }
        if (changement != 0) {
            montantFluct *= ((changement / Math.abs(changement)));
            System.out.println("Nouveau montant de fluctuation: " + montantFluct);
        }
        if (montantFluct >= 0) {
            semence.setPrix(Math.min(prix + montantFluct, (double)semence.getPlafond() / 100));
        } else {
            semence.setPrix(Math.max(prix + montantFluct, (double)semence.getPlancher() / 100));
        }


    }

    /**
     * observe l'historique des prix d'une semence et retourne le nombre de
     * changements consécutifs
     *
     * @param jour      jour courant du programme
     * @param histoPrix historique des prix d'une semence
     * @return montant de changements consecutifs et leur signe
     */
    public static int changementsConsecutifs(int jour, ArrayList<Double> histoPrix) {
        if (jour < 2){
            return 0;
        }
        int consecutif = 0;
        double changement;
        int[] signes = new int[histoPrix.size()]; // size of this array should have 2 options depending on if current day is past day 6

//        on parcours l'historique de prix à partir du jour courant et on stocke le signe du changement de prix dans un tableau
        for (int i = histoPrix.size() - 1; i > 1; i--) {
            System.out.print("Position : " + i + " ");
            changement = (histoPrix.get(i) - histoPrix.get(i - 1));
            int signe = (int) (changement / Math.abs(changement));
            signes[i] = signe;
            System.out.print("valeur du changement changement : " + changement + " ");
            System.out.print("Changement posi ou nega: " + signe + " ");
        }
//      la dernière position de la liste contient le changement de prix prix le plus récent
        int signe = signes[signes.length - 1];
//          on parcours le tableaux de signes. si les signes des changements sont identiques au plus récent, on incrémente consécutif en conséquence
        for (int j = signes.length - 2; j > 0; j--) {
            boolean egal = (j == signe);
            if (egal) {
                consecutif += signe;
                System.out.println("Changements consecutifs: " + consecutif);
            } else {
                System.out.println("fin des changements consecutifs.");
                return consecutif;
            }
        }

        System.out.println("Erreur dans le compte de changements consecutifs");
        return 0;
    }

    private static int chanceChangement(int nbChangements) {
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
