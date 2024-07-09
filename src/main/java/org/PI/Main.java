package org.PI;
import util.*;
import entities.*;
import services.*;
import java.sql.*;
import java.util.List;


public class Main {
    public static void main(String[] args)  {


     // Datasource datasource=new Datasource();
        ServiceSeance ps =new ServiceSeance();
        Seance s1 =new Seance("seance1","01/02/2024",30,"jlassi","salle1",20);
//        Utilisateur u1 =new Utilisateur();
//        u1.setIdU(2);
//        Reservation r2=new Reservation(5,"moemen","jlassi","test.test@esprit.tn", Reservation.sexe.HOMME,99368498, "",u1);


            //ps.ajouter(s1);
            List<Seance> list= ps.getSeanceOrderBy("nomS");
        try {
            System.out.println(ps.countNomS("BOXE"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //ps.supprimer(5);


    }
}