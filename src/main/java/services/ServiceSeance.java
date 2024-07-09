
package services;

import entities.Seance;
import util.*;
import java.sql.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceSeance implements IService <Seance>{

   Connection cnx=Datasource.getInstance().getCon();

    @Override
    public void ajouter(Seance seance) throws SQLException {
        String req=" INSERT INTO `seance` (Ids,`NomS`,`Date`,`Duree`,`Coach`,`salle`,`nb_place`) VALUES (?,?,?,?,?,?,?)";

        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1,seance.getIdS());
        ps.setString(2,seance.getNomS());
        ps.setString(3, seance.getDate());
        ps.setInt(4,seance.getDuree());
        ps.setString(5,seance.getCoach());
        ps.setString(6,seance.getSalle());
        ps.setInt(7,seance.getNb_place());
        ps.executeUpdate();
        System.out.println("Seance added !");    }

    @Override
    public void modifier(Seance seance) throws SQLException {
        String req = "UPDATE `seance` SET nomS=?, date = ?, duree = ?, coach =?, salle=?,nb_place=? WHERE idS = ?";

        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1,seance.getNomS());
        ps.setString(2,seance.getDate());
        ps.setInt(3, seance.getDuree());
        ps.setString(4,seance.getCoach());
        ps.setString(5,seance.getSalle());
        ps.setInt(6,seance.getNb_place());
        ps.setInt(7,seance.getIdS());
        ps.executeUpdate();
        System.out.println("Seance modifier !");

    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM `seance` WHERE ids = ?";

        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);
        ps.executeUpdate();
        System.out.println("Seance supprimée !");


    }

    @Override
    public Seance getOneById(int id)  throws SQLException{
        Seance seance = null;
        String req = "SELECT * FROM seance WHERE idseance = ?";
        ResultSet res;
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            res = ps.executeQuery();
        }
        if (res.next()) {
            String nom = res.getString("Nom");
            String date = res.getString("Date");
            int duree = res.getInt("Duree");
            String coach= res.getString("Coach");
            String salle = res.getString("SAlle");
            int nb_place= res.getInt("Nb_place");

            seance = new Seance(id,nom,date, duree, coach,salle,nb_place);
        }

        return seance;
    }

    @Override
    public List<Seance> getAll()  throws SQLException{
        List <Seance> seances = new ArrayList<>();

        String req = "Select * from seance";

        Statement st = cnx.createStatement();
        ResultSet res = st.executeQuery(req);
        while (res.next()){
            int id =res.getInt(1);
            String nomS = res.getString(2);
           String date = res.getString(3);
            int duree = res.getInt(4);
            String coach= res.getString(5);
            String salle = res.getString(6);
            int nb_place= res.getInt(7);

            Seance seance = new Seance(id, nomS,date,duree,coach,salle,nb_place);
            seances.add(seance);
        }



        return seances;
    }
    public Seance getSeanceByNom(String nomSeance) {
        try {
            String query = "SELECT * FROM seance WHERE nom = ?";
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setString(1, nomSeance);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id =rs.getInt(1);
                String nomS = rs.getString(2);
                String date = rs.getString(3);
                int duree = rs.getInt(4);
                String coach= rs.getString(5);
                String salle = rs.getString(6);
                int nb_place= rs.getInt(7);
                // Créez et retournez un objet Seance avec les données récupérées
                return new Seance(id,nomS,date,duree,coach,salle,nb_place);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception
        }
        return null; // Retourner null si la séance n'a pas été trouvée

    }
    public List<Seance> getSeanceByAll(String text) {
        List<Seance> mylist = new ArrayList<Seance>();
        try {

            String query = "SELECT * FROM seance WHERE nomS LIKE ? OR coach LIKE ? ";
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setString(1, "%" + text + "%");
            ps.setString(2, "%" + text + "%");
          //  ps.setString(3, "%" + text + "%");
           /* ps.setString(4, "%" + text + "%");
            ps.setString(5, "%" + text + "%");
            ps.setString(6, "%" + text + "%");*/
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String nomS = rs.getString(2);
                String date = rs.getString(3);
                int duree = rs.getInt(4);
                String coach = rs.getString(5);
                String salle = rs.getString(6);
                int nb_place = rs.getInt(7);
                // Créez et retournez un objet Seance avec les données récupérées
                Seance s = new Seance(id, nomS, date, duree, coach, salle, nb_place);
                mylist.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception
        }
        return mylist;
    }

    public List<Seance> getSeanceOrderBy(String text) {
        List<Seance> mylist = new ArrayList<Seance>();
        System.out.println(text);
        try {

            String query = "SELECT * FROM seance ORDER BY " +text ;
            PreparedStatement ps = cnx.prepareStatement(query);
           // System.out.println("nnn"+text.equals("coach"));
           //ps.setString(1, text);
           // ps.setString(2, "%" + text + "%");
            //  ps.setString(3, "%" + text + "%");
           /* ps.setString(4, "%" + text + "%");
            ps.setString(5, "%" + text + "%");
            ps.setString(6, "%" + text + "%");*/
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String nomS = rs.getString(2);
                String date = rs.getString(3);
                int duree = rs.getInt(4);
                String coach = rs.getString(5);
                String salle = rs.getString(6);
                int nb_place = rs.getInt(7);
                // Créez et retournez un objet Seance avec les données récupérées
                Seance s = new Seance(id, nomS, date, duree, coach, salle, nb_place);
             //  System.out.println(s);
                mylist.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception
        }
        return mylist;
    }

    public int countNomS(String text) throws SQLException
    {
        String req = "SELECT COUNT(*) FROM seance WHERE nomS = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, text);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
