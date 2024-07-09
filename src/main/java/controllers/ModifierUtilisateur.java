package controllers;

import Services.UtilisateurService;
import entites.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import util.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ModifierUtilisateur {

    @FXML
    private TextField txtidm;

    @FXML
    private TextField txtnvdate;

    @FXML
    private TextField txtnvmail;

    @FXML
    private TextField txtnvmdp;

    @FXML
    private TextField txtnvnom;

    @FXML
    private TextField txtnvprenom;

    @FXML
    private TextField txtnvstatut;

    @FXML
    private TextField txtnvtel;

    @FXML
    void modifuser(ActionEvent event) {
        try {
            int id = Integer.parseInt(txtidm.getText().trim());
            String nom = txtnvnom.getText().trim();
            String prenom = txtnvprenom.getText().trim();
            String mdp = txtnvmdp.getText().trim();
            String mail = txtnvmail.getText().trim();
            long tel = Long.parseLong(txtnvtel.getText().trim());
            boolean statut = Boolean.parseBoolean(txtnvstatut.getText().trim());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date naissance = sdf.parse(txtnvdate.getText().trim());
            java.sql.Date sqlDateNaissance = new java.sql.Date(naissance.getTime());

            Utilisateur utilisateurModifie = new Utilisateur();
            utilisateurModifie.setIdU(id);
            utilisateurModifie.setNomU(nom);
            utilisateurModifie.setPrenomU(prenom);
            utilisateurModifie.setMdp(mdp);
            utilisateurModifie.setMailU(mail);
            utilisateurModifie.setTel(String.valueOf(tel));
            utilisateurModifie.setStatut(statut);
            utilisateurModifie.setDateNaissance(sqlDateNaissance);



            UtilisateurService utilisateurService = new UtilisateurService();
            utilisateurService.modifier(id, utilisateurModifie);

            showAlert(Alert.AlertType.INFORMATION, "Succès", "Utilisateur modifié avec succès.");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "ID ou téléphone invalide. Veuillez entrer des valeurs correctes.");
        } catch (ParseException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Format de date invalide. Utilisez le format yyyy-MM-dd.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de la mise à jour de l'utilisateur.");
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
