package controllers;

import Services.UtilisateurService;
import entites.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RechercherUtilisateurparnom {

    @FXML
    private TextField txtidr;

    @FXML
    void searchuser(ActionEvent event) {
        try {
            String nom = txtidr.getText().trim();

            // Initialize your connection here (if necessary)
            Connection connection = null; // Replace with actual connection logic if needed

            UtilisateurService utilisateurService = new UtilisateurService();
            List<Utilisateur> utilisateurs = utilisateurService.rechercher(nom);

            if (utilisateurs.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "Résultat de la recherche", "Aucun utilisateur trouvé avec ce nom.");
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Utilisateurs trouvés :\n");
                for (Utilisateur utilisateur : utilisateurs) {
                    sb.append(utilisateur.getNomU()).append(" ").append(utilisateur.getPrenomU()).append("\n");
                }
                showAlert(Alert.AlertType.INFORMATION, "Résultat de la recherche", sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite. Veuillez réessayer.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
