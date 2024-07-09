package controllers.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import entities.AvisEquipement;
import services.AvisEquipementService;

import java.io.IOException;
import java.util.List;

public class AfficheAvisEquipement {

    @FXML
    private Button ajouter;

    @FXML
    private ListView<AvisEquipement> listview;

    @FXML
    private Button modifier;

    @FXML
    private Button supprimer;

    private AvisEquipementService avisEquipementService = new AvisEquipementService();

    @FXML
    void initialize() {
        afficherTousLesAvis();
        customizeListView();
    }

    private void customizeListView() {
        listview.setCellFactory(listView -> new ListCell<AvisEquipement>() {
            @Override
            protected void updateItem(AvisEquipement avis, boolean empty) {
                super.updateItem(avis, empty);
                if (empty || avis == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    TextFlow textFlow = new TextFlow();

                    Text idText = new Text("Numéro commentaire: ");
                    idText.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                    Text valueId = new Text(String.valueOf(avis.getIdA()) + "\n");
                    valueId.setFill(Color.BLUE);

                    Text commentaireText = new Text("Commentaire: ");
                    commentaireText.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                    Text valueCommentaire = new Text(avis.getCommentaire() + "\n");
                    valueCommentaire.setFill(Color.BLACK);

                    textFlow.getChildren().addAll(idText, valueId, commentaireText, valueCommentaire);
                    setGraphic(textFlow);
                }
            }
        });
    }

    private void afficherTousLesAvis() {
        try {
            List<AvisEquipement> avis = avisEquipementService.getAll();
            ObservableList<AvisEquipement> observableAvis = FXCollections.observableArrayList(avis);
            listview.setItems(observableAvis);
        } catch (Exception e) {
            showAlert("Erreur", "Erreur lors du chargement des avis : " + e.getMessage());
        }
    }

    @FXML
    void add(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutAvis.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Ajouter un avis");
            stage.showAndWait();
            afficherTousLesAvis();
        } catch (IOException e) {
            showAlert("Erreur", "Erreur lors du chargement du formulaire d'ajout : " + e.getMessage());
        }
    }

    @FXML
    void delete(ActionEvent event) {
        AvisEquipement selectedAvis = listview.getSelectionModel().getSelectedItem();
        if (selectedAvis != null) {
            try {
                avisEquipementService.supprimer(selectedAvis.getIdA());
                afficherTousLesAvis();
                showAlert("Succès", "Avis supprimé avec succès.");
            } catch (Exception e) {
                showAlert("Erreur", "Erreur lors de la suppression de l'avis : " + e.getMessage());
            }
        } else {
            showAlert("Avertissement", "Veuillez sélectionner un avis à supprimer.");
        }
    }

    @FXML
    void update(ActionEvent event) {
        AvisEquipement selectedAvis = listview.getSelectionModel().getSelectedItem();
        if (selectedAvis != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierAvis.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(loader.load()));
                ModifierAvisEquipement controller = loader.getController();
                controller.setAvis(selectedAvis);
                stage.setTitle("Modifier un avis");
                stage.showAndWait();
                afficherTousLesAvis();
            } catch (IOException e) {
                showAlert("Erreur", "Erreur lors du chargement du formulaire de modification : " + e.getMessage());
            }
        } else {
            showAlert("Avertissement", "Veuillez sélectionner un avis à modifier.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
