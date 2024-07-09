package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import Services.UtilisateurService;
import entites.Utilisateur;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Modifierutilisateuradmin {

    @FXML
    private TextField idutuadmin;

    @FXML
    private TextField nomuadmin;

    @FXML
    private TextField prenomuadmin;

    @FXML
    private TextField mdpuadmin;

    @FXML
    private TextField mailuadmin;

    @FXML
    private TextField teluadmin;

    @FXML
    private TextField dateuadmin;

    private UtilisateurService utilisateurService;

    public Modifierutilisateuradmin() {
        this.utilisateurService = new UtilisateurService();
    }

    @FXML
    void ModifierUtilisateurAdmin(ActionEvent event) {
        try {
            // Récupérer les valeurs des champs de texte
            int idU = Integer.parseInt(idutuadmin.getText().trim());
            String nomU = nomuadmin.getText().trim();
            String prenomU = prenomuadmin.getText().trim();
            String mdp = mdpuadmin.getText().trim();
            String mailU = mailuadmin.getText().trim();
            String telU = teluadmin.getText().trim();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateNaissance = sdf.parse(dateuadmin.getText().trim());

            // Créer un nouvel objet Utilisateur modifié
            Utilisateur utilisateurModifie = new Utilisateur();
            utilisateurModifie.setIdU(idU);
            utilisateurModifie.setNomU(nomU);
            utilisateurModifie.setPrenomU(prenomU);
            utilisateurModifie.setMdp(mdp);
            utilisateurModifie.setMailU(mailU);
            utilisateurModifie.setTel(telU);
            utilisateurModifie.setDateNaissance(dateNaissance);

            // Appeler le service pour modifier l'utilisateur
            utilisateurService.modifier(idU, utilisateurModifie);

            // Afficher une confirmation à l'utilisateur
            showAlert(Alert.AlertType.INFORMATION, "Utilisateur modifié", "L'utilisateur a été modifié avec succès.");

        } catch (NumberFormatException | ParseException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez saisir des valeurs valides pour les champs numériques et dates.");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur SQL", "Erreur lors de la modification de l'utilisateur : " + e.getMessage());
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
