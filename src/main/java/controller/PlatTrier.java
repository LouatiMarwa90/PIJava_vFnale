package controller;

import entities.PlatWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class PlatTrier implements Initializable {

    @FXML
    private TableView<PlatWrapper> table;

    @FXML
    private TableColumn<PlatWrapper, Integer> id;

    @FXML
    private TableColumn<PlatWrapper, String> nomP;

    @FXML
    private TableColumn<PlatWrapper, Float> prixP;

    @FXML
    private TableColumn<PlatWrapper, Integer> calories;

    private List<PlatWrapper> plats;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Pannier pannierController = new Pannier(); // Assuming you can create an instance like this
        plats = pannierController.getData1();

        plats.sort(Comparator.comparing(plat -> plat.getPrixP()));


        // Bind columns with data
        id.setCellValueFactory(cellData -> cellData.getValue().idPProperty().asObject());
        nomP.setCellValueFactory(cellData -> cellData.getValue().nomPProperty());
        prixP.setCellValueFactory(cellData -> cellData.getValue().prixPProperty().asObject());
        calories.setCellValueFactory(cellData -> cellData.getValue().caloriesProperty().asObject());

        // Populate TableView
        table.getItems().addAll(plats);
    }
}
