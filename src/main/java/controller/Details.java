package controller;

import entities.AvisPlat;
import entities.Plat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import services.ServicesPlat;
import services.ServicesAvisPlat;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

public class Details {

    @FXML
    private Label nomLabel;

    @FXML
    private Label prixLabel;

    @FXML
    private Label descLabel;

    @FXML
    private Label alergieLabel;

    @FXML
    private Label etatLabel;

    @FXML
    private Label caloriesLabel;

    @FXML
    private ImageView photopImageView;

    @FXML
    private RadioButton star1;

    @FXML
    private RadioButton star2;

    @FXML
    private RadioButton star3;

    @FXML
    private RadioButton star4;

    @FXML
    private RadioButton star5;

    @FXML
    private CheckBox favCheckbox;

    @FXML
    private TextField commAPField;

    @FXML
    private ListView<String> commentsListView;

    private ServicesPlat servicePlat;
    private final ServicesAvisPlat servicesAvisPlat = new ServicesAvisPlat();
    private controller.AfficherPlatt2 AfficherPlatt2 = new controller.AfficherPlatt2();
    private int platId;
//    private Client u;

    public Details() {
//        servicePlat = new ServicesPlat(); // Initialize your service
//        SessionManagement ss = new SessionManagement();
//        String mail = ss.getEmail();
//        ClientService cs = new ClientService();
//        try {
//            u = cs.getOneByEmail(mail);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    public void initData(int platId) {
        this.platId = platId;
    }

    public void initialize(int platId) {
        this.platId = platId;
        try {
            Plat plat = servicePlat.getOneById(platId);
            if (plat != null) {
                nomLabel.setText(plat.getNomP());
                prixLabel.setText(String.valueOf(plat.getPrixP()) + "DT");
                descLabel.setText(plat.getDescP());
                alergieLabel.setText(plat.getAlergieP());
                etatLabel.setText(plat.getEtatP() ? "En stock" : "Rupture de stock");
                caloriesLabel.setText(String.valueOf(plat.getCalories()) + " CAL");
                Image image = new Image(plat.getPhotop());
                photopImageView.setImage(image);

                double averageStarRating = servicesAvisPlat.calculateAverageStarRating(platId);
                setStarRating(averageStarRating);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Set<AvisPlat> comments = servicesAvisPlat.getAllByPlatId(platId);
        ObservableList<String> commentsList = FXCollections.observableArrayList();
        for (AvisPlat avis : comments) {
            commentsList.add(avis.getCommAP() + " | Star: " + avis.getStar());
        }
        commentsListView.setItems(commentsList);
    }

    private void setStarRating(double rating) {
        star1.setSelected(false);
        star2.setSelected(false);
        star3.setSelected(false);
        star4.setSelected(false);
        star5.setSelected(false);

        if (rating >= 1) star1.setSelected(true);
        if (rating >= 2) star2.setSelected(true);
        if (rating >= 3) star3.setSelected(true);
        if (rating >= 4) star4.setSelected(true);
        if (rating >= 5) star5.setSelected(true);
    }

    @FXML
    void Ajout(ActionEvent event) {
        try {
            String commAP = commAPField.getText();
            int star = getSelectedStarRating();
            boolean fav = favCheckbox.isSelected();

            if (star < 1 || star > 5) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Entrez un star rate entre 1 et 5.");
                return;
            }

            Plat platt = servicePlat.getOneById(platId);
//            servicesAvisPlat.ajouter(new AvisPlat(commAP, star, fav, platt, u));
            initialize(platId);

            showAlert(Alert.AlertType.INFORMATION, "Validation", "Avis ajouté avec succès");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherAvis.fxml"));
            Parent root = loader.load();
            commAPField.getScene().setRoot(root);

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Entrez une valeur valide (numeric value).");
        } catch (SQLException | IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    private int getSelectedStarRating() {
        if (star5.isSelected()) return 5;
        if (star4.isSelected()) return 4;
        if (star3.isSelected()) return 3;
        if (star2.isSelected()) return 2;
        if (star1.isSelected()) return 1;
        return 0;
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void goBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPlatt2.fxml"));
            Parent root = loader.load();
            nomLabel.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openModifierPage(ActionEvent actionEvent) {
    }
}
