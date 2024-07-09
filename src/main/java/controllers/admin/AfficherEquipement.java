package controllers.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import services.EquipmentService;
import entities.Equipment;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherEquipement {

    @FXML
    private Button ajouter;

    @FXML
    private ListView<Equipment> listview;

    @FXML
    private Button modifier;

    @FXML
    private TextField searchfx;

    @FXML
    private Button supprimer;

    @FXML
    private Button tri;

    private EquipmentService equipmentService = new EquipmentService();

    @FXML
    public void initialize() {
        loadEquipmentList(null);
        listview.setOnMouseClicked(this::handleListViewClick);
        customizeListView();
        setupSearch();
    }

    private void loadEquipmentList(String category) {
        try {
            if (category == null || category.isEmpty()) {
                listview.getItems().setAll(equipmentService.getAll());
            } else {
                listview.getItems().setAll(equipmentService.findByCategory(category));
            }
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors du chargement des équipements : " + e.getMessage());
        }
    }

    private void customizeListView() {
        listview.setCellFactory(listView -> new ListCell<Equipment>() {
            @Override
            protected void updateItem(Equipment equipment, boolean empty) {
                super.updateItem(equipment, empty);
                if (empty || equipment == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    TextFlow textFlow = new TextFlow();
                    Text nameText = new Text("Nom: ");
                    nameText.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                    Text valueName = new Text(equipment.getNomEq() + "\n");
                    valueName.setFill(Color.BLUE);

                    Text descText = new Text("Description: ");
                    descText.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                    Text valueDesc = new Text(equipment.getDescEq() + "\n");
                    valueDesc.setFill(Color.BLUE);

                    Text docText = new Text("Documentation: ");
                    docText.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                    Text valueDoc = new Text(equipment.getDocumentation() + "\n");
                    valueDoc.setFill(Color.BLUE);

                    Text imageText = new Text("Image: ");
                    imageText.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                    Text valueImage = new Text(equipment.getImageEq() + "\n");
                    valueImage.setFill(Color.BLUE);

                    Text noteText = new Text("Note: ");
                    noteText.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                    Text valueNote = new Text(String.valueOf(equipment.getNoteEq()) + "\n");
                    valueNote.setFill(equipment.getNoteEq() >= 10 ? Color.GREEN : Color.RED);

                    Text catText = new Text("Catégorie: ");
                    catText.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                    Text valueCat = new Text(equipment.getCategorie().name() + "\n");
                    valueCat.setFill(Color.BLUE);

                    textFlow.getChildren().addAll(nameText, valueName, descText, valueDesc, docText, valueDoc, imageText, valueImage, noteText, valueNote, catText, valueCat);
                    setGraphic(textFlow);
                }
            }
        });
    }

    private void setupSearch() {
        searchfx.textProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Recherche pour la catégorie: " + newVal);
            loadEquipmentList(newVal.trim());
        });
    }

    @FXML
    void add(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterEquipe.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Ajouter un équipement");
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Erreur lors du chargement du formulaire d'ajout : " + e.getMessage());
        }
    }

    @FXML
    void delete(ActionEvent event) {
        Equipment selected = listview.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                equipmentService.supprimer(selected.getIdEq());
                loadEquipmentList(null);
                showAlert("Succès", "Équipement supprimé avec succès.");
            } catch (SQLException e) {
                if (e.getMessage().contains("foreign key constraint fails")) {
                    showAlert("Erreur", "Impossible de supprimer l'équipement car il est référencé par un ou plusieurs avis.");
                } else {
                    showAlert("Erreur", "Erreur lors de la suppression de l'équipement : " + e.getMessage());
                }
            }
        } else {
            showAlert("Sélectionner", "Veuillez sélectionner un équipement à supprimer.");
        }
    }

    @FXML
    void update(ActionEvent event) {
        Equipment selected = listview.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierEquipement.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(loader.load()));
                ModifierEquipement controller = loader.getController();
                controller.setEquipment(selected);
                stage.setTitle("Modifier un équipement");
                stage.show();
            } catch (IOException e) {
                showAlert("Erreur", "Erreur lors du chargement du formulaire de modification : " + e.getMessage());
            }
        } else {
            showAlert("Sélectionner", "Veuillez sélectionner un équipement à modifier.");
        }
    }

    private void handleListViewClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            update(new ActionEvent());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void faireTri(ActionEvent event) {
        try {
            List<Equipment> sortedList = equipmentService.sortEquipmentByNote();
            listview.getItems().setAll(sortedList);
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors du tri des équipements : " + e.getMessage());
        }
    }
}
