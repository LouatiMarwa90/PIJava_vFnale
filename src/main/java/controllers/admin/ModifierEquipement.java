package controllers.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.EquipmentService;
import entities.Equipment;
import entities.EquipmentCategory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

public class ModifierEquipement {

    @FXML
    private ComboBox<String> categorieComboBox;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private Button uploadButton; // Button for uploading image

    @FXML
    private Button modifier;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField noteTextField;

    @FXML
    private Button revenir;

    private Equipment currentEquipment;
    private byte[] imageBytes; // Byte array to store image data

    @FXML
    private void initialize() {
        categorieComboBox.getItems().addAll("CARDIO", "FITNESS", "MUSCULATION");
        if (currentEquipment != null) {
            loadEquipmentDetails();
        }
    }

    public void setEquipment(Equipment equipment) {
        this.currentEquipment = equipment;
        loadEquipmentDetails();
    }

    private void loadEquipmentDetails() {
        if (currentEquipment != null) {
            nameTextField.setText(currentEquipment.getNomEq());
            descriptionTextArea.setText(currentEquipment.getDescEq());
            categorieComboBox.setValue(currentEquipment.getCategorie().name());
            noteTextField.setText(String.valueOf(currentEquipment.getNoteEq()));
            // Assuming you have a method to convert byte array back to a displayable format if necessary
        }
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
    void update(ActionEvent event) {
        if (validateInputs()) {
            EquipmentService equipmentService = new EquipmentService();

            String name = nameTextField.getText();
            String description = descriptionTextArea.getText();
            EquipmentCategory category = EquipmentCategory.valueOf(categorieComboBox.getValue().toUpperCase());
            int note = Integer.parseInt(noteTextField.getText());

            currentEquipment.setNomEq(name);
            currentEquipment.setDescEq(description);
            currentEquipment.setImageEq(imageBytes); // Set image as byte array
            currentEquipment.setCategorie(category);
            currentEquipment.setNoteEq(note);

            try {
                equipmentService.modifier(currentEquipment);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Équipement modifié avec succès.");
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur SQL", "Erreur lors de la modification dans la base de données: " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Données manquantes", "Veuillez remplir tous les champs correctement avant de soumettre.");
        }
    }

    private boolean validateInputs() {
        return !nameTextField.getText().trim().isEmpty() &&
                !descriptionTextArea.getText().trim().isEmpty() &&
                imageBytes != null &&
                categorieComboBox.getValue() != null &&
                noteTextField.getText().trim().matches("\\d+");
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
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
