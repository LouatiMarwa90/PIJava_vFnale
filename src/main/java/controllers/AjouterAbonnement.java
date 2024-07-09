package controllers;


import Services.AbonnementService;
import entites.Abonnement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AjouterAbonnement {
    @FXML
    private TextField txtcode;

    @FXML
    private TextField txtdateexp;

    @FXML
    private TextField txtiduab;

    @FXML
    private TextField txtmontant;

    @FXML
    private TextField txttypeab;

    @FXML
    void addsubscription(ActionEvent event) {
        try {
            int idUtilisateur = Integer.parseInt(txtiduab.getText());
            String typeAbonnement = txttypeab.getText();
            double montant = Double.parseDouble(txtmontant.getText());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dateExpiration = sdf.parse(txtdateexp.getText());
            java.sql.Date sqlDateExpiration = new java.sql.Date(dateExpiration.getTime());

            String codePromo = txtcode.getText(); // Récupération du code promotionnel

            Abonnement abonnement = new Abonnement();
            abonnement.setIdU(idUtilisateur);
            abonnement.setTypeAbonnement(typeAbonnement);
            abonnement.setMontant((float) montant);
            abonnement.setDateExpiration(sqlDateExpiration);
            abonnement.setCodePromo(codePromo); // Attribution du code promotionnel

            // Initialisation de la connexion à la base de données
            Connection connection = DriverManager.getConnection("your_database_url", "username", "password");

            AbonnementService abonnementService = new AbonnementService();
            abonnementService.ajouterS(abonnement);

            showAlert(Alert.AlertType.INFORMATION, "Succès", "Abonnement ajouté avec succès.");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "ID utilisateur ou montant invalide. Veuillez entrer des valeurs correctes.");
        } catch (ParseException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Format de date invalide. Utilisez le format yyyy-MM-dd.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de l'ajout de l'abonnement.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite. Veuillez réessayer.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
