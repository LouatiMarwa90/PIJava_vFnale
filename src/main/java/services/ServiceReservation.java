package services;

import entities.Categorieplat;
import entities.Seance;
import util.Datasource;
import entities.Reservation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ServiceReservation implements IService <Reservation> {
    Connection con= Datasource.getInstance().getCon();

    @Override
    public void ajouter(Reservation reservation) throws SQLException {



     }

    @Override
    public void modifier(Reservation reservation) throws SQLException {



    }

    @Override
    public void supprimer(int id) throws SQLException {

    }

    @Override
    public void supprimer(Categorieplat categorie) throws SQLException {

    }

    @Override
    public Reservation  getOneById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Reservation> getAll() throws SQLException {
        return List.of();
    }
}
