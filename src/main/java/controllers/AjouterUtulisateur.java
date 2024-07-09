package controllers;

import Services.UtilisateurService;
import entites.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import test.MainFx;
import util.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AjouterUtulisateur {

    private MainFx mainApp;

    @FXML
    private TextField fxiddatei;

    @FXML
    private TextField fxidemaili;

    @FXML
    private TextField fxidmdpi;

    @FXML
    private TextField fxidnomi;

    @FXML
    private TextField fxidprenomi;

    @FXML
    private TextField fxidteli;

    public void setMainApp(MainFx mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    void Bouttoninscription(ActionEvent event) {
        try {
            String nom = fxidnomi.getText().trim();
            String prenom = fxidprenomi.getText().trim();
            String mdp = fxidmdpi.getText().trim();
            String mail = fxidemaili.getText().trim();
            String telStr = fxidteli.getText().trim();
            long tel = 0;

            if (!telStr.isEmpty()) {
                tel = Long.parseLong(telStr);
            }

            String datenaissance = fxiddatei.getText().trim();

            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNomU(nom);
            utilisateur.setPrenomU(prenom);
            utilisateur.setMdp(mdp);
            utilisateur.setMailU(mail);
            utilisateur.setTel(String.valueOf(tel));

            // Convert String date to java.util.Date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateNaissance = sdf.parse(datenaissance);
            utilisateur.setDateNaissance(dateNaissance);



            UtilisateurService utilisateurService = new UtilisateurService();
            utilisateurService.ajouter(utilisateur);

            // Show success message
            showAlert(Alert.AlertType.INFORMATION, "Utilisateur ajouté", "L'utilisateur a été ajouté avec succès.");

            // Clear input fields
            clearFields();

            // Close the registration window and show the login screen
            Stage stage = (Stage) fxidnomi.getScene().getWindow();
            stage.close();
            mainApp.showLoginScreen();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de format", "Veuillez vérifier le format des données entrées.");
            System.err.println("Invalid number format: " + e.getMessage());
            e.printStackTrace(); // Optionally show a message to the user

        } catch (ParseException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de date", "Format de date incorrect.");
            System.err.println("Date parsing error: " + e.getMessage());
            e.printStackTrace(); // Optionally show a message to the user

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur SQL", "Une erreur s'est produite lors de l'ajout de l'utilisateur.");
            System.err.println("SQL error: " + e.getMessage());
            e.printStackTrace(); // Optionally show a message to the user

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur inattendue s'est produite.");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace(); // Catch any other exceptions
        }
    }

    @FXML
    void bouttonfermer(ActionEvent event) {
        // Close the window and show the login screen
        Stage stage = (Stage) fxidnomi.getScene().getWindow();
        stage.close();
        mainApp.showLoginScreen();
    }

    // Method to clear input fields
    private void clearFields() {
        fxidnomi.clear();
        fxidprenomi.clear();
        fxidmdpi.clear();
        fxidemaili.clear();
        fxidteli.clear();
        fxiddatei.clear();
    }

    // Method to show an alert dialog
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
