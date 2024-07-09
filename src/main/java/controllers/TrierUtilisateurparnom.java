package controllers;

import Services.UtilisateurService;
import entites.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.List;

public class TrierUtilisateurparnom {
    @FXML
    private TextField txtidtri;

    @FXML
    void sortuser(ActionEvent event) {
        try {
            UtilisateurService utilisateurService = new UtilisateurService();
            List<Utilisateur> sortedUsers = utilisateurService.trierParNom();

            // Construction du message avec tous les noms des utilisateurs triés
            StringBuilder message = new StringBuilder("Utilisateurs triés par nom :\n");
            for (Utilisateur user : sortedUsers) {
                message.append(user.getNomU()).append("\n");
            }

            // Affichage d'un message d'alerte avec la liste des utilisateurs triés
            showAlert(Alert.AlertType.INFORMATION, "Tri Réussi", message.toString());

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors du tri des utilisateurs.");
            e.printStackTrace(); // Affichage de l'erreur dans la console pour le débogage
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
