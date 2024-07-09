package controllers;

import entities.Reclamation;
import entities.Utilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.ReclamationService;
import services.UtilisateurService;
import util.DataSource;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Rec implements Initializable {

    @FXML
    private Button AjouterRec;
    @FXML
    private AnchorPane ajform;
    @FXML
    private Label statusLabel;
    @FXML
    private ChoiceBox<String> cat;
    @FXML
    private TextArea rec;

    private ObservableList<String> reclamationList = FXCollections.observableArrayList("Problème Technique", "Qualité de service", "Communication Durable");
    private ObservableList<Reclamation> reclamationsData = FXCollections.observableArrayList();
    private Connection DatabaseConnection;
    private ReclamationService reclamationService = new ReclamationService();
    private UtilisateurService utilisateurService = new UtilisateurService();

    public Rec() {
        DatabaseConnection = DataSource.getInstance().getConnexion();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cat.setItems(reclamationList);
    }

    @FXML
    private void handleAddReclamation(ActionEvent event) throws IOException {
        if (validateForm()) {
            // Récupérer les valeurs des champs de texte
            String categorieR = cat.getValue();
            String description = rec.getText();

            // Créer un nouvel objet Reclamation
            Reclamation reclamation = new Reclamation();
            reclamation.setCategorieR(categorieR);
            reclamation.setDescription(description);

            // Obtenir l'utilisateur connecté
            Optional<Utilisateur> connectedUser = utilisateurService.getConnectedUser();

            if (connectedUser.isPresent()) {
                int idU = connectedUser.get().getIdU();
                reclamation.setIdU(idU);

                // Appeler le service pour insérer la réclamation dans la base de données
                reclamationService.insert(reclamation);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Réclamation ajoutée avec succès");
                alert.setHeaderText(null);
                alert.setContentText("Réclamation ajoutée avec succès !");
                alert.showAndWait();

                // Charger la nouvelle scène
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/mesrec.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) AjouterRec.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Aucun utilisateur connecté trouvé.");
                alert.showAndWait();
            }
            clearFieldsRec();
        }
    }

    public void handleShowReclamations() {
        try {
            Optional<Utilisateur> connectedUser = utilisateurService.getConnectedUser();
            if (connectedUser.isPresent()) {
                List<Reclamation> reclamations = reclamationService.getAllByConnectedUser(connectedUser.get().getIdU());
                // Code pour afficher les réclamations dans l'interface utilisateur
                displayReclamations(reclamations);
            } else {
                // Gérer le cas où aucun utilisateur connecté n'est trouvé
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Aucun utilisateur connecté trouvé.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs, peut-être afficher un message d'erreur à l'utilisateur
        }
    }

    private void displayReclamations(List<Reclamation> reclamations) {
        // Implémenter le code pour afficher les réclamations dans l'interface utilisateur
    }

    private boolean validateForm() {
        String recText = rec.getText().trim();
        String selectedCategory = cat.getValue();

        if (recText.isEmpty() || selectedCategory == null) {
            statusLabel.setText("Tous les champs doivent être remplis !");
            return false;
        }
        return true;
    }


    @FXML
    private void clearFieldsRec() {
        cat.setValue(null);
        rec.clear();
    }
}
