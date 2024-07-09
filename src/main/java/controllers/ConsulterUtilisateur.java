package controllers;

import Services.UtilisateurService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import entites.Utilisateur;
import util.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConsulterUtilisateur {
    @FXML
    private TextField txtidc;

    @FXML
    void checkuser(ActionEvent event) {
        try {
            int idU = Integer.parseInt(txtidc.getText().trim());

            // Initialize your connection here (you need to replace this with your actual connection logic)
            Connection connection = DataSource.getInstance().getConnexion();  // Replace with actual connection logic

            UtilisateurService utilisateurService = new UtilisateurService();
            Utilisateur utilisateur = utilisateurService.consulter(String.valueOf(idU));  // Corrected: pass idU instead of mailU

            if (utilisateur != null) {
                // Handle the retrieved utilisateur (e.g., display it in the UI)
                System.out.println("User found: " + utilisateur.getNomU());
                // You can further process the retrieved user information as needed
            } else {
                System.out.println("No user found with ID: " + idU);
                // Optionally, inform the user that no user was found with the given ID
            }

        } catch (NumberFormatException e) {
            System.err.println("Invalid number format: " + e.getMessage());
            e.printStackTrace(); // Optionally show a message to the user
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace(); // Catch any other exceptions
        }
    }
}
