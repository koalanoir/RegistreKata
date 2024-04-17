package com.koalanoir;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ParseException {
        String[][] MembersList = init();
        boolean continuer = true;
        while (continuer) {// Boucle principale du programme
            afficherMenu();
            Scanner scanner = new Scanner(System.in);// Lecture de l'entrée de l'utilisateur
            int choix = scanner.nextInt();
            switch (choix) {// Traitement en fonction du choix de l'utilisateur
                case 1:
                    afficherListeMembres(MembersList);
                    message();
                    break;
                case 2:
                    afficherListeMembresAJour(MembersList);
                    message();
                    break;
                case 3:
                    MembersList=addMember(MembersList);
                    message();
                    break;
                case 4:
                    MembersList=updateSubscription(MembersList);
                    message();
                    break;
                case 5:
                    continuer = false; // Quitter le programme
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        }
    }

    public static void afficherMenu() {
        System.out.println("Menu :");
        System.out.println("1. Afficher la liste des membres");
        System.out.println("2. Afficher la liste des membres avec leurs côtisations à jour");
        System.out.println("3. Ajouter un membre");
        System.out.println("4. Renouveler une souscription");
        System.out.println("5. Quitter le programme");
        System.out.print("Choix : ");
    }
    public static void message(){
        System.out.println("Tapez 0 puis entrée pour retourner au menu ..  ");
        Scanner scanner = new Scanner(System.in);
        while (scanner.nextInt()!=0)
            continue;
    }
    public static void afficherListeMembres(String[][] MembersList) {
        System.out.println("Liste des membres :");
        for (int i = 0; i < MembersList.length; i++) {
            String ligneString =MembersList[i][0] + ", " + MembersList[i][1] + ", " + MembersList[i][2];
            System.out.println(i+" - "+ ligneString);
        }
    }
    public static void afficherListeMembresAJour(String[][] MembersList) throws ParseException {
        System.out.println("Liste des membres :");
        int position=0;
        for (int i = 0; i < MembersList.length; i++) {
            String ligneString = MembersList[i][0] + ", " + MembersList[i][1] + ", " + MembersList[i][2];
            if(!Unvalid(MembersList[i][2]))
            {
                System.out.println(position+" - "+ligneString);
                position++;
            }
        }
    }

    public static String[][] init(){
        return new String[][]{
            {"Bernard", "Julie", "31/12/2024"},
            {"Dupont", "Jean", "31/12/2024"},
            {"Dubois", "Marie", "31/12/2021"},
            {"Garcia", "Mathis", "31/12/2024"},
            {"Lefevre", "Pierre", "31/12/2023"},
            {"Leroy", "Hugo", "31/12/2024"},
            {"Martin", "Sophie", "31/12/2024"},
            {"Martinez", "Léa", "31/12/2022"},
            {"Petit", "Emma", "31/12/2024"},
            {"Richard", "Camille", "31/12/2022"},
            {"Robert", "Lucas", "31/12/2024"},
            {"Thomas", "Nicolas", "31/12/2024"}
        };
    }
    public static String[][] addMember( String[][] MembersList){
        System.out.println("Veuillez saisir son nom : ");
        Scanner scanner = new Scanner(System.in);
        String nom = scanner.next();
        System.out.println("et son prénom : ");
        String prennom = scanner.next();
        Calendar calendar = Calendar.getInstance();
        String[] member={nom,prennom,"31/12/"+calendar.get(Calendar.YEAR)};
        int position=0;
        for (String[] existingMember : MembersList) {
            if (member[0].compareTo(existingMember[0]) < 0) {
                break;
            }
            position++;
        }
        for (int i = MembersList.length - 1; i > position; i--) {
            MembersList[i] = MembersList[i - 1];
        }
        MembersList[position] = member;// Insérer la nouvelle ligne à la position spécifiée
        System.out.println("Nouveau membre "+MembersList[position][1]+" "+MembersList[position][0]+" a été ajouté");
        return MembersList;
    }

    public static String[][] updateSubscription(String[][] MembersList) throws ParseException {
        afficherListeMembres(MembersList);
        System.out.println("Quelle est le numero associé du membre : ");
        Scanner scanner = new Scanner(System.in);
        int position=scanner.nextInt();
        if (position < 0 || position >= MembersList.length) {
            System.out.println("Position invalide.");
            return MembersList;
        }
        Calendar calendar = Calendar.getInstance();
        int moisActuel = calendar.get(Calendar.MONTH) + 1; // Les mois dans Calendar sont indexés à partir de 0
        int anneeActuelle = calendar.get(Calendar.YEAR);
        if(moisActuel==12){
            int annee=anneeActuelle++;
            MembersList[position][2]="31/12/"+annee;
            System.out.println("Côtisation de "+MembersList[position][1]+" "+MembersList[position][0]+" à été mis à jour. Désormais valable jusqu'au "+MembersList[position][2]);
        }
        else {
            String datefin = MembersList[position][2];
            System.out.println(MembersList[position][2]);
            if(Unvalid(MembersList[position][2])){
                MembersList[position][2]="31/12/"+anneeActuelle;
                System.out.println("Côtisation de "+MembersList[position][1]+" "+MembersList[position][0]+" à été mis à jour. Désormais valable jusqu'au "+MembersList[position][2]);
            }else {
                System.out.println("Côtisation de "+MembersList[position][1]+" "+MembersList[position][0]+" est déjà à jour. Pas de modification effectuée");
            }
        }
        return MembersList;
    }

    public static boolean Unvalid(String dateMembre) throws ParseException {
        // Formater la date du membre
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dateMembreFormatée = sdf.parse(dateMembre);

        // Obtenir la date actuelle
        Calendar calendrier = Calendar.getInstance();
        Date dateActuelle = calendrier.getTime();

        // Comparer les dates
        return dateActuelle.after(dateMembreFormatée);
    }

}