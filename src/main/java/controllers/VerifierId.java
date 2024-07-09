package controllers;

import Services.UtilisateurService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class VerifierId {
    @FXML
    private TextField txtidverif;

    @FXML
    void checkid(ActionEvent event) {
        try {
            int idU = Integer.parseInt(txtidverif.getText().trim());

            UtilisateurService utilisateurService = new UtilisateurService();
            boolean idExists = utilisateurService.verifId(idU);

            // Affichage d'un message en fonction du résultat de la vérification
            if (idExists) {
                showAlert(Alert.AlertType.INFORMATION, "ID Trouvé", "L'ID " + idU + " a été trouvé.");
            } else {
                showAlert(Alert.AlertType.WARNING, "ID Introuvable", "L'ID " + idU + " n'a pas été trouvé.");
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Format de nombre invalide: " + e.getMessage());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite: " + e.getMessage());
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
