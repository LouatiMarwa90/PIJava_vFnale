package controller;

import entities.Plat;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ServicesPlat;

import java.io.IOException;
import java.util.List;

public class AfficherPlatt2  {
    @FXML
    public Label searchField;
    @FXML
    private TextField RechercheField;

    @FXML
    private ListView<Plat> listViewPlat;

    @FXML
    private Button sortButton;

    @FXML
    private Button sortButton1;

    @FXML
    private Button sortButton11;

    @FXML
    private Button sortButton111;

    private ServicesPlat servicesPlat = new ServicesPlat();
    private ObservableList<Plat> platList = FXCollections.observableArrayList();
    private ListView<Plat> platListView;
    private ImageView selectedImageView;


    @FXML
    void AJOUTERPLAT(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterplat.fxml"));
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
    void RECHERCHE(MouseEvent event) {
        String recherche = RechercheField.getText();
        List<Plat> platList = servicesPlat.rechercherPlat(recherche);
        afficherPlatt2(platList);
    }

    @FXML
    void SINGLEPLAT(ActionEvent event) {
        Plat selectedPlat = listViewPlat.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SinglePlat.fxml"));
            Parent root = loader.load();
            SinglePlat singlePlatController = loader.getController();
            singlePlatController.setPlat(selectedPlat);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openAfficherCommUsr(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCommUsr.fxml"));
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
    void sortByPrix(ActionEvent event) {
        List<Plat> sortedPlatList = ServicesPlat.trierPlatParPrix();
        afficherPlatt2(sortedPlatList);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PLAT TRIER.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void afficherPlatt2(List<Plat> plats) {


    }


    @FXML
    void handleImageClick(MouseEvent event) {
        ImageView clickedImageView = (ImageView) event.getSource();
        if (selectedImageView != null) {
            selectedImageView.setStyle(""); // Remove the highlight from the previously selected image
        }
        clickedImageView.setStyle("-fx-border-color: blue; -fx-border-width: 2px;"); // Highlight the clicked image
        selectedImageView = clickedImageView;
    }

    public void searchplat(MouseEvent mouseEvent) {
    }
}