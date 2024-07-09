package controllers.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import entities.AvisEquipement;
import javafx.stage.Stage;
import services.AvisEquipementService;

import java.io.IOException;

public class AjoutAvisEquipement {

    @FXML
    private Button ajouter;

    @FXML
    private TextArea commentaire;

    @FXML
    private Button revenir;

    private AvisEquipementService avisEquipementService = new AvisEquipementService();

    @FXML
    void add(ActionEvent event) {
        try {
            String commentaireText = commentaire.getText().trim();

            if (commentaireText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Le champ commentaire ne peut pas être vide.");
                return;
            }

            AvisEquipement avisEquipement = new AvisEquipement();
            avisEquipement.setCommentaire(commentaireText);
            System.out.println("Insertion de l'avis avec commentaire: " + commentaireText);
            avisEquipementService.insert(avisEquipement);

            showAlert(Alert.AlertType.ERROR, "Succès", "Avis ajouté avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout de l'avis : " + e.getMessage());
        }
    }

    @FXML
    void back(ActionEvent event) {
        try {
            // Assurez-vous que le chemin est correct et que le fichier FXML se trouve dans le dossier des ressources
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficheAllAvis.fxml"));
            if (loader.getLocation() == null) {
                showAlert(Alert.AlertType.ERROR, "Erreur de chargement", "Le fichier FXML n'a pas été trouvé.");
                return;
            }
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) revenir.getScene().getWindow(); // Récupère la fenêtre actuelle où le bouton est cliqué
            stage.setScene(scene); // Définit la nouvelle scène pour cette fenêtre
            stage.show(); // Affiche la fenêtre avec la nouvelle scène
        } catch (IOException e) {
            e.printStackTrace(); // Affiche la trace de la pile pour le débogage
            showAlert(Alert.AlertType.ERROR, "Erreur lors du chargement de la vue", "Une erreur est survenue lors du chargement de la vue : " + e.getMessage());
        }
    }


    private void showAlert(Alert.AlertType error, String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
