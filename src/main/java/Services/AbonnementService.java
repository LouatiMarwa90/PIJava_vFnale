package Services;

import util.DataSource;
import entites.Abonnement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class AbonnementService {
    Connection connection;

    public AbonnementService() {
        connection = DataSource.getInstance().getConnexion();
    }

    // Ajouter un abonnement
    public void ajouterS(Abonnement abonnement) throws SQLException {
        String query = "INSERT INTO abonnement (montant, dateExpiration, codePromo, typeAbonnement, idU) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setFloat(1, abonnement.getMontant());
            preparedStatement.setDate(2, new java.sql.Date(abonnement.getDateExpiration().getTime()));
            preparedStatement.setString(3, abonnement.getCodePromo());



            preparedStatement.setString(4, abonnement.getTypeAbonnement());
            preparedStatement.setInt(5, abonnement.getIdU());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                abonnement.setIdA(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating abonnement failed, no ID obtained.");
            }
        }
    }


    // Modifier un abonnement
    public void modifierS(int id, Abonnement abonnementModifie) throws SQLException {
        String query = "UPDATE abonnement SET montant = ?, dateExpiration = ?, codePromo = ?, typeAbonnement = ?, idU = ? WHERE idA = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setFloat(1, abonnementModifie.getMontant());
            preparedStatement.setDate(2, new java.sql.Date(abonnementModifie.getDateExpiration().getTime()));
            preparedStatement.setString(3, abonnementModifie.getCodePromo());
            preparedStatement.setString(4, abonnementModifie.getTypeAbonnement());
            preparedStatement.setInt(5, abonnementModifie.getIdU());
            preparedStatement.setInt(6, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Abonnement modifié avec succès : " + abonnementModifie.getIdA());
            } else {
                System.out.println("Aucun abonnement trouvé avec l'ID : " + id);
            }
        }
    }

    // Supprimer un abonnement
    public void supprimerS(int id) {
        String query = "DELETE FROM abonnement WHERE idA = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Abonnement supprimé avec succès, ID : " + id);
            } else {
                System.out.println("Aucun abonnement trouvé avec l'ID : " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Consulter un abonnement
    public Abonnement consulter(int id) {
        String query = "SELECT * FROM abonnement WHERE idA = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Abonnement abonnement = new Abonnement();
                abonnement.setIdA(resultSet.getInt("idA"));
                abonnement.setMontant(resultSet.getFloat("montant"));
                abonnement.setDateExpiration(resultSet.getDate("dateExpiration"));
                abonnement.setCodePromo(resultSet.getString("codePromo"));
                abonnement.setTypeAbonnement(resultSet.getString("typeAbonnement"));
                abonnement.setIdU(resultSet.getInt("idU"));
                return abonnement;
            } else {
                System.out.println("Aucun abonnement trouvé avec l'ID : " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Abonnement personnalisé
    public void abonnementPersonnalise(int id, String codePromo) throws SQLException {
        String query = "UPDATE abonnement SET codePromo = ? WHERE idA = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, codePromo);
            preparedStatement.setInt(2, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Code promo ajouté avec succès pour l'abonnement ID : " + id);
            } else {
                System.out.println("Aucun abonnement trouvé avec l'ID : " + id);
            }
        }
    }

    // Rappel automatique
    public void rappelAutomatique() {
        String query = "SELECT idA, dateExpiration FROM abonnement WHERE DATE(dateExpiration) = CURRENT_DATE";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idA = resultSet.getInt("idA");
                Date dateExpiration = resultSet.getDate("dateExpiration");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = sdf.format(dateExpiration);

                String message = "Votre abonnement (ID : " + idA + ") expire aujourd'hui (" + formattedDate + "). Pensez à le renouveler !";
                afficherMessageConsole(message); // Utilisation de la méthode pour afficher dans la console
                afficherMessageUI(message); // Utilisation de la méthode pour afficher dans l'interface utilisateur
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Affichage du message dans la console
    private void afficherMessageConsole(String message) {
        System.out.println(message);
    }

    // Affichage du message dans l'interface utilisateur (à implémenter)
    private void afficherMessageUI(String message) {
        // Implémentez la logique pour afficher le message dans l'interface utilisateur
        // Par exemple :
        // labelMessage.setText(message); // Si labelMessage est un contrôle JavaFX
        // ou
        // showAlert(Alert.AlertType.INFORMATION, "Rappel d'abonnement", message); // Pour afficher une alerte dans JavaFX
    }


    // Paiement abonnement
    public void paiementAbonnement(int id) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        // Afficher un message de confirmation avec l'ID de l'abonnement
        System.out.println("Voulez-vous vraiment effectuer le paiement de l'abonnement ID " + id + " ? (Oui/Non)");

        // Lire l'entrée de l'utilisateur
        String reponse = scanner.nextLine();

        // Vérifier si l'utilisateur a confirmé le paiement
        if (reponse.equalsIgnoreCase("Oui")) {
            // Logique de paiement de l'abonnement (e.g., via une API de paiement)
            System.out.println("Paiement de l'abonnement ID " + id + " effectué avec succès !");
        } else {
            // Afficher un message si l'utilisateur a annulé le paiement
            System.out.println("Le paiement de l'abonnement ID " + id + " a été annulé.");
        }
    }

    // Méthode pour récupérer tous les abonnements
    public List<Abonnement> trouverTous() {
        List<Abonnement> abonnements = new ArrayList<>();
        String query = "SELECT * FROM abonnement";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Abonnement abonnement = new Abonnement();
                abonnement.setIdA(resultSet.getInt("idA"));
                abonnement.setMontant(resultSet.getFloat("montant"));
                abonnement.setDateExpiration(resultSet.getDate("dateExpiration"));
                abonnement.setCodePromo(resultSet.getString("codePromo"));
                abonnement.setTypeAbonnement(resultSet.getString("typeAbonnement"));
                abonnement.setIdU(resultSet.getInt("idU"));
                abonnements.add(abonnement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return abonnements;
    }
}
