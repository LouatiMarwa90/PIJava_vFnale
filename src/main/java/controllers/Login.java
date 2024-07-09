package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import util.DataSource;
import entites.Utilisateur;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {

    @FXML
    private TextField fxidauthemail;

    @FXML
    private TextField fxidautmdp;

    @FXML
    void Authentification(ActionEvent event) {
        String email = fxidauthemail.getText().trim();
        String password = fxidautmdp.getText().trim();

        try {
            Connection connection = DataSource.getInstance().getConnexion();

            if (email.equals("admin@admin.com") && password.equals("admin")) {
                // Navigate to InterfaceAdmin
                navigateToAdminMenu();
            } else {
                Utilisateur utilisateur = authenticateUser(connection, email, password);

                if (utilisateur != null) {
                    // Update is_connected for the user
                    updateIsConnected(connection, utilisateur.getIdU(), true);

                    showAlert(Alert.AlertType.INFORMATION, "Authentification réussie", "Vous êtes maintenant connecté.");

                    clearFields();

                    // Load the UserMenu view and pass the user
                    navigateToUserMenu(utilisateur);
                } else {
                    showAlert(Alert.AlertType.ERROR, "Authentification échouée", "Veuillez vérifier vos identifiants.");
                }
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur SQL", "Une erreur s'est produite lors de l'authentification.");
            System.err.println("SQL error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur inattendue s'est produite.");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void bouttonfermer(ActionEvent event) {
        Stage stage = (Stage) fxidauthemail.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Utilisateur authenticateUser(Connection connection, String email, String password) throws SQLException {
        String sql = "SELECT * FROM utilisateur WHERE mailU = ? AND mdp = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Utilisateur utilisateur = new Utilisateur();
                    utilisateur.setIdU(rs.getInt("idU"));
                    utilisateur.setNomU(rs.getString("nomU"));
                    utilisateur.setPrenomU(rs.getString("prenomU"));
                    utilisateur.setMailU(rs.getString("mailU"));
                    utilisateur.setTel(String.valueOf(rs.getLong("tel")));
                    utilisateur.setDateNaissance(rs.getDate("Date_naissance"));
                    utilisateur.setConnected(rs.getBoolean("is_connected")); // Correct column name
                    return utilisateur;
                }
            }
        }
        return null;
    }

    private void updateIsConnected(Connection connection, int userId, boolean isConnected) throws SQLException {
        String sql = "UPDATE utilisateur SET is_connected = ? WHERE idU = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setBoolean(1, isConnected);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
        }
    }

    private void clearFields() {
        fxidauthemail.clear();
        fxidautmdp.clear();
    }

    private void navigateToUserMenu(Utilisateur utilisateur) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../UserMenu.fxml"));
            Parent root = loader.load();

            UserMenu userMenuController = loader.getController();
            userMenuController.setUtilisateur(utilisateur); // Pass the authenticated user

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current login window
            Stage currentStage = (Stage) fxidauthemail.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de chargement", "Impossible de charger la vue UserMenu.");
            e.printStackTrace();
        }
    }

    private void navigateToAdminMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../InterfaceAdmin.fxml"));
            Parent root = loader.load();

            // You can also pass information to the AdminMenuController if necessary
            // AdminMenu adminMenuController = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current login window
            Stage currentStage = (Stage) fxidauthemail.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de chargement", "Impossible de charger la vue InterfaceAdmin.");
            e.printStackTrace();
        }
    }
}
