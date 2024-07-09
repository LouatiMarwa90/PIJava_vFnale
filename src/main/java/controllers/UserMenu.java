package controllers;

import Services.UtilisateurService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import entites.Utilisateur;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.DataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static Services.UtilisateurService.modifier;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class UserMenu {

    @FXML
    private ImageView UserPhoto;

    @FXML
    private TextField champdatenaissance;

    @FXML
    private TextField champemail;

    @FXML
    private TextField champid;

    @FXML
    private TextField champnom;

    @FXML
    private TextField champprenom;

    @FXML
    private TextField champtelephone;

    @FXML
    private ImageView usermenuphotox;

    private Utilisateur utilisateur;
    private UtilisateurService utilisateurService;
    private static Connection connection;

    @FXML
    public void initialize() {
        connection = DataSource.getInstance().getConnexion();
        utilisateurService = new UtilisateurService();


    }

    @FXML
    void Afficherleprofil(ActionEvent event) {
        // Afficher les informations de l'utilisateur
        if (utilisateur != null) {
            champid.setText(String.valueOf(utilisateur.getIdU()));
            champnom.setText(utilisateur.getNomU());
            champprenom.setText(utilisateur.getPrenomU());
            champemail.setText(utilisateur.getMailU());
            champtelephone.setText(utilisateur.getTel());
            champdatenaissance.setText(utilisateur.getDateNaissance().toString());
        }
    }

    @FXML
    void Boutonmodifierinfo(ActionEvent event) {
        try {
            int id = Integer.parseInt(champid.getText().trim()); // Récupérer l'ID de l'utilisateur
            String nom = champnom.getText().trim();
            String prenom = champprenom.getText().trim();
            String mdp = ""; // Vous pouvez ajouter la logique pour récupérer le mot de passe si nécessaire
            String mail = champemail.getText().trim();
            String tel = champtelephone.getText().trim();
            boolean statut = true; // Exemple : à ajuster selon votre logique
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dateNaissance = sdf.parse(champdatenaissance.getText().trim());

            Utilisateur utilisateurModifie = new Utilisateur();
            utilisateurModifie.setIdU(id);
            utilisateurModifie.setNomU(nom);
            utilisateurModifie.setPrenomU(prenom);
            utilisateurModifie.setMdp(mdp);
            utilisateurModifie.setMailU(mail);
            utilisateurModifie.setTel(tel);
            utilisateurModifie.setStatut(statut);
            utilisateurModifie.setDateNaissance(new java.sql.Date(dateNaissance.getTime()));

            // Appeler le service pour modifier l'utilisateur
            utilisateurService.modifier(id, utilisateurModifie);

            showAlert(Alert.AlertType.INFORMATION, "Succès", "Utilisateur modifié avec succès.");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "ID invalide. Veuillez entrer une valeur numérique.");
        } catch (ParseException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Format de date invalide. Utilisez le format yyyy-MM-dd.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de la modification de l'utilisateur.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite. Veuillez réessayer.");
        }
    }

    @FXML
    void MenuAbonnement(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'interface d'ajout d'abonnement
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../AjouterAbonnementMenuUser.fxml"));
            Parent root = loader.load();

            // Obtenir le contrôleur de la vue d'ajout d'abonnement
            AjouterAbonnementMenuUser controller = loader.getController();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Créer une nouvelle fenêtre modale
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Ajouter un abonnement");
            stage.setScene(scene);

            // Afficher la fenêtre modale et attendre que l'utilisateur la ferme
            stage.showAndWait();

            // Récupérer les données d'abonnement saisies par l'utilisateur
            if (controller != null) {
                showAlert(INFORMATION, "Abonnement ajouté", "L'abonnement a été ajouté avec succès.");
            }

        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de chargement", "Une erreur est survenue lors du chargement de l'interface d'abonnement.");
            e.printStackTrace(); // Gérer les erreurs de chargement du fichier FXML
        }
    }

    @FXML
    void MenuAcceuil(ActionEvent event) {
        // Implement navigation to home menu
    }

    @FXML
    void MenuAlimentaire(ActionEvent event) {
        // Implement navigation to food menu
    }

    @FXML
    void MenuEquipement(ActionEvent event) {
        // Implement navigation to equipment menu
    }

    @FXML
    void Bouttondeconnexionuser(ActionEvent event) {
        try {
            int userId = utilisateur.getIdU(); // Récupérer l'ID de l'utilisateur connecté

            String updateQuery = "UPDATE utilisateur SET is_connected = 0 WHERE idU = ?";
            PreparedStatement pstmt = connection.prepareStatement(updateQuery);
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();





        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la déconnexion. Veuillez réessayer.");
        }
    }

    @FXML
    void MenuEvenement(ActionEvent event) {
        // Implement navigation to event menu
    }

    @FXML
    void MenuPlanning(ActionEvent event) {
        // Implement navigation to planning menu
    }

    @FXML
    void MenuReclamations(ActionEvent event) {
        // Implement navigation to complaints menu
    }

    // Méthode pour afficher une boîte de dialogue d'alerte
    private void showAlert(Alert.AlertType alertType, String titre, String contenu) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
