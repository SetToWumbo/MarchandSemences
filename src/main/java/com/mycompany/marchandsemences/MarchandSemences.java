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

        infosDuJour(joueur);
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
                    infosDuJour(joueur);
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
                    infosDuJour(joueur);
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
                    infosDuJour(joueur);
                    break;

                case HISTORIQUE:
                    System.out.println("Quelle semence voulez-vous consulter?");
                    choixSemence = saisirSemence();
                    System.out.print("Prix des semences de " + choixSemence.toString() + " des 7 derniers jours (plus ancien au plus récent): ");
                    choixSemence.afficherHistoPrix();
                    infosDuJour(joueur);
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

    /**
     * Affiche les informations du jour de la partie
     * @param joueur fournis les infos du joueur à afficher
     */
    private static void infosDuJour(Joueur joueur) {
        StringBuilder stats = new StringBuilder();
        int[] stocks = joueur.getStocks();

        stats.append("Jour ");
        stats.append(joueur.getJour());
        stats.append("   $");
        stats.append(String.format("%.2f", joueur.getBanque()));
        stats.append("   ");

        int i = 0;
        for (Semences semence : Semences.values()) {
            stats.append(semence.toString());
            stats.append("=");
            stats.append(joueur.getStocks()[i]);
            stats.append("[$");
            stats.append(String.format("%.2f", semence.getPrix()));
            stats.append("] ");
            i++;
        }
        System.out.println(stats.toString());
    }

    /**
     * Fait l'achat ou la vente des semences selon l'action en paramètre
     * @param action achat ou vente
     * @param joueur contient les infos du compte en banque et des stocks à modifier
     * @param choixSemence semence à acheter ou vendre
     * @return confirme le succès ou l'échec de l'action
     */
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
     * Compte les augmentations ou diminutions consécutives du prix d'une semence
     * @param jourCourant Jour courant de la partie
     * @param histoPrix historique des prix d'une semence
     * @return int contenant le nombre de changements dans la même direction, indiquée par le signe du nombre
     */
    private static int changementsConsecutifs(int jourCourant, ArrayList<Float> histoPrix) {
        if (histoPrix.size() < 2) {
            return 0;
        }
        int consecutif = 0;
        float fluctuation;
//        tableau contenant le signe de chaque fluctuation de prix
        ArrayList<Integer> signes = new ArrayList<Integer>();

        for (int i = histoPrix.size() - 1; i > 0; i--) {
            fluctuation = (histoPrix.get(i) - histoPrix.get(i - 1));
            int signe = (int) (fluctuation / Math.abs(fluctuation));
            signes.add(0, signe);
        }
        for (int i = signes.size() - 2; i > 0; i--) {
            if (signes.get(i) == signes.get(i + 1)) {
                consecutif += signes.get(i + 1);
            } else {
                signes.clear();
                return consecutif;
            }
        }
        signes.clear();
        return consecutif;
    }

    /**
     * Indique le pourcentage de chance que la fluctuation de prix continue dans la même direction.
     * @param nbChangements Nombre de changements de prix consécutifs dans la même direction
     * @return pourcentage de chance que la direction du changement est conservée
     */
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

    /**
     * Calcule le prix du jour d'une semence selon ses nombres de changements consécutifs dans la même direction dans l'historique des prix
     * @param jour jour courant de la partie
     * @param semence pour laquelle on calcule le prix
     */
    private static void prixDuJour(int jour, Semences semence) {
        Random random = new Random();

        float prix = semence.getPrix();
        float montantFluctuation = (float) random.nextInt(semence.getFluct()) / 100; // montant duquel le prix changera
        int nbConsecutifs = changementsConsecutifs(jour, semence.getHistoPrix()); // nb de changements de prix consecutifs et leur direction
        int chanceContinuer = chanceChangement(Math.abs(nbConsecutifs)); // chance en % de continuer dans la meme direction
        int directionChangement; // direction du changement. 1: aug, -1: dim
        if (nbConsecutifs != 0) {
            directionChangement = nbConsecutifs / Math.abs(nbConsecutifs);
        } else {
            directionChangement = 1;
        }

        if (random.nextInt(100 + 1) > chanceContinuer) {
            directionChangement *= -1;
        }
        montantFluctuation *= directionChangement;
        if (montantFluctuation >= 0) {
            prix = Math.min(prix += montantFluctuation, semence.getPlafond());
        } else {
            prix = Math.max(prix += montantFluctuation, semence.getPlancher());
        }

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
            menu.append(semence.toString());
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
            menu.append(action.toString());
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

    private static int saisirMontant(String item, int maximum) {
        StringBuilder menu = new StringBuilder();
        menu.append("Combien de ");
        menu.append(item);
        menu.append(" [1 à ");
        menu.append(maximum);
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
        } while (montant < 1 || montant > maximum);
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
