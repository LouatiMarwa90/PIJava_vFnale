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

public class Modifierabonnementadmin {

    @FXML
    private TextField IDabomodifieadmin;

    @FXML
    private TextField idutuadmin;

    @FXML
    private TextField novcodpromoadmin;

    @FXML
    private TextField novdatexpadmin;

    @FXML
    private TextField novmontantaboadmin;

    @FXML
    private TextField novtypeadmin;

    private AbonnementService abonnementService;

    public Modifierabonnementadmin() {
        this.abonnementService = new AbonnementService();
    }

    @FXML
    void ModifierAbonnementAdmin(ActionEvent event) {
        try {
            // Récupérer les valeurs des champs de texte
            int idA = Integer.parseInt(IDabomodifieadmin.getText().trim());
            int idU = Integer.parseInt(idutuadmin.getText().trim());
            String codePromo = novcodpromoadmin.getText().trim();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateExpiration = sdf.parse(novdatexpadmin.getText().trim());
            float montant = Float.parseFloat(novmontantaboadmin.getText().trim());
            String typeAbonnement = novtypeadmin.getText().trim().toUpperCase();  // Assurez-vous d'avoir le type en majuscules

            // Créer un nouvel objet Abonnement modifié
            Abonnement abonnementModifie = new Abonnement();
            abonnementModifie.setIdA(idA);
            abonnementModifie.setIdU(idU);
            abonnementModifie.setCodePromo(codePromo);
            abonnementModifie.setDateExpiration(dateExpiration);
            abonnementModifie.setMontant(montant);
            abonnementModifie.setTypeAbonnement(typeAbonnement);

            // Appeler le service pour modifier l'abonnement
            abonnementService.modifierS(idA, abonnementModifie);

            // Afficher une confirmation à l'utilisateur
            showAlert(Alert.AlertType.INFORMATION, "Abonnement modifié", "L'abonnement a été modifié avec succès.");

        } catch (NumberFormatException | ParseException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez saisir des valeurs valides pour les champs numériques et dates.");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur SQL", "Erreur lors de la modification de l'abonnement : " + e.getMessage());
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
