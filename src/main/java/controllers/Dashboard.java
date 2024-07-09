package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.net.URL;

public class Dashboard {

    @FXML
    private Button AfficheAvis;

    @FXML
    private Button accueil;

    @FXML
    private Button afficheEquipement;

    @FXML
    private BorderPane mainContent;

    @FXML
    void goAfficheAvis(ActionEvent event) {
        loadView("/AfficheAllAvis.fxml");
    }

    @FXML
    void goBackDashboard(ActionEvent event) {
        loadView("/home.fxml");
    }

    @FXML
    void goAfficheEquipement(ActionEvent event) {
        loadView("/AfficherEquipement.fxml");
    }

    private void loadView(String fxmlFile) {
        try {
            URL fxmlUrl = getClass().getResource(fxmlFile);
            if (fxmlUrl == null) {
                showAlert("Load Error", "Cannot find the file: " + fxmlFile);
                return; // Stop if the FXML file is not found
            }
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Node view = loader.load();
            mainContent.getChildren().clear();
            mainContent.setCenter(view); // Assuming mainContent is a BorderPane
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Load Error", "Failed to load the view: " + fxmlFile);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
