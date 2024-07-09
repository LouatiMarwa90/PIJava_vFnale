package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import Services.UtilisateurService;

import java.sql.SQLException;

public class Supprimerutilisateuradmin {

    @FXML
    private TextField idutuasupprimeradmin;

    private UtilisateurService utilisateurService;

    public Supprimerutilisateuradmin() {
        this.utilisateurService = new UtilisateurService();
    }

    @FXML
    void Supprimerutilisateuradmin(ActionEvent event) {
        try {
            // Récupérer l'ID de l'utilisateur à supprimer
            int id = Integer.parseInt(idutuasupprimeradmin.getText().trim());

            // Appeler le service pour supprimer l'utilisateur
            utilisateurService.supprimer(id);

            // Afficher une confirmation à l'utilisateur
            showAlert(Alert.AlertType.INFORMATION, "Utilisateur supprimé", "L'utilisateur a été supprimé avec succès.");

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez saisir un ID valide pour l'utilisateur à supprimer.");
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
