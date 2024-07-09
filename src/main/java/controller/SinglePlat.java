package controller;

import entities.Plat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import test.MainFx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SinglePlat {
    @FXML
    private ImageView imageplat;

    @FXML
    private Label nameplat;

    @FXML
    private Label prixplat;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button ajouterPanierButton;

    @FXML
    private Button modifierPlatButton;

    @FXML
    private Button supprimerPlatButton;
    private ActionEvent event;
    private ActionEvent event1;
    private ActionEvent event2;
    private Plat plat;
    private ImageView img;


    public static void setData() {
    }

    @FXML
    void ajouterPanier(ActionEvent event) {
        event1 = event;

        showAlert("Ajouté au Panier", "Le plat a été ajouté au panier avec succès.");
    }

    @FXML
    void modifierPlat(ActionEvent event) {
        this.event = event;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifierplat.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void supprimerPlat(ActionEvent event) {
        event2 = event;

        showAlert("Supprimer Plat", "Le plat a été supprimé avec succès.");
    }

    @FXML
    void initialize() {
        assert ajouterPanierButton != null : "fx:id=\"ajouterPanierButton\" was not injected: check your FXML file 'SinglePlat.fxml'.";
        assert modifierPlatButton != null : "fx:id=\"modifierPlatButton\" was not injected: check your FXML file 'SinglePlat.fxml'.";
        assert supprimerPlatButton != null : "fx:id=\"supprimerPlatButton\" was not injected: check your FXML file 'SinglePlat.fxml'.";
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setPlat(Plat selectedPlat) {
    }
}
