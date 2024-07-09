package test;

import controllers.AjouterUtulisateur;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFx extends Application {

    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showAjouterUtilisateurScreen(); // Afficher l'écran d'inscription au démarrage
    }

    // Méthode pour afficher l'écran d'inscription
    public void showAjouterUtilisateurScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterUtilisateur.fxml")); // Adjusted path
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Ajouter Utilisateur");
            primaryStage.show();

            // Récupérer le contrôleur AjouterUtilisateurController
            AjouterUtulisateur controller = loader.getController();
            controller.setMainApp(this); // Passer une référence de MainFx au contrôleur

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour afficher l'écran de connexion après l'inscription réussie
    public void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml")); // Adjusted path
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login");
            primaryStage.show();

            // Récupérer le contrôleur LoginController si nécessaire pour gérer la connexion

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour afficher l'écran de menu utilisateur après la connexion réussie
    public void showUserMenuScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserMenu.fxml")); // Adjusted path
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Menu User");
            primaryStage.show();

            // Vous pouvez passer des données au contrôleur InterfaceAdmin si nécessaire
            // InterfaceAdminController controller = loader.getController();
            // controller.initData(adminData); // Exemple d'initialisation de données

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
