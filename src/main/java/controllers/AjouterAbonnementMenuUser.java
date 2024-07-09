package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import Services.AbonnementService;
import entites.Abonnement;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AjouterAbonnementMenuUser {

    @FXML
    private TextField Codepromoabo;

    @FXML
    private TextField Dateexpabo;

    @FXML
    private TextField Iduabo;

    @FXML
    private TextField Montantabo;

    @FXML
    private TextField Typeabo;

    @FXML
    void Sabonner(ActionEvent event) {
        try {
            // Récupérer les valeurs des champs de texte
            String codePromo = Codepromoabo.getText().trim();
            String dateExpirationStr = Dateexpabo.getText().trim();
            int idUtilisateur = Integer.parseInt(Iduabo.getText().trim());
            float montant = Float.parseFloat(Montantabo.getText().trim());
            String typeAbonnement = Typeabo.getText().trim().toUpperCase();  // Assurez-vous d'avoir le type en majuscules

            // Convertir la date d'expiration en objet Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dateExpiration = dateFormat.parse(dateExpirationStr);

            // Créer un nouvel objet Abonnement
            Abonnement nouvelAbonnement = new Abonnement();
            nouvelAbonnement.setCodePromo(codePromo);
            nouvelAbonnement.setDateExpiration(dateExpiration);
            nouvelAbonnement.setIdU(idUtilisateur);
            nouvelAbonnement.setMontant(montant);
            nouvelAbonnement.setTypeAbonnement(typeAbonnement);

            // Appeler une méthode de service pour ajouter l'abonnement
            AbonnementService abonnementService = new AbonnementService();
            abonnementService.ajouterS(nouvelAbonnement);

            // Afficher une boîte de dialogue de confirmation
            showAlert(Alert.AlertType.INFORMATION, "Abonnement ajouté", "L'abonnement a été ajouté avec succès.");

            // Effacer les champs après l'ajout
            Codepromoabo.clear();
            Dateexpabo.clear();
            Iduabo.clear();
            Montantabo.clear();
            Typeabo.clear();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez saisir des valeurs numériques valides pour l'ID utilisateur et le montant.");
        } catch (ParseException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de date", "Format de date invalide. Utilisez le format 'yyyy-MM-dd'.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur d'ajout", "Erreur lors de l'ajout de l'abonnement dans la base de données.");
        }
    }

    // Méthode pour afficher une boîte de dialogue d'alerte
    private void showAlert(Alert.AlertType alertType, String titre, String contenu) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }
}
