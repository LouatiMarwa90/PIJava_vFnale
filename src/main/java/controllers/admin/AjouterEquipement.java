package controllers.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.EquipmentService;
import entities.Equipment;
import entities.EquipmentCategory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

public class AjouterEquipement {

    @FXML
    private Button ajouter;

    @FXML
    private ComboBox<String> categorieComboBox;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField noteTextField;

    @FXML
    private Button uploadButton; // Changed from imageTextField to uploadButton

    @FXML
    private Button revenir;

    private byte[] imageBytes; // To store the uploaded image

    @FXML
    private void initialize() {
        // Ajout des options à la ComboBox
        categorieComboBox.getItems().addAll("CARDIO", "FITNESS", "MUSCULATION");
        categorieComboBox.setValue("CARDIO"); // Set default value
    }

    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                imageBytes = Files.readAllBytes(selectedFile.toPath());
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "File Read Error", "Could not read the image file: " + e.getMessage());
            }
        }
    }

    @FXML
    void add(ActionEvent event) {
        if (validateInputs()) {
            EquipmentService equipmentService = new EquipmentService();

            String name = nameTextField.getText();
            String description = descriptionTextArea.getText();
            String documentation = "";  // Assuming you have a field or default value
            EquipmentCategory category;
            int note;

            try {
                category = EquipmentCategory.valueOf(categorieComboBox.getValue().toUpperCase());
                note = Integer.parseInt(noteTextField.getText());

                Equipment newEquipment = new Equipment(name, description, documentation, imageBytes, category, note);
                equipmentService.insert(newEquipment);

                showAlert(Alert.AlertType.INFORMATION, "Succès", "Équipement ajouté avec succès.");
                clearFields();

            } catch (IllegalArgumentException | SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout de l'équipement: " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Données manquantes", "Veuillez remplir tous les champs avant de soumettre.");
        }
    }

    private boolean validateInputs() {
        if (nameTextField.getText().trim().isEmpty() ||
                descriptionTextArea.getText().trim().isEmpty() ||
                imageBytes == null ||
                categorieComboBox.getValue() == null ||
                noteTextField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Données manquantes", "Tous les champs sont obligatoires.");
            return false;
        }

        try {
            int note = Integer.parseInt(noteTextField.getText());
            if (note < 1 || note > 20) {
                showAlert(Alert.AlertType.ERROR, "Erreur de Note", "La note doit être entre 1 et 20.");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur Numérique", "La note doit être un nombre valide entre 1 et 20.");
            return false;
        }

        return true; // All validations passed
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    void clearFields() {
        nameTextField.clear();
        descriptionTextArea.clear();
        noteTextField.clear();
        categorieComboBox.setValue("CARDIO"); // Reset to default or clear with null
        imageBytes = null;  // Reset the image
    }

    @FXML
    void back(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEquipement.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) revenir.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur lors du chargement", "Une erreur est survenue lors du chargement de la vue : " + e.getMessage());
        }
    }
}
