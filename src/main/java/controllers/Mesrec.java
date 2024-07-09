package controllers;

import entities.Reclamation;
import entities.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.ReclamationService;
import services.UtilisateurService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Mesrec implements Initializable {

    @FXML
    private Button modif;

    @FXML
    private Button supp;


    @FXML
    private AnchorPane MRec_form;

    @FXML
    private Button Rec_btn;
    @FXML
    private Button ajRec;

    @FXML
    private TableColumn<Reclamation, String> categ;

    @FXML
    private Button close;

    @FXML
    private TableColumn<Reclamation, String> etat;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private TableColumn<Reclamation, String> mesrec;

    @FXML
    private Button minimize;

    @FXML
    private TableView<Reclamation> tableViewReclamations;

    @FXML
    void close(ActionEvent event) {
        // Code pour fermer la fenêtre
    }

    @FXML
    void handleLogout(ActionEvent event) {
        // Code pour gérer la déconnexion
    }

    @FXML
    void minimize(ActionEvent event) {
        // Code pour minimiser la fenêtre
    }

    private UtilisateurService utilisateurService = new UtilisateurService();
    private ReclamationService reclamationService = new ReclamationService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categ.setCellValueFactory(new PropertyValueFactory<>("categorieR"));
        etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        mesrec.setCellValueFactory(new PropertyValueFactory<>("description"));

        tableViewReclamations.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                handleShowReclamations();
            }
        });

        // Charger les réclamations au démarrage
        handleShowReclamations();
    }

    @FXML
    private void handleAjRec(ActionEvent event) {
        // Charger la nouvelle vue (mesrec.fxml)
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/rec.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) ajRec.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void handleShowReclamations() {
        try {
            Optional<Utilisateur> connectedUser = utilisateurService.getConnectedUser();
            if (connectedUser.isPresent()) {
                List<Reclamation> reclamations = reclamationService.getAllByConnectedUser(connectedUser.get().getIdU());
                displayReclamations(reclamations);
            } else {
                // Gérer le cas où aucun utilisateur n'est connecté
                System.out.println("Aucun utilisateur connecté trouvé.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs, peut-être afficher un message d'erreur à l'utilisateur
        }
    }

    private void displayReclamations(List<Reclamation> reclamations) {
        tableViewReclamations.getItems().clear();
        tableViewReclamations.getItems().addAll(reclamations);
    }

    @FXML
    private void handleSupprimerReclamation(ActionEvent event) {
        Reclamation selectedReclamation = tableViewReclamations.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            int idRec = selectedReclamation.getIdRec();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText(null);
            alert.setContentText("Êtes-vous sûr de vouloir supprimer cette réclamation ?");

            // Ajouter les boutons OK et Annuler à l'alerte
            ButtonType buttonTypeOK = new ButtonType("OK");
            ButtonType buttonTypeCancel = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);

            // Afficher l'alerte et attendre la réponse de l'utilisateur
            Optional<ButtonType> result = alert.showAndWait();

            // Vérifier la réponse de l'utilisateur
            if (result.isPresent() && result.get() == buttonTypeOK) {
                // L'utilisateur a cliqué sur OK, procéder à la suppression
                reclamationService.supprimer(idRec); // Marquer la réclamation comme supprimée

                // Afficher une alerte de succès
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Réclamation supprimée");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Réclamation supprimée avec succès !");
                successAlert.showAndWait();

                handleShowReclamations(); // Recharger les données après suppression
            } else {
                // L'utilisateur a cliqué sur Annuler ou a fermé l'alerte
                System.out.println("Suppression annulée par l'utilisateur.");
            }
        }
    }

    @FXML
    private void handleUpdateReclamation(ActionEvent event) throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/rec.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage stage = (Stage) ajRec.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        Reclamation selectedReclamation = tableViewReclamations.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            selectedReclamation.setCategorieR(categ.getText());
            selectedReclamation.setDescription(mesrec.getText());

            reclamationService.modifier(selectedReclamation);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Réclamation modifiée avec succès");
            alert.setHeaderText(null);
            alert.setContentText("Réclamation modifiée avec succès !");
            alert.showAndWait();

        }
    }
}
