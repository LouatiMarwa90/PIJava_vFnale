package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import Services.AbonnementService;

import java.sql.SQLException;

public class SupprimerAbonnement {
    @FXML
    private TextField txtidabs;

    private AbonnementService abonnementService;

    public SupprimerAbonnement() {
        abonnementService = new AbonnementService(); // Initialize your service instance here
    }

    @FXML
    void deleteabonnement(ActionEvent event) {
        try {
            int idAbonnement = Integer.parseInt(txtidabs.getText());

            abonnementService.supprimerS(idAbonnement);

            showAlert(Alert.AlertType.INFORMATION, "Succès", "Abonnement supprimé avec succès.");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "ID abonnement invalide. Veuillez entrer un nombre entier.");
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
