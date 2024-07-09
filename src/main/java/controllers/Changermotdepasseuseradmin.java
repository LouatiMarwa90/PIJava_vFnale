package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import Services.UtilisateurService; // Importer votre classe UtilisateurService ici

public class Changermotdepasseuseradmin {

    @FXML
    private TextField IDabomodifieadmin;

    @FXML
    private TextField novmontantaboadmin;

    // Instance de votre service UtilisateurService
    private UtilisateurService utilisateurService = new UtilisateurService();

    @FXML
    void Modifiermotdepasseuseradmin(ActionEvent event) {
        String idText = IDabomodifieadmin.getText().trim();
        String nouveauMdp = novmontantaboadmin.getText().trim();

        // Vérifier si les champs sont vides
        if (idText.isEmpty() || nouveauMdp.isEmpty()) {
            showAlert("Champs requis", "Veuillez remplir tous les champs.");
            return;
        }

        try {
            int id = Integer.parseInt(idText);

            // Vérifier si l'ID utilisateur existe
            if (!utilisateurService.verifId(id)) {
                showAlert("Utilisateur non trouvé", "Aucun utilisateur trouvé avec l'ID : " + id);
                return;
            }

            // Mettre à jour le mot de passe de l'utilisateur
            utilisateurService.oublierMdp(id, nouveauMdp);

            // Afficher une confirmation
            showAlert("Mot de passe mis à jour", "Mot de passe mis à jour avec succès pour l'utilisateur ID : " + id);

            // Effacer les champs après la mise à jour
            IDabomodifieadmin.clear();
            novmontantaboadmin.clear();

        } catch (NumberFormatException e) {
            showAlert("Erreur de format", "L'ID doit être un nombre entier.");
        }
    }

    // Méthode pour afficher une boîte de dialogue d'alerte
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
