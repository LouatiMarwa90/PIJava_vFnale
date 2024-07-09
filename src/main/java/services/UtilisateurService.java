package services;

import entities.Utilisateur;
import util.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class UtilisateurService {
    private Connection cnx;

    public UtilisateurService() {
        cnx = DataSource.getInstance().getConnexion();
    }

    public Optional<Utilisateur> getConnectedUser() {
        String query = "SELECT * FROM utilisateur WHERE is_connected = 1 LIMIT 1";
        try {
            Statement statement = cnx.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                Utilisateur user = new Utilisateur();
                user.setIdU(resultSet.getInt("idU"));
                user.setNomU(resultSet.getString("nomU"));
                user.setPrenomU(resultSet.getString("prenomU"));
                user.setMdp(resultSet.getString("mdp"));
                user.setMailU(resultSet.getString("mailU"));
                user.setTel(resultSet.getLong("tel"));
                user.setStatut(resultSet.getBoolean("statut"));
                user.setDateNaissance(resultSet.getDate("Date_naissance")); // Vérifiez ici
                user.setIs_connected(resultSet.getInt("is_connected"));
                return Optional.of(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}

