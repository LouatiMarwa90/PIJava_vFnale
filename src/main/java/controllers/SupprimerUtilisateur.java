package controllers;

import Services.UtilisateurService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class SupprimerUtilisateur {
    @FXML
    private TextField txtid;

    @FXML
    void deleteuser(ActionEvent event) {
        try {
            int idU = Integer.parseInt(txtid.getText().trim());  // Récupérer le texte et convertir en int
            UtilisateurService ps = new UtilisateurService();
            ps.supprimer(idU);

            showAlert(Alert.AlertType.INFORMATION, "Suppression Réussie", "Utilisateur supprimé avec succès.");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "ID invalide. Veuillez entrer un nombre valide.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de la suppression de l'utilisateur.");
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
