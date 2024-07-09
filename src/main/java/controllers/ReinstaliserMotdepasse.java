package controllers;

import Services.UtilisateurService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class ReinstaliserMotdepasse {
    @FXML
    private TextField txtidreset;

    @FXML
    private TextField txtmdp;

    @FXML
    void resetmdp(ActionEvent event) {
        try {
            int id = Integer.parseInt(txtidreset.getText());
            String nouveauMdp = txtmdp.getText();

            if (nouveauMdp.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Le mot de passe ne peut pas être vide.");
                return;
            }

            UtilisateurService utilisateurService = new UtilisateurService();
            utilisateurService.oublierMdp(id, nouveauMdp);

            showAlert(Alert.AlertType.INFORMATION, "Succès", "Mot de passe réinitialisé avec succès.");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "ID invalide. Veuillez entrer un nombre valide.");
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
