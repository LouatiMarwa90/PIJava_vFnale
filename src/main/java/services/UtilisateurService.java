// UtilisateurService.java
package Services;

import util.DataSource;
import entites.Utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurService {
    static Connection connection;

    public UtilisateurService() {
        connection = DataSource.getInstance().getConnexion();
    }

    // Ajouter un utilisateur
    public void ajouter(Utilisateur utilisateur) throws SQLException {
        String query = "INSERT INTO utilisateur (nomU, prenomU, mdp, mailU, tel, statut, Date_naissance) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, utilisateur.getNomU());
            preparedStatement.setString(2, utilisateur.getPrenomU());
            preparedStatement.setString(3, utilisateur.getMdp());
            preparedStatement.setString(4, utilisateur.getMailU());
            preparedStatement.setString(5, utilisateur.getTel()); // Change here: tel as String
            preparedStatement.setBoolean(6, utilisateur.isStatut());
            preparedStatement.setDate(7, new java.sql.Date(utilisateur.getDateNaissance().getTime()));
            preparedStatement.executeUpdate();
            System.out.println("Utilisateur ajouté : " + utilisateur.getNomU());
        }
    }

    // Supprimer un utilisateur
    public void supprimer(int id) {
        String query = "DELETE FROM utilisateur WHERE idU = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Utilisateur supprimé avec succès, ID : " + id);
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'ID : " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Utilisateur consulter(String email) {
        String query = "SELECT * FROM utilisateur WHERE mailU = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setIdU(resultSet.getInt("idU"));
                utilisateur.setNomU(resultSet.getString("nomU"));
                utilisateur.setPrenomU(resultSet.getString("prenomU"));
                utilisateur.setMdp(resultSet.getString("mdp"));
                utilisateur.setMailU(resultSet.getString("mailU"));
                utilisateur.setTel(resultSet.getString("tel"));
                utilisateur.setStatut(resultSet.getBoolean("statut"));
                utilisateur.setDateNaissance(resultSet.getDate("Date_naissance"));
                return utilisateur;
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'email : " + email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    // Rechercher des utilisateurs par nom
    public List<Utilisateur> rechercher(String nom) {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String query = "SELECT * FROM utilisateur WHERE nomU LIKE ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "%" + nom + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setIdU(resultSet.getInt("idU"));
                utilisateur.setNomU(resultSet.getString("nomU"));
                utilisateur.setPrenomU(resultSet.getString("prenomU"));
                utilisateur.setMdp(resultSet.getString("mdp"));
                utilisateur.setMailU(resultSet.getString("mailU"));
                utilisateur.setTel(String.valueOf(Long.parseLong(resultSet.getString("tel")))); // Change here: tel as String
                utilisateur.setStatut(resultSet.getBoolean("statut"));
                utilisateur.setDateNaissance(resultSet.getDate("Date_naissance"));
                utilisateurs.add(utilisateur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateurs;
    }

    // Trier les utilisateurs par nom
    public List<Utilisateur> trierParNom() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String query = "SELECT * FROM utilisateur ORDER BY nomU";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setIdU(resultSet.getInt("idU"));
                utilisateur.setNomU(resultSet.getString("nomU"));
                utilisateur.setPrenomU(resultSet.getString("prenomU"));
                utilisateur.setMdp(resultSet.getString("mdp"));
                utilisateur.setMailU(resultSet.getString("mailU"));
                utilisateur.setTel(resultSet.getString("tel")); // Change here: tel as String
                utilisateur.setStatut(resultSet.getBoolean("statut"));
                utilisateur.setDateNaissance(resultSet.getDate("Date_naissance"));
                utilisateurs.add(utilisateur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateurs;
    }

    // Vérifier si l'ID existe
    public boolean verifId(int id) {
        String query = "SELECT 1 FROM utilisateur WHERE idU = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Oublier mot de passe (mise à jour du mot de passe)
    public void oublierMdp(int id, String nouveauMdp) {
        String query = "UPDATE utilisateur SET mdp = ? WHERE idU = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nouveauMdp);
            preparedStatement.setInt(2, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Mot de passe mis à jour avec succès pour l'utilisateur ID : " + id);
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'ID : " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Modifier un utilisateur
    public static void modifier(int id, Utilisateur utilisateurModifie) throws SQLException {
        String query = "UPDATE utilisateur SET nomU = ?, prenomU = ?, mdp = ?, mailU = ?, tel = ?, statut = ?, Date_naissance = ? WHERE idU = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, utilisateurModifie.getNomU());
            preparedStatement.setString(2, utilisateurModifie.getPrenomU());
            preparedStatement.setString(3, utilisateurModifie.getMdp());
            preparedStatement.setString(4, utilisateurModifie.getMailU());
            preparedStatement.setString(5, utilisateurModifie.getTel()); // Change here: tel as String
            preparedStatement.setBoolean(6, utilisateurModifie.isStatut());
            preparedStatement.setDate(7, new java.sql.Date(utilisateurModifie.getDateNaissance().getTime()));
            preparedStatement.setInt(8, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Utilisateur modifié avec succès : " + utilisateurModifie.getNomU());
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'ID : " + id);
            }
        }
    }
}
