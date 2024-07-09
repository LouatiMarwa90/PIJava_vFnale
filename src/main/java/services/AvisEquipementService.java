package services;

import entities.AvisEquipement;
import util.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;



public class AvisEquipementService implements IRService<AvisEquipement> {
    private Connection cnx;

    public AvisEquipementService() {
        cnx = DataSource.getInstance().getConnexion();
    }

    public void insert(AvisEquipement ae) {
        try {
            String query = "INSERT INTO avisequipement (commentaire) VALUES (?)";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setString(1, ae.getCommentaire());
                preparedStatement.executeUpdate();
                System.out.println("Avis inséré !");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de l'avis : " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erreur inattendue lors de l'insertion de l'avis : " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public void supprimer(int idA) throws SQLException {
        try {
            String ae = "DELETE FROM avisequipement WHERE idA = " + idA;
            PreparedStatement ps = cnx.prepareStatement(ae);
            Statement ste = cnx.createStatement();
            ste.executeUpdate(ae);
            System.out.println("Avis supprimé !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void modifier(AvisEquipement avisEquipement) throws SQLException {
        // Vérifier si idA existe
        String checkIdARequest = "SELECT COUNT(*) FROM avisequipement WHERE idA = ?";
        try (PreparedStatement checkIdAStatement = cnx.prepareStatement(checkIdARequest)) {
            checkIdAStatement.setInt(1, avisEquipement.getIdA());
            ResultSet rsIdA = checkIdAStatement.executeQuery();
            if (rsIdA.next() && rsIdA.getInt(1) == 0) {
                System.out.println("Erreur : idA n'existe pas.");
                return;
            }
        }

        // Mettre à jour le commentaire
        String updateRequest = "UPDATE avisequipement SET commentaire = ? WHERE idA = ?";
        try (PreparedStatement updateStatement = cnx.prepareStatement(updateRequest)) {
            updateStatement.setString(1, avisEquipement.getCommentaire());
            updateStatement.setInt(2, avisEquipement.getIdA());
            updateStatement.executeUpdate();
            System.out.println("Avis modifié !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public AvisEquipement readById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<AvisEquipement> readByEquipmentId(int idEq) throws SQLException {
        List<AvisEquipement> avisList = new ArrayList<>();

        // Vérifier si idEq existe
        String checkIdEqRequest = "SELECT COUNT(*) FROM equipment WHERE idEq = ?";
        try (PreparedStatement checkIdEqStatement = cnx.prepareStatement(checkIdEqRequest)) {
            checkIdEqStatement.setInt(1, idEq);
            ResultSet rsIdEq = checkIdEqStatement.executeQuery();
            if (rsIdEq.next() && rsIdEq.getInt(1) == 0) {
                System.out.println("Erreur : idEq n'existe pas.");
                return avisList;
            }
        }

        // Récupérer les avis pour l'équipement donné
        String query = "SELECT * FROM avisequipement WHERE idEq = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, idEq);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                AvisEquipement avisEquipement = new AvisEquipement(
                        rs.getString("commentaire"),
                        rs.getInt("idU"),
                        rs.getInt("idEq")
                );
                avisEquipement.setIdA(rs.getInt("idA"));
                avisList.add(avisEquipement);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return avisList;
    }

    @Override
    public List<AvisEquipement> getAll() throws SQLException {
        List<AvisEquipement> avisList = new ArrayList<>();
        String query = "SELECT * FROM avisequipement";
        try (Statement stmt = cnx.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                AvisEquipement avisEquipement = new AvisEquipement();
                avisEquipement.setIdA(rs.getInt("idA"));
                avisEquipement.setCommentaire(rs.getString("commentaire"));
                avisList.add(avisEquipement);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return avisList;
    }
    @Override
    public AvisEquipement getAvisById(int idA) throws SQLException {
        String query = "SELECT * FROM avisequipement WHERE idA = ?";
        AvisEquipement avisEquipement = null;
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, idA);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                avisEquipement = new AvisEquipement(
                        rs.getString("commentaire"),
                        rs.getInt("idU"),
                        rs.getInt("idEq")
                );
                avisEquipement.setIdA(rs.getInt("idA"));
            } else {
                System.out.println("Erreur : idA n'existe pas.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return avisEquipement;
    }


    public void envoyerNotif(int idU) {
        String query = "SELECT mailU FROM utilisateur WHERE idU = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, idU);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String email = rs.getString("mailU");

                // Configuration de l'email
                String from = "ghazinasri243@gmail.com"; // Change this to your email
                String password = "***********"; // Change this to your email password
                String host = "smtp.gmail.com";

                Properties properties = System.getProperties();
                properties.put("mail.smtp.host", host);
                properties.put("mail.smtp.port", "587");
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.starttls.enable", "true");

                Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                });

                try {
                    MimeMessage message = new MimeMessage(session);

                    message.setFrom(new InternetAddress(from));
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

                    message.setSubject("Nouveau commentaire ajouté");
                    message.setText("Un nouveau commentaire a été ajouté à votre équipement.");

                    Transport.send(message);
                    System.out.println("Notification envoyée avec succès à " + email);
                } catch (MessagingException mex) {
                    mex.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<AvisEquipement> starTopEqByNote() throws SQLException {
        return null;
    }

    @Override
    public void noterEquipment(int idEq, int note) throws SQLException {

    }
}