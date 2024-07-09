package services;

import javax.mail.Session;

import entities.Reclamation;
import entities.Reponse;
import entities.Utilisateur;
import util.DataSource;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class ReponseService implements IRService<Reponse> {

    private Connection cnx;

    public ReponseService() {
        cnx = DataSource.getInstance().getConnexion();
    }


    public void updateReclamationEtat(int idRec, String nouvelEtat) {
        try {
            String query = "UPDATE reclamation SET etat = ? WHERE idRec = ?";
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setString(1, nouvelEtat);
            preparedStatement.setInt(2, idRec);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void insert(Reponse r) {
        try {
            // Insertion de la réponse
            String query = "INSERT INTO reponse (reponse, idRec) VALUES (?, ?)";
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setString(1, r.getReponse());
            preparedStatement.setInt(2, r.getIdRec());

            preparedStatement.executeUpdate();

            // Mise à jour de l'état de la réclamation
            String updateQuery = "UPDATE reclamation SET etat = 'traité' WHERE idRec = ? AND etat = 'en cours'";
            PreparedStatement updateStatement = cnx.prepareStatement(updateQuery);
            updateStatement.setInt(1, r.getIdRec());

            updateStatement.executeUpdate();

            // Récupération de l'utilisateur connecté
            UtilisateurService utilisateurService = new UtilisateurService();
            Optional<Utilisateur> optionalUser = utilisateurService.getConnectedUser();

            if (optionalUser.isPresent()) {
                Utilisateur connectedUser = optionalUser.get();
                String userEmail = connectedUser.getMailU();

                // Envoi de la notification par email avec la réponse
                envoyerNotificationEmail(r.getIdRec(), "traité", userEmail, r.getReponse());
            } else {
                System.out.println("Aucun utilisateur connecté trouvé.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
        public void supprimer ( int idRep) throws SQLException {
            try {
                String req = "DELETE FROM reponse WHERE idRep = " + idRep;

                PreparedStatement ps  = cnx.prepareStatement(req);
                Statement ste  = cnx.createStatement();
                ste.executeUpdate(req);
                System.out.println("Traitement supprimé !");

            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }


        }

    @Override
    public void modifier(Reponse reponse) throws SQLException {
        String request = "UPDATE reponse SET reponse = ? WHERE idRep = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(request)) {
            preparedStatement.setString(1, reponse.getReponse());
            preparedStatement.setInt(2, reponse.getIdRep());
            preparedStatement.executeUpdate();
        }
    }



        @Override
        public Reponse readById ( int idRep) throws SQLException {

            return null;
        }


        @Override
        public  List<Reponse> getAll() throws SQLException {
            String request = "SELECT * FROM reponse";
            List<Reponse> reponses = new ArrayList<>();
            try (Statement statement = cnx.createStatement();
                 ResultSet resultSet = statement.executeQuery(request)) {
                while (resultSet.next()) {
                    Reponse Rep = new Reponse();
                    Rep.setIdRep(resultSet.getInt("idRep"));
                    Rep.setReponse(resultSet.getString("reponse"));
                    Rep.setIdRec(resultSet.getInt("idRec"));

                    reponses.add(Rep);
                }
            }
            return reponses;
        }


@Override
    public void envoyerNotificationEmail(int idRec, String nouvelEtat, String userEmail, String reponse) {
        String to = userEmail;
        String subject = "Réponse sur réclamation";
        String content = "Votre réclamation a été bien traitée.\n\n" +
                "Voici la réponse :\n" + reponse;

        String from = "mounahamrouni43@gmail.com"; // Votre adresse email
        String host = "smtp.gmail.com"; // Serveur SMTP

        final String username = "mounahamrouni43@gmail.com"; // Votre adresse email
        final String password = "hngd yupj qcpp bzdx"; // Votre mot de passe email

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587"); // Port SMTP, ajustez si nécessaire

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(content);

            // Envoyer le message
            Transport.send(message);

            System.out.println("Email envoyé avec succès !");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public java.util.List<Reclamation> readByEtat(String etat) {
        return null;
    }
}






