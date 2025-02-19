/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.marchandsemences;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author nikolakisiov
 */
public class MarchandSemences {

    private static final int MONTANT_DEPART = 25000;
    private static final int COUT_OPERATION = 25;
    private static final int JOUR_DEPART = 0;
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Joueur joueur = new Joueur(JOUR_DEPART, MONTANT_DEPART);
        Actions action = null;
        Semences choixSemence = null;

        afficherStats(joueur);
        do {
            action = saisirChoix();

            switch (action) {

                case ACHETER:
                    int tropPauvre = 0;
                    for (var semence : Semences.values()) {
                        if (semence.getPrix() > joueur.getBanque()) {
                            tropPauvre++;
                        }
                    }
                    if (tropPauvre == Semences.values().length) {
                        System.out.println("Vous n'avez pas assez d'argent pour acheter une semence");
                        break;
                    }

                    System.out.println("Quelle marchandise souhaitez-vous acheter?");
                    boolean confirme = false;
                    do {
                        choixSemence = saisirSemence();
                        confirme = acheterOuVendre(action, joueur, choixSemence);
                    } while (!confirme);
                    afficherStats(joueur);
                    break;

                case VENDRE:
                    tropPauvre = 0;
                    for (var semence : Semences.values()) {
                        if (joueur.getStocks()[semence.getId()] == 0) {
                            tropPauvre++;
                        }
                    }
                    if (tropPauvre == Semences.values().length) {
                        System.out.println("Vous n'avez pas de stocks à vendre!");
                        break;
                    }

                    System.out.println("Quelle marchandise souhaitez-vous vendre?");
                    confirme = false;
                    do {
                        choixSemence = saisirSemence();
                        confirme = acheterOuVendre(action, joueur, choixSemence);
                    } while (!confirme);
                    afficherStats(joueur);
                    break;

                case ATTENDRE:
                    int jours;
                    jours = saisirMontant("jours", 7);
                    for (int i = 0; i < jours; i++) {
                        for (Semences semence : Semences.values()) {
                            prixDuJour(joueur.getJour(), semence);
                        }
                        joueur.comptabiliser(Actions.ACHETER, COUT_OPERATION);
                        joueur.avancerJour();
                    }
                    afficherStats(joueur);
                    break;

                case HISTORIQUE:
                    System.out.println("Quelle semence voulez-vous consulter?");
                    choixSemence = saisirSemence();
                    System.out.print("Prix des semences de " + choixSemence.toString() + " des 7 derniers jours (plus ancien au plus récent): ");
                    choixSemence.afficherHistoPrix();
                    afficherStats(joueur);
                    break;

                case QUITTER:
                    System.out.println("Au revoir.");
                    break;
            }
        } while (action != Actions.QUITTER && (joueur.getBanque() >= 0));
        scanner.close();

        if (joueur.getBanque() < 0) {
            System.out.println("Vous avez fait faillite!");
        }

    }

    private static void afficherStats(Joueur joueur) {
        StringBuilder stats = new StringBuilder();
        int[] stocks = joueur.getStocks();

        stats.append("Jour ");
        stats.append(joueur.getJour());
        stats.append("   $");
        stats.append(String.format("%.2f", joueur.getBanque()));
        stats.append("   ");

        int i = 0;
        for (Semences semence : Semences.values()) {
            stats.append(semence);
            stats.append("=");
            stats.append(joueur.getStocks()[i]);
            stats.append("[$");
            stats.append(String.format("%.2f", semence.getPrix()));
            stats.append("] ");
            i++;
        }
        System.out.println(stats.toString());
    }

    private static boolean acheterOuVendre(Actions action, Joueur joueur, Semences choixSemence) {
        int max = 0;
        switch (action) {
            case ACHETER:
                if (choixSemence.getPrix() > joueur.getBanque()) {
                    System.out.println("Pas assez d'argent dans le compte!");
                    return false;
                }
                max = (int) (joueur.getBanque() / choixSemence.getPrix());
                break;
            case VENDRE:
                if (joueur.getStocks()[choixSemence.getId()] == 0) {
                    System.out.println("Vous n'avez pas de stocks à vendre!");
                    return false;
                }
                max = joueur.getStocks()[choixSemence.getId()];
                break;
        }
        int kilogrammes = saisirMontant("kilogrammes", max);
        float prixTotal = kilogrammes * choixSemence.getPrix();
        joueur.comptabiliser(action, prixTotal);
        joueur.changerStocks(action, choixSemence, kilogrammes);
        return true;
    }

    /**
     * Rends le nombre de fluctuations consécutives et leur direction a partir
     * de l'historique des prix
     *
     * @param jourCourant Jour courant de la partie
     * @param histoPrix historique des prix d'une semence
     * @return int contenant le nombre de changements dans la même direction.
     * Leur direction est indiquée par le signe
     */
    private static int changementsConsecutifs(int jourCourant, ArrayList<Float> histoPrix) {
//        System.out.println("Debut de la methode changement");
        if (histoPrix.size() < 2) {
//            System.out.println("Tiaille de la liste trop petite pour un changement");
            return 0;
        }

        int consecutif = 0;
        float changement;
//        tableau contenant le signe de chaque changement de prix
        ArrayList<Integer> signes = new ArrayList<Integer>();

        for (int i = histoPrix.size() - 1; i > 0; i--) {
            changement = (histoPrix.get(i) - histoPrix.get(i - 1));
//            System.out.print("Changement de prix: " + changement);
            int signe = (int) (changement / Math.abs(changement));
//            System.out.println(" signe du changement: " + signe);
            signes.add(0, signe);
        }
//        impression du tableau des signes
//        System.out.print("Tableau des signes: ");
//        for (int i = 0; i < signes.size(); i++) {
//            System.out.print(signes.get(i) + ", ");
//        }
//        System.out.println();

//      la dernière position de la liste de signes est le changement de prix prix le plus récent
//        int signe = signes[signes.length - 1];
        for (int i = signes.size() - 2; i > 0; i--) {
            if (signes.get(i) == signes.get(i + 1)) {
                consecutif += signes.get(i + 1);
            } else {
                signes.clear();
                return consecutif;
            }
        }

//        System.out.println("Fin de la boucle des changements consecutifs");
        signes.clear();
        return consecutif;
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

    private static void prixDuJour(int jour, Semences semence) {
        Random random = new Random();

        float prix = semence.getPrix(); // prix courant de la semence
        float montantFluctuation = (float) random.nextInt(semence.getFluct()) / 100; // montant duquel le prix changera
        int nbChangements = changementsConsecutifs(jour, semence.getHistoPrix()); // nb de changement s de prix consecutifs et leur direction
        int chanceContinuer = chanceChangement(Math.abs(nbChangements)); // chance en % de continuer dans la meme direction
        int directionChangement; // direction du changement. 1: aug, -1: dim
        if (nbChangements != 0) {
            directionChangement = nbChangements / Math.abs(nbChangements);
        } else {
            directionChangement = 1;
        }

//        System.out.print("montant de la fluctuation : " + montantFluctuation + " Nombre de changements: " + nbChangements
//                + " Chance de continuer : " + chanceContinuer + " Direction du changement: " + directionChangement + "\n");
        if (random.nextInt(100 + 1) > chanceContinuer) {
            directionChangement *= -1;
//            System.out.println("Nouvelle direction du changement: " + directionChangement);
        }
        montantFluctuation *= directionChangement;
//        System.out.println("Nouveau montant de fluctuation: " + montantFluctuation);

        if (montantFluctuation >= 0) {
            prix = Math.min(prix += montantFluctuation, semence.getPlafond());
        } else {
            prix = Math.max(prix += montantFluctuation, semence.getPlancher());
        }

//        System.out.println("Prix du jour pour " + semence.toString() + ": " + prix + "\n");
        semence.setPrix(prix);
        semence.sauverPrix(prix);
    }

    private static Semences saisirSemence() throws IllegalArgumentException {
        String saisie;
        StringBuilder menu = new StringBuilder();

        for (Semences semence : Semences.values()) {
            menu.append(" [");
            menu.append(semence.getTag());
            menu.append("] ");
            menu.append(semence);
            menu.append(" ");
        }
        menu.append(" : ");
        Semences semence = null;
        do {
            System.out.print(menu.toString());
            saisie = scanner.nextLine().trim().toLowerCase();
            try {
                semence = validerSemence(saisie);
            } catch (Exception e) {

            }
        } while (semence == null);
        return semence;
    }

    private static Actions saisirChoix() {
        String saisie;
        StringBuilder menu = new StringBuilder();
        for (Actions action : Actions.values()) {
            menu.append("[");
            menu.append(action.getTag());
            menu.append("] ");
            menu.append(action);
            menu.append(" ");
        }
        menu.append(" : ");
        Actions action = null;
        do {
            System.out.print(menu.toString());
            saisie = scanner.nextLine().trim().toLowerCase();
            try {
                action = validerSaisie(saisie);
            } catch (Exception e) {
            }
        } while (action == null);
        return action;
    }

    private static int saisirMontant(String item, int maxItem) {
        StringBuilder menu = new StringBuilder();
        menu.append("Combien de ");
        menu.append(item);
        menu.append(" [1 à ");
        menu.append(maxItem);
        menu.append("]");
        menu.append(" ? : ");

        String saisie;
        int montant = 0;

        do {
            System.out.print(menu.toString());
            saisie = scanner.nextLine().trim();
            try {
                montant = Integer.parseInt(saisie);
            } catch (Exception e) {
                montant = 0;
            }
        } while (montant < 1 || montant > maxItem);
        return montant;
    }

    private static Actions validerSaisie(String saisie) throws IllegalArgumentException {
        for (Actions action : Actions.values()) {
            if (saisie.equals(action.getTag())) {
                return action;
            }
        }
        throw new IllegalArgumentException("Saisie invalide.");
    }

    private static Semences validerSemence(String saisie) throws IllegalArgumentException {
        for (Semences semence : Semences.values()) {
            if (saisie.equals(semence.getTag())) {
                return semence;
            }
        }
        throw new IllegalArgumentException("Saisie invalide.");
    }
}
