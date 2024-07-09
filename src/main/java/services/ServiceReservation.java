package services;

import entities.*;
import util.*;
import java.sql.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceReservation implements IService <Reservation> {


    @Override
    public void ajouter(Reservation reservation) throws SQLException {
        String req="INSERT INTO reservations (nom,prenom,email,sexe,etat,idU) VALUES (?,?,?,?,?,?)";
        //String req=" INSERT INTO reservation (idRS,`nom`,`prenom`) VALUES (?,?,?)";
        PreparedStatement ps= cnx.prepareStatement(req);
        //ps.setInt(1,reservation.getIdRS());
        ps.setString(1,reservation.getName());
        ps.setString(2,reservation.getPrenom());
        ps.setString(3,reservation.getEmail());

        ps.setString(4,String.valueOf(reservation.getSexe()));
        ps.setString(5,String.valueOf(reservation.getEtat()));
        ps.setInt(6, reservation.getIdU().getIdU());
        int i=ps.executeUpdate();
        System.out.println("RESERVATION AJOUTER !"+i);




    }

    @Override
    public void modifier(Reservation reservation) throws SQLException {
        String req= "UPDATE `reservations`set nom=?,prenom=?,email=?,tel=?,sexe=?,etat=?,idU=? WHERE idRS=?";

        PreparedStatement ps= cnx.prepareStatement(req);

        ps.setString(1,reservation.getName());
        ps.setString(2,reservation.getPrenom());
        ps.setString(3,reservation.getEmail());
        ps.setFloat(4,reservation.getTel());
        ps.setString(5,String.valueOf(reservation.getSexe()));
        ps.setString(6,String.valueOf(reservation.getEtat()));
        ps.setInt(7,reservation.getIdU().getIdU());
        ps.setInt(8,reservation.getIdRS());
        int i=ps.executeUpdate();
        System.out.println("RESERVATION MODIFIER !"+i);


    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM `reservations` WHERE idRS = ?";

        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);
        int i=ps.executeUpdate();
        System.out.println("RESERVATION SUPPRIMEE !"+i);

    }

    @Override
    public Reservation getOneById(int id) throws SQLException {
        Reservation reservation = null;
        String req = "SELECT * FROM seance WHERE idRS = ?";
        ResultSet res;
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            res = ps.executeQuery();
        }
        if (res.next()) {
            int idRS=res.getInt("idRS");
            String nom=res.getString("nom");
            String prenom=res.getString("prenom");
            String email=res.getString("email");
            float tel=res.getFloat("tel");
            Reservation.sexe s= Reservation.sexe.valueOf(res.getString("sexe"));
            Reservation.etat e=Reservation.etat.valueOf(res.getString("etat"));
            int idU=res.getInt("idU");
Utilisateur u=new Utilisateur();
u.setIdU(idU);

            reservation = new Reservation(idRS,nom,prenom,email,s,tel,e,u);
            
        }

        return reservation;
    }


    @Override
    public List<Reservation> getAll() throws SQLException {
        List <Reservation> reservations = new ArrayList<>();

        String req = "Select * from reservations";

        Statement st = cnx.createStatement();
        ResultSet res = st.executeQuery(req);
        while (res.next()){
            int idRS =res.getInt(1);
            String nom = res.getString(2);
            String prenom = res.getString(3);
            String email = res.getString(4);
            float tel= res.getFloat(5);
           Reservation.sexe s= Reservation.sexe.valueOf(res.getString(6));
           Reservation.etat e = Reservation.etat.valueOf(res.getString(7));
            int idU=res.getInt(8);
            Utilisateur u=new Utilisateur();
            u.setIdU(idU);

            Reservation reservation=new Reservation(idRS,nom,prenom,email,s,tel,e,u);
            reservations.add(reservation);
        }

        return reservations;
    }

    public List<Utilisateur> getAllUser() throws SQLException {
        List <Utilisateur> users = new ArrayList<Utilisateur>();

        String req = "Select idU,nomU,prenomU,mailU,sexe from utilisateur";

        Statement st = cnx.createStatement();
        ResultSet res = st.executeQuery(req);
        while (res.next()){
            int idU  =res.getInt(1);
            String nom = res.getString(2);
            String prenom = res.getString(3);
            String email = res.getString(4);
            String sexe= res.getString(5);

            Utilisateur u=new Utilisateur(idU,nom,prenom,email,sexe);


            users.add(u);
        }

        return users;
    }
    public Utilisateur getUserbyId(int id) throws SQLException {
        Utilisateur u=new Utilisateur();

        String req = "Select nomU,prenomU,mailU,sexe from utilisateur where  idU=?";

        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);
        ResultSet res = ps.executeQuery();
        while (res.next()){
           // int idU  =res.getInt(1);
            String nom = res.getString(1);
            String prenom = res.getString(2);
            String email = res.getString(3);
            String sexe= res.getString(4);

           u=new Utilisateur(nom,prenom,email,sexe);



        }
return u;

    }
    public List<Reservation> getAllJust() throws SQLException {
        List <Reservation> reservations = new ArrayList<>();

        String req = "Select idU,nom,prenom,email,sexe,etat from reservations";

        Statement st = cnx.createStatement();
        ResultSet res = st.executeQuery(req);
        while (res.next()){
            int idU =res.getInt(1);
            String nom = res.getString(2);
            String prenom = res.getString(3);
            String email = res.getString(4);

            Reservation.sexe s= Reservation.sexe.valueOf(res.getString(5));
            Reservation.etat e = Reservation.etat.valueOf(res.getString(6));

            Utilisateur u=new Utilisateur();
            u.setIdU(idU);

            Reservation reservation=new Reservation(nom,prenom,email,s,e,u);
           System.out.println("hi"+reservation.getName());
            reservations.add(reservation);
        }

        return reservations;
    }

}
