package controllers;

import entities.Reclamation;
import entities.Reponse;
import entities.Utilisateur;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.ReclamationService;
import services.ReponseService;
import services.UtilisateurService;
import util.DataSource;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class Dashboard implements Initializable {

    ObservableList<String> reclamationList = FXCollections.observableArrayList("Problème Technique", "Qualité de service", "Communication Durable");

    ObservableList<String> etatList = FXCollections.observableArrayList("en cours", "traité", "refusé");

    @FXML
    private ChoiceBox<String> addReclamation_CR;
    @FXML
    private ChoiceBox<String> addReclamation_Etat;

    @FXML
    private AnchorPane addRec_form;
    @FXML
    private AnchorPane Rep_form;

    @FXML
    private TextField addReclamation_IdRec;

    @FXML
    private TextField addReclamation_IdUser;

    @FXML
    private Button addReclamation_Modfbtn;

    @FXML
    private Button addReclamation_Suppbtn;

    @FXML
    private Button addReclamation_addbtn;

    @FXML
    private Button RecRep;

    @FXML
    private Button addReclamation_clearbtn;
    @FXML
    private TableView<Reclamation> tableViewReclamations;
    @FXML
    private TableView<Reponse> tableViewReponses;

    @FXML
    private TableColumn<?, ?> addReclamation_col_CR;

    @FXML
    private TableColumn<?, ?> addReclamation_col_Etat;

    @FXML
    private TableColumn<?, ?> addReclamation_col_RecId;

    @FXML
    private TableColumn<?, ?> addReclamation_col_UserId;

    @FXML
    private TableColumn<?, ?> addReclamation_col_desc;

    @FXML
    private TextArea addReclamation_desc;

    @FXML
    private Button addReclamation_importbtn;

    @FXML
    private TextField addReclamation_search;

    //@FXML
    //private BarChart<?, ?> home_chart;
    @FXML
    private BarChart<String, Number> home_chart;
    @FXML
    private AnchorPane home_form;

    @FXML
    private Label home_totRec;

    @FXML
    private Label home_totRecEnC;

    @FXML
    private Label home_totRecTr;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button minimize;

    @FXML
    private Button close;
    @FXML
    private Button home_btn;

    @FXML
    private Button Rec_btn;
    //************Reponse***************************
    @FXML
    private Button rep_btn;
    @FXML
    private Button addRp_btn;

    @FXML
    private Button clearRp_btn;
    @FXML
    private Button rep_modifBtn;

    @FXML
    private Button suppRp_btn;
    @FXML
    private TableColumn<?, ?> idRepcol;
    @FXML
    private TableColumn<?, ?> Repcol;
    @FXML
    private TableColumn<?, ?> IdReccol;
    @FXML
    private TextArea Rep;
    @FXML
    private TextField CidRec;
    @FXML
    private Label statusLabel;

    private double x = 0;
    private double y = 0;
    private ReclamationService reclamationService = new ReclamationService();
    private ObservableList<Reclamation> reclamationsData = FXCollections.observableArrayList();

    private ReponseService reponseService = new ReponseService();
    private ObservableList<Reponse> reponsesData = FXCollections.observableArrayList();
    private Connection DatabaseConnection;

    public Dashboard() {
        DatabaseConnection = DataSource.getInstance().getConnexion();
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showHome();
        updateDashboard();
        home_btn.setOnAction(e -> showHome());
        Rec_btn.setOnAction(e -> showReclamation());
        rep_btn.setOnAction(e -> showReponse());
        RecRep.setOnAction(e -> handlerep());

        // *******************Reclamation*************
        // Initialization logic here
        addReclamation_col_RecId.setCellValueFactory(new PropertyValueFactory<>("idRec"));
        addReclamation_col_CR.setCellValueFactory(new PropertyValueFactory<>("categorieR"));
        addReclamation_col_desc.setCellValueFactory(new PropertyValueFactory<>("description"));
        addReclamation_col_UserId.setCellValueFactory(new PropertyValueFactory<>("idU"));
        addReclamation_col_Etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        addReclamation_CR.setItems(reclamationList);
        addReclamation_Etat.setItems(etatList);
        // Charger les données dans le tableau
        loadReclamationData();
        // Ajouter un écouteur de sélection
        tableViewReclamations.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showReclamationDetails(newValue);
                int idRecSelectionne = newValue.getIdRec();
                CidRec.setText(String.valueOf(idRecSelectionne));
            }
        });
        // Ajouter un écouteur au champ de recherche pour la recherche par état
        addReclamation_search.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filterByEtat(newValue);
            }
        });

        //*************Reponse**********

        idRepcol.setCellValueFactory(new PropertyValueFactory<>("idRep"));
        Repcol.setCellValueFactory(new PropertyValueFactory<>("reponse"));
        IdReccol.setCellValueFactory(new PropertyValueFactory<>("idRec"));
        loadReponses();
        tableViewReponses.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Implémentez la méthode showReponseDetails(newValue) pour afficher les détails de la réponse sélectionnée
                showReponseDetails(newValue);

            }
        });
        // ******************************************************************************


    }


    private void filterByEtat(String etat) {
        try {
            List<Reclamation> filteredList;
            if (etat.isEmpty()) {
                filteredList = reclamationService.getAllNonDeleted();
            } else {
                filteredList = reclamationService.readByEtat(etat);
            }
            reclamationsData.setAll(filteredList);
            tableViewReclamations.setItems(reclamationsData);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception
        }
    }


    private void showHome() {
        home_form.setVisible(true);
        addRec_form.setVisible(false);
        Rep_form.setVisible(false);
    }

    private void showReclamation() {
        home_form.setVisible(false);
        addRec_form.setVisible(true);
        Rep_form.setVisible(false);
    }

    private void showReponse() {
        home_form.setVisible(false);
        addRec_form.setVisible(false);
        Rep_form.setVisible(true);
    }

    private void handlerep() {
        Rep_form.setVisible(true);
        addRec_form.setVisible(false);

    }

    private void showReclamationDetails(Reclamation reclamation) {
        addReclamation_CR.setValue(reclamation.getCategorieR());
        addReclamation_desc.setText(reclamation.getDescription());
        addReclamation_IdUser.setText(String.valueOf(reclamation.getIdU()));
        addReclamation_Etat.setValue(reclamation.getEtat());
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

                loadReclamationData(); // Recharger les données après suppression
                clearFields(); // Effacer les champs après suppression
            } else {
                // L'utilisateur a cliqué sur Annuler ou a fermé l'alerte
                System.out.println("Suppression annulée par l'utilisateur.");
            }
        }
    }

    private void loadReclamationData() {
        try {
            reclamationsData.setAll(reclamationService.getAllNonDeleted());
            tableViewReclamations.setItems(reclamationsData);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception
        }
    }

    @FXML
    private void handleAddReclamation(ActionEvent event) {
        // Récupérer les valeurs des champs de texte
        String categorieR = addReclamation_CR.getValue();
        String description = addReclamation_desc.getText();
        String etat = addReclamation_Etat.getValue();

        // Obtenir l'utilisateur connecté
        UtilisateurService userService = new UtilisateurService();
        Optional<Utilisateur> connectedUser = userService.getConnectedUser();

        if (connectedUser.isPresent()) {
            // Créer un nouvel objet Reclamation
            Reclamation reclamation = new Reclamation();
            reclamation.setCategorieR(categorieR);
            reclamation.setDescription(description);
            reclamation.setIdU(connectedUser.get().getIdU());
            reclamation.setEtat(etat); // Assurez-vous que votre Reclamation a une méthode setEtat si nécessaire

            // Appeler le service pour insérer la réclamation dans la base de données
            reclamationService.insert(reclamation);

            // Afficher une alerte de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Réclamation ajoutée avec succès");
            alert.setHeaderText(null);
            alert.setContentText("Réclamation ajoutée avec succès !");
            alert.showAndWait();

            // Effacer les champs de texte après ajout
            clearFields();

            // Recharger les données du tableau si nécessaire
            loadReclamationData();

        } else {
            // Gérer le cas où aucun utilisateur connecté n'est trouvé
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Aucun utilisateur connecté trouvé.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleUpdateReclamation(ActionEvent event) throws SQLException {
        Reclamation selectedReclamation = tableViewReclamations.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            selectedReclamation.setCategorieR(addReclamation_CR.getValue());
            selectedReclamation.setDescription(addReclamation_desc.getText());
            selectedReclamation.setIdU(Integer.parseInt(addReclamation_IdUser.getText()));
            selectedReclamation.setEtat(addReclamation_Etat.getValue());

            reclamationService.modifier(selectedReclamation);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Réclamation modifiée avec succès");
            alert.setHeaderText(null);
            alert.setContentText("Réclamation modifiée avec succès !");
            alert.showAndWait();

            loadReclamationData();
        }
    }


    @FXML
    private void clearFields() {
        addReclamation_CR.setValue(null);
        addReclamation_desc.clear();
        addReclamation_IdUser.clear();
        addReclamation_Etat.setValue(null);
    }

    @FXML
    private void handleLogout(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de déconnexion");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous vraiment vous déconnecter ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/rec.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) logout.getScene().getWindow();
            stage.setScene(scene);
            stage.show();        }
    }

    public void close(ActionEvent event) {
        System.exit(0);
    }

    public void minimize(ActionEvent event) {
        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }

    public void updateDashboard() {
        home_totRec.setText(String.valueOf(reclamationService.getTotalReclamations()));
        home_totRecEnC.setText(String.valueOf(reclamationService.getTotalReclamationsEnCours()));
        home_totRecTr.setText(String.valueOf(reclamationService.getTotalReclamationsTraitees()));

    }
    // ********************** Reponse ***********************


    private void loadReponses() {
        try {
            List<Reponse> reponses = reponseService.getAll();
            reponsesData.clear();
            reponsesData.addAll(reponses);
            tableViewReponses.setItems(reponsesData);
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer les erreurs de manière appropriée
        }
    }

    private void showReponseDetails(Reponse reponse) {

        Rep.setText(reponse.getReponse());
    }

    @FXML
    private void handleAddReponse(ActionEvent event) {
        // Valider le champ de réponse
        if (!validateRep()) {
            return; // Sortir de la méthode si la validation échoue
        }

        // Récupérer le texte de la réponse et l'ID de la réclamation
        String reponseText = Rep.getText();
        int idRec = Integer.parseInt(CidRec.getText()); // Assurez-vous que CidRec est accessible dans cette méthode

        // Créer une nouvelle instance de Reponse
        Reponse reponse = new Reponse();
        reponse.setReponse(reponseText);
        reponse.setIdRec(idRec);

        // Utiliser le service pour insérer la nouvelle réponse
        reponseService.insert(reponse);

        // Afficher un message de succès
        System.out.println("Réponse ajoutée avec succès !");

        // Afficher une alerte de succès
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Réponse ajoutée avec succès");
        alert.setHeaderText(null);
        alert.setContentText("Réponse ajoutée avec succès !");
        alert.showAndWait();

        // Recharger les données du tableau des réponses
        loadReponses();

        // Effacer les champs de texte après ajout
        clearFieldsRep();
    }




    @FXML
    private void clearFieldsRep() {
        CidRec.clear();
        Rep.clear();

    }


    @FXML
    private void handleUpdateReponse(ActionEvent event) {
        try {
            // Récupérer la réponse sélectionnée dans le TableView
            Reponse selectedReponse = tableViewReponses.getSelectionModel().getSelectedItem();

            // Vérifier que la réponse est bien sélectionnée et que le champ de réponse n'est pas vide
            if (selectedReponse != null && !Rep.getText().isEmpty()) {
                // Mettre à jour la réponse avec le nouveau texte
                selectedReponse.setReponse(Rep.getText());

                // Appeler la méthode de modification du service
                reponseService.modifier(selectedReponse);
                // Afficher une alerte de succès
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Réponse modifiée avec succès");
                alert.setHeaderText(null);
                alert.setContentText("Réponse modifiée avec succès !");
                alert.showAndWait();

                // Recharger les réponses dans le TableView
                loadReponses();
            } else {
                // Afficher une alerte d'erreur si aucun élément n'est sélectionné ou si le champ est vide
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner une réponse et remplir le champ de réponse.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            // Gérer les exceptions SQL
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur s'est produite lors de la modification de la réponse.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        try {
            Reponse selectedReponse = tableViewReponses.getSelectionModel().getSelectedItem();

            // Suppose que vous obtenez l'identifiant de la réponse sélectionnée dans votre UI
            int selectedId = getSelectedReponseId(); // Implémentez cette méthode pour obtenir l'id de la réponse sélectionnée
            ReponseService reponseService = new ReponseService(); // Assurez-vous d'avoir une instance de votre service
            reponseService.supprimer(selectedId);
            loadReponses();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la réponse : " + e.getMessage());
        }
    }

    // Exemple de méthode pour obtenir l'id de la réponse sélectionnée (à ajuster selon votre implémentation)
    private int getSelectedReponseId() {
        // Implémentez la logique pour obtenir l'id de la réponse sélectionnée
        // Par exemple, si vous utilisez un TableView
        Reponse selectedReponse = tableViewReponses.getSelectionModel().getSelectedItem();
        return selectedReponse != null ? selectedReponse.getIdRep() : -1;
    }

    private boolean validateRep() {
        String recText = Rep.getText().trim();
        if (recText.isEmpty() ) {
            statusLabel.setText("Ecrivez votre reponse !");
            return false;
        }
        return true;
    }



}




