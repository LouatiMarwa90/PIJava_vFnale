package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class ModifierAvis {

    @FXML
    private TextField commAPField;

    @FXML
    private CheckBox favCheckBox;

    @FXML
    private TextField starField;

    @FXML
    void goBack(ActionEvent event) {
        showAlert("Going back to the previous screen!");
    }

    @FXML
    void modifierAvis(ActionEvent event) {
        String commentaire = commAPField.getText();
        String starRate = starField.getText();
        boolean isFavorite = favCheckBox.isSelected();


        if (commentaire.isEmpty() || starRate.isEmpty()) {
            showAlert("Commentaire and Star Rate must be filled.");
            return;
        }

        showAlert("Avis modified:\nCommentaire: " + commentaire + "\nStar Rate: " + starRate + "\nFavorite: " + isFavorite);
    }

    @FXML
    void supprimerAvis(ActionEvent event) {
        showAlert("Avis deleted!");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
