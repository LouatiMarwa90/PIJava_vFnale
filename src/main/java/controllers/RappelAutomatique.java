package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RappelAutomatique {

    private Connection connection; // Assurez-vous que cette connexion est initialisée et injectée correctement

    // Setter pour la connexion
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    // Méthode déclenchée lorsque le bouton est cliqué
    @FXML
    void buttonExpire(ActionEvent event) {
        if (connection == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur de connexion", "Connexion à la base de données non disponible.");
            return;
        }

        String query = "SELECT idA, dateExpiration FROM abonnement WHERE DATE(dateExpiration) = CURRENT_DATE";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            boolean abonnementsTrouves = false;
            while (resultSet.next()) {
                int idA = resultSet.getInt("idA");
                Date dateExpiration = resultSet.getDate("dateExpiration");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = sdf.format(dateExpiration);

                String message = "Votre abonnement (ID : " + idA + ") expire aujourd'hui (" + formattedDate + "). Pensez à le renouveler !";
                showAlert(Alert.AlertType.INFORMATION, "Rappel d'Abonnement", message);
                abonnementsTrouves = true;
            }

            if (!abonnementsTrouves) {
                showAlert(Alert.AlertType.INFORMATION, "Rappel d'Abonnement", "Aucun abonnement n'expire aujourd'hui.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors du rappel automatique.");
        }
    }

    // Méthode pour afficher un message dans une boîte de dialogue
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
