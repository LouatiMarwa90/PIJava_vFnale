package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import services.ServiceCategorieplat;

import java.sql.SQLException;

public class Categorieplat extends entities.Categorieplat {

    private ServiceCategorieplat serviceCategorieplat = new ServiceCategorieplat() {
        @Override
        public void supprimer(int id) {

        }
    };

    @FXML
    private TextField searchField;

    public Categorieplat(String nomCategorie) {

    }

    @FXML
    void ajouterCategorie(ActionEvent event) throws SQLException {
        String nomCategorie = searchField.getText();
        if (!nomCategorie.isEmpty()) {
            entities.Categorieplat newCategorie = new Categorieplat(nomCategorie);
            serviceCategorieplat.ajouter(newCategorie);
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Catégorie ajoutée avec succès !");
            searchField.clear();
        } else {
            showAlert(Alert.AlertType.WARNING, "Erreur", "Veuillez saisir un nom de catégorie.");
        }
    }

    @FXML
    void modifierSaladedefruit(ActionEvent event) {
        modifierCategorie(2, "Salade de Fruit"); // Assuming 2 is the ID for "Salade de Fruit"
    }

    @FXML
    void modifierjuscoctel(ActionEvent event) {
        modifierCategorie(1, "Jus Coctel"); // Assuming 1 is the ID for "Jus Coctel"
    }

    @FXML
    void modifierpouletpanne(ActionEvent event) {
        modifierCategorie(3, "Poulet Pané"); // Assuming 3 is the ID for "Poulet Pané"
    }

    @FXML
    void supprimerSaladedefruit(ActionEvent event) {
        supprimerCategorie(2); // Assuming 2 is the ID for "Salade de Fruit"
    }

    @FXML
    void supprimerjuscoctel(ActionEvent event) {
        supprimerCategorie(1); // Assuming 1 is the ID for "Jus Coctel"
    }

    @FXML
    void suprimerpouletpanne(ActionEvent event) {
        supprimerCategorie(3); // Assuming 3 is the ID for "Poulet Pané"
    }

    private void modifierCategorie(int id, String nouveauNom) {
        try {
            Categorieplat categorie = (Categorieplat) serviceCategorieplat.getOneById(id);
            categorie.setNomCategorie(nouveauNom);
            serviceCategorieplat.modifier(categorie);
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Catégorie modifiée avec succès !");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la modification de la catégorie : " + e.getMessage());
        }
    }

    private void setNomCategorie(String nouveauNom) {
    }

    private void supprimerCategorie(int id) {
        try {
            Categorieplat categorie = (Categorieplat) serviceCategorieplat.getOneById(id);
            serviceCategorieplat.supprimer(categorie.getIdC());
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Catégorie supprimée avec succès !");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression de la catégorie : " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}