package org.example;

import entities.Plat;
import services.ServicesPlat;

import java.sql.SQLException;

public class main {
    public static void main(String[] args) {
        ServicesPlat platService = new ServicesPlat();

        // Ajouter un plat
        Plat plat = new Plat("poulet panné", 12f, "", true);
        try {
            platService.ajouter(plat);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        //Modifier un plat
        plat.setNomP("Poulet panné - Nouveau");
        plat.setPrixP(16.0f);
        plat.setIdP(2);
        try {
            platService.modifier(plat);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

         //Supprimer un Plat
        try {
            platService.supprimer(3);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
