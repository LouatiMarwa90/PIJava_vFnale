package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import Services.AbonnementService;
import entites.Abonnement;

import java.sql.SQLException;

public class ConsulterAbonnement {
    @FXML
    private TextField idabconsul;

    private AbonnementService abonnementService;

    public ConsulterAbonnement() {
        abonnementService = new AbonnementService(); // Initialize your service instance here
    }

    @FXML
    void checkabonnement(ActionEvent event) {
        try {
            int idAbonnement = Integer.parseInt(idabconsul.getText());

            Abonnement abonnement = abonnementService.consulter(idAbonnement);

            if (abonnement != null) {
                // Display or use the fetched subscription details
                showAlert(Alert.AlertType.INFORMATION, "Détails Abonnement",
                        "Montant: " + abonnement.getMontant() +
                                "\nDate d'expiration: " + abonnement.getDateExpiration() +
                                "\nCode promo: " + abonnement.getCodePromo() +
                                "\nType d'abonnement: " + abonnement.getTypeAbonnement() +
                                "\nID Utilisateur: " + abonnement.getIdU());
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Aucun abonnement trouvé avec l'ID : " + idAbonnement);
            }
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
