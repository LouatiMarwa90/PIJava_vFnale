package controller;

import entities.Plat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert;
import services.ServicesPlat;

import java.io.IOException;
import java.sql.SQLException;

public class Modifierplat {

    @FXML
    private TextField NomPlatField;

    @FXML
    private TextField alergiePlatField;

    @FXML
    private Button butt;

    @FXML
    private TextField caloriesField;

    @FXML
    private TextField descPField;

    @FXML
    private CheckBox etatPlatCheckbox;

    @FXML
    private ImageView photopImageView;

    @FXML
    private TextField prixPlatField;

    private ServicesPlat servicePlat = new ServicesPlat();
    private int platId;

    public void initData(int platId) {
        this.platId = platId;
        try {
            Plat plat = servicePlat.getOneById(platId);
            if (plat != null) {
                NomPlatField.setText(plat.getNomP());
                prixPlatField.setText(String.valueOf(plat.getPrixP()));
                alergiePlatField.setText(plat.getAlergieP());
                etatPlatCheckbox.setSelected(plat.getEtatP());
                descPField.setText(plat.getDescP());
                caloriesField.setText(String.valueOf(plat.getCalories()));
                Image image = new Image(plat.getPhotop());
                photopImageView.setImage(image);
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load plat details: " + e.getMessage());
        }
    }

    @FXML
    void ModifierPlat(ActionEvent event) {
        try {
            String nomP = NomPlatField.getText();
            String alergieP = alergiePlatField.getText();
            Float prixP = Float.parseFloat(prixPlatField.getText());
            boolean etatP = etatPlatCheckbox.isSelected();
            String descP = descPField.getText();
            int calories = Integer.parseInt(caloriesField.getText());

            Plat plat = new Plat(platId, nomP);
            servicePlat.modifier(plat);

            showAlert(Alert.AlertType.INFORMATION, "Success", "Plat updated successfully!");

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter valid values.");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update plat: " + e.getMessage());
        }
    }

    @FXML
    void SupprimerPlat(ActionEvent event) {
        try {
            servicePlat.supprimer(platId);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Plat deleted successfully!");
            backToAfficherPlat(event);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete plat: " + e.getMessage());
        }
    }

    @FXML
    void backToAfficherPlat(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPlatt2.fxml"));
            Parent root = loader.load();
            butt.getScene().setRoot(root);
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to go back: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
