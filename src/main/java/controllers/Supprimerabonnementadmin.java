package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import Services.AbonnementService;

import java.sql.SQLException;

public class Supprimerabonnementadmin {

    @FXML
    private TextField idabonnementasupprimeradmin;

    private AbonnementService abonnementService;

    public Supprimerabonnementadmin() {
        this.abonnementService = new AbonnementService();
    }

    @FXML
    void Supprimerabonnementadmin(ActionEvent event) {
        try {
            // Récupérer l'ID de l'abonnement à supprimer
            int id = Integer.parseInt(idabonnementasupprimeradmin.getText().trim());

            // Appeler le service pour supprimer l'abonnement
            abonnementService.supprimerS(id);

            // Afficher une confirmation à l'utilisateur
            showAlert(Alert.AlertType.INFORMATION, "Abonnement supprimé", "L'abonnement a été supprimé avec succès.");

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez saisir un ID valide pour l'abonnement à supprimer.");
        }
    }

    // Méthode pour afficher une boîte de dialogue d'alerte
    private void showAlert(Alert.AlertType alertType, String titre, String contenu) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }
}
