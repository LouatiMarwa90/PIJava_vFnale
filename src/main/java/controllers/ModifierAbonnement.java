package controllers;

import Services.AbonnementService;
import entites.Abonnement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ModifierAbonnement {
    @FXML
    private TextField txtidnovcod;

    @FXML
    private TextField txtidnovdt;

    @FXML
    private TextField txtidnovtyp;

    @FXML
    private TextField txtidnvab;

    @FXML
    private TextField txtidnvmon;

    @FXML
    private TextField txtidumab;

    private Connection connection; // Assume this is set elsewhere

    // Setter for the connection
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @FXML
    void modifierabonnement(ActionEvent event) {
        if (connection == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur de connexion", "Connexion à la base de données non disponible.");
            return;
        }

        try {
            int idAbonnement = Integer.parseInt(txtidnvab.getText());
            int idUtilisateur = Integer.parseInt(txtidumab.getText());
            double montant = Double.parseDouble(txtidnvmon.getText());
            String typeAbonnement = txtidnovtyp.getText();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dateExpiration = sdf.parse(txtidnovdt.getText());
            java.sql.Date sqlDateExpiration = new java.sql.Date(dateExpiration.getTime());
            String codePromo = txtidnovcod.getText();

            Abonnement abonnementModifie = new Abonnement();
            abonnementModifie.setIdA(idAbonnement);
            abonnementModifie.setIdU(idUtilisateur);
            abonnementModifie.setMontant((float) montant);
            abonnementModifie.setTypeAbonnement(typeAbonnement);
            abonnementModifie.setDateExpiration(sqlDateExpiration);
            abonnementModifie.setCodePromo(codePromo);

            AbonnementService abonnementService = new AbonnementService();
            abonnementService.modifierS(idAbonnement, abonnementModifie);

            showAlert(Alert.AlertType.INFORMATION, "Succès", "Abonnement modifié avec succès.");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "ID abonnement ou ID utilisateur ou montant invalide. Veuillez entrer des valeurs correctes.");
        } catch (ParseException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Format de date invalide. Utilisez le format yyyy-MM-dd.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de la modification de l'abonnement.");
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
