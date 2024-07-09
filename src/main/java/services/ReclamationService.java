package services;

import entities.Reclamation;
import util.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReclamationService implements IRService<Reclamation> {

    private Connection cnx;
    public ReclamationService() {
        cnx= DataSource.getInstance().getConnexion();
    }
    @Override
    public  void insert(Reclamation r) {
        try {
            // Votre code de connexion et d'exécution SQL ici
            String query = "INSERT INTO reclamation (categorieR, description, IdU, etat) VALUES (?, ?, ?, 'en cours')";
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setString(1, r.getCategorieR());
            preparedStatement.setString(2, r.getDescription());
            preparedStatement.setInt(3, r.getIdU());
            //preparedStatement.setString(4, r.getEtat());


            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void supprimer(int idRec) {
        try {
            String req = "UPDATE reclamation SET deleted = true WHERE idRec = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idRec);
            ps.executeUpdate();
            System.out.println("Réclamation marquée comme supprimée pour ID : " + idRec);
        } catch (SQLException e) {
            System.err.println("Erreur lors du marquage de la réclamation comme supprimée : " + e.getMessage());
        }
    }
    public List<Reclamation> getAllNonDeleted() throws SQLException {
        List<Reclamation> reclamations = new ArrayList<>();
        String req = "SELECT * FROM reclamation WHERE deleted = false";
        Statement ste = cnx.createStatement();
        ResultSet rs = ste.executeQuery(req);
        while (rs.next()) {
            // Lire les données et créer les objets Reclamation
            Reclamation rec = new Reclamation();
            rec.setIdRec(rs.getInt("idRec"));
            rec.setCategorieR(rs.getString("categorieR"));
            rec.setDescription(rs.getString("description"));
            rec.setIdU(rs.getInt("idU"));
            rec.setEtat(rs.getString("etat"));
            rec.setDeleted(rs.getBoolean("deleted"));

            reclamations.add(rec);
        }
        return reclamations;
    }
    @Override
    public void modifier(Reclamation reclamation) throws SQLException {
        // Requête pour obtenir l'état actuel de la réclamation
        String selectRequest = "SELECT etat FROM reclamation WHERE idREC = ?";
        try (PreparedStatement selectStatement = cnx.prepareStatement(selectRequest)) {
            selectStatement.setInt(1, reclamation.getIdRec());
            ResultSet rs = selectStatement.executeQuery();
            if (rs.next()) {
                String currentEtat = rs.getString("etat");
                // Vérifiez si l'état actuel est "en cours"
                if ("en cours".equals(currentEtat)) {
                    // Requête pour mettre à jour la réclamation
                    String updateRequest = "UPDATE reclamation SET categorieR = ?, description = ?, IdU = ?, etat = ? WHERE idREC = ?";
                    try (PreparedStatement updateStatement = cnx.prepareStatement(updateRequest)) {
                        updateStatement.setString(1, reclamation.getCategorieR());
                        updateStatement.setString(2, reclamation.getDescription());
                        updateStatement.setInt(3, reclamation.getIdU());
                        updateStatement.setString(4, reclamation.getEtat());
                        updateStatement.setInt(5, reclamation.getIdRec());

                        updateStatement.executeUpdate();
                        System.out.println("Réclamation modifiée !");
                    }
                } else {
                    System.out.println("La réclamation ne peut être modifiée que si son état est 'en cours'.");
                }
            } else {
                System.out.println("Réclamation non trouvée.");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Reclamation readById(int id) {
        String requete="select * from reclamation where idRec="+id;
        Reclamation r=null;
        try {
            Statement ste=cnx.createStatement();
            ResultSet rs=ste.executeQuery(requete);
            if(rs.next())
                r=new Reclamation(rs.getNString(2),
                        rs.getString(3),rs.getInt(4),rs.getString(5));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return r;
    }
@Override
public List<Reclamation> readByEtat(String etat) {
    String requete = "SELECT * FROM reclamation WHERE etat = ? AND deleted = false";
    List<Reclamation> reclamations = new ArrayList<>();
    try {
        PreparedStatement ps = cnx.prepareStatement(requete);
        ps.setString(1, etat);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Reclamation r = new Reclamation(
                    rs.getNString("categorieR"),
                    rs.getString("description"),
                    rs.getInt("idU"),
                    rs.getString("etat")
            );
            reclamations.add(r);
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    return reclamations;
}

    @Override
    public List<Reclamation> getAll() throws SQLException {
        String request = "SELECT * FROM reclamation";
        List<Reclamation> reclamations = new ArrayList<>();
        try (Statement statement = cnx.createStatement();
             ResultSet resultSet = statement.executeQuery(request)) {
            while (resultSet.next()) {
                Reclamation Rec = new Reclamation();
                Rec.setIdRec(resultSet.getInt("idRec"));
                Rec.setCategorieR(resultSet.getString("categorieR"));
                Rec.setDescription(resultSet.getString("description"));
                Rec.setIdU(resultSet.getInt("idU"));
                Rec.setEtat(resultSet.getString("etat"));
                reclamations.add(Rec);
            }
        }
        return reclamations;
    }

    @Override
    public  void envoyerNotificationEmail(int idRec, String nouvelEtat, String userEmail, String reponse){}    // Méthodes pour obtenir les statistiques des réclamations
    public int getTotalReclamations() {
        String query = "SELECT COUNT(*) FROM reclamation";
        try (Statement statement = cnx.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int getTotalReclamationsEnCours() {
        String query = "SELECT COUNT(*) FROM reclamation WHERE etat = 'en cours'";
        try (Statement statement = cnx.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int getTotalReclamationsTraitees() {
        String query = "SELECT COUNT(*) FROM reclamation WHERE etat = 'traité'";
        try (Statement statement = cnx.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Reclamation> getAllByConnectedUser(int connectedUserId) throws SQLException {
        String request = "SELECT * FROM reclamation WHERE idU = ? AND  deleted = false";
        List<Reclamation> reclamations = new ArrayList<>();
        try (PreparedStatement statement = cnx.prepareStatement(request)) {
            statement.setInt(1, connectedUserId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Reclamation rec = new Reclamation();
                    rec.setIdRec(resultSet.getInt("idRec"));
                    rec.setCategorieR(resultSet.getString("categorieR"));
                    rec.setDescription(resultSet.getString("description"));
                    rec.setIdU(resultSet.getInt("idU"));
                    rec.setEtat(resultSet.getString("etat"));
                    reclamations.add(rec);
                }
            }
        }
        return reclamations;
    }


}


