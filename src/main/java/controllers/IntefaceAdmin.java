package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import Services.UtilisateurService;
import Services.AbonnementService;
import entites.Utilisateur;
import entites.Abonnement;

import java.util.List;

public class IntefaceAdmin {

    @FXML
    private TableColumn<Utilisateur, Integer> colIdU;

    @FXML
    private TableColumn<Utilisateur, String> colNomU;

    @FXML
    private TableColumn<Utilisateur, String> colPrenomU;

    @FXML
    private TableColumn<Utilisateur, String> colMailU;

    @FXML
    private TableColumn<Utilisateur, Long> colTel;

    @FXML
    private TableColumn<Utilisateur, Boolean> colStatut;

    @FXML
    private TableColumn<Utilisateur, java.sql.Date> colDateNaissance;

    @FXML
    private TableView<Utilisateur> TableauUser;

    @FXML
    private TableColumn<Abonnement, Integer> colIdA;

    @FXML
    private TableColumn<Abonnement, Double> colMontant;

    @FXML
    private TableColumn<Abonnement, java.sql.Date> colDateExpiration;

    @FXML
    private TableColumn<Abonnement, String> colCodePromo;

    @FXML
    private TableColumn<Abonnement, String> colTypeAbonnement;

    @FXML
    private TableView<Abonnement> TableauAbonnement;

    private final UtilisateurService utilisateurService;
    private final AbonnementService abonnementService;

    public IntefaceAdmin() {
        utilisateurService = new UtilisateurService();
        abonnementService = new AbonnementService();
    }

    @FXML
    void initialize() {
        // Initialize TableView columns for Utilisateurs
        colIdU.setCellValueFactory(new PropertyValueFactory<>("idU"));
        colNomU.setCellValueFactory(new PropertyValueFactory<>("nomU"));
        colPrenomU.setCellValueFactory(new PropertyValueFactory<>("prenomU"));
        colMailU.setCellValueFactory(new PropertyValueFactory<>("mailU"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));
        colDateNaissance.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));

        // Initialize TableView columns for Abonnements
        colIdA.setCellValueFactory(new PropertyValueFactory<>("idA"));
        colMontant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        colDateExpiration.setCellValueFactory(new PropertyValueFactory<>("dateExpiration"));
        colCodePromo.setCellValueFactory(new PropertyValueFactory<>("codePromo"));
        colTypeAbonnement.setCellValueFactory(new PropertyValueFactory<>("typeAbonnement"));

        // Load data into TableViews
        chargerUtilisateurs();
        chargerAbonnements();
    }

    // Charger tous les utilisateurs dans le TableView
    private void chargerUtilisateurs() {
        List<Utilisateur> utilisateurs = utilisateurService.trierParNom();
        ObservableList<Utilisateur> utilisateurObservableList = FXCollections.observableArrayList(utilisateurs);
        TableauUser.setItems(utilisateurObservableList);
    }

    // Charger tous les abonnements dans le TableView
    private void chargerAbonnements() {
        List<Abonnement> abonnements = abonnementService.trouverTous();
        ObservableList<Abonnement> abonnementObservableList = FXCollections.observableArrayList(abonnements);
        TableauAbonnement.setItems(abonnementObservableList);
    }

    @FXML
    void Adminchangermdpuser(ActionEvent event) {
        Utilisateur utilisateurSelectionne = TableauUser.getSelectionModel().getSelectedItem();
        if (utilisateurSelectionne != null) {
            // Afficher une boîte de dialogue pour changer le mot de passe (non implémenté ici)
            System.out.println("Changer le mot de passe pour l'utilisateur : " + utilisateurSelectionne.getNomU());
        } else {
            showAlert(Alert.AlertType.WARNING, "Sélection requise", "Veuillez sélectionner un utilisateur.");
        }
    }

    @FXML
    void Adminmodifieruser(ActionEvent event) {
        Utilisateur utilisateurSelectionne = TableauUser.getSelectionModel().getSelectedItem();
        if (utilisateurSelectionne != null) {
            // Afficher une boîte de dialogue pour la modification (non implémenté ici)
            System.out.println("Modifier l'utilisateur : " + utilisateurSelectionne.getNomU());
        } else {
            showAlert(Alert.AlertType.WARNING, "Sélection requise", "Veuillez sélectionner un utilisateur.");
        }
    }

    @FXML
    void Adminabonnementsexpires(ActionEvent event) {
        abonnementService.rappelAutomatique();
    }

    @FXML
    void Adminsupprimeruser(ActionEvent event) {
        Utilisateur utilisateurSelectionne = TableauUser.getSelectionModel().getSelectedItem();
        if (utilisateurSelectionne != null) {
            int idUtilisateur = utilisateurSelectionne.getIdU();
            utilisateurService.supprimer(idUtilisateur);
            chargerUtilisateurs(); // Rafraîchir la liste des utilisateurs après la suppression
            showAlert(Alert.AlertType.INFORMATION, "Utilisateur supprimé", "Utilisateur ID : " + idUtilisateur + " a été supprimé.");
        } else {
            showAlert(Alert.AlertType.WARNING, "Sélection requise", "Veuillez sélectionner un utilisateur.");
        }
    }

    @FXML
    void Admintrierparnom(ActionEvent event) {
        // Trier les utilisateurs par nom
        List<Utilisateur> utilisateursTries = utilisateurService.trierParNom();
        ObservableList<Utilisateur> utilisateurObservableList = FXCollections.observableArrayList(utilisateursTries);
        TableauUser.setItems(utilisateurObservableList);
    }

    @FXML
    void Adminmodifierabonnement(ActionEvent event) {
        Abonnement abonnementSelectionne = TableauAbonnement.getSelectionModel().getSelectedItem();
        if (abonnementSelectionne != null) {
            // Afficher une boîte de dialogue pour la modification (non implémenté ici)
            System.out.println("Modifier l'abonnement : " + abonnementSelectionne.getIdA());
        } else {
            showAlert(Alert.AlertType.WARNING, "Sélection requise", "Veuillez sélectionner un abonnement.");
        }
    }

    @FXML
    void Adminsupprimerabonnement(ActionEvent event) {
        Abonnement abonnementSelectionne = TableauAbonnement.getSelectionModel().getSelectedItem();
        if (abonnementSelectionne != null) {
            int idAbonnement = abonnementSelectionne.getIdA();
            abonnementService.supprimerS(idAbonnement);
            chargerAbonnements(); // Rafraîchir la liste des abonnements après la suppression
            showAlert(Alert.AlertType.INFORMATION, "Abonnement supprimé", "Abonnement ID : " + idAbonnement + " a été supprimé.");
        } else {
            showAlert(Alert.AlertType.WARNING, "Sélection requise", "Veuillez sélectionner un abonnement.");
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
