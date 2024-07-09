package services;

import entities.Categorieplat;
import util.Datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceCategorieplat implements IService<Categorieplat> {

    private Connection con = Datasource.getInstance().getCon();

    @Override
    public void ajouter(Categorieplat categorie) throws SQLException {
        String req = "INSERT INTO Categorieplat (nomC, descC, etatC) VALUES (?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(req);
        ps.setString(1, categorie.getNomC());
        ps.setString(2, categorie.getDescC());
        ps.setBoolean(3, categorie.getetatC());
        ps.executeUpdate();
    }

    @Override
    public void modifier(Categorieplat categorie) throws SQLException {
        String req = "UPDATE Categorieplat SET nomC = ?, descC = ?, etatC = ? WHERE idC = ?";
        PreparedStatement ps = con.prepareStatement(req);
        ps.setString(1, categorie.getNomC());
        ps.setString(2, categorie.getDescC());
        ps.setBoolean(3, categorie.getetatC());
        ps.setInt(4, categorie.getIdC());
        ps.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {

    }

    //  @Override
  //  public void supprimer(int id) throws SQLException {

  //  }

    @Override
    public void supprimer(Categorieplat categorie) throws SQLException {
        String req = "DELETE FROM Categorieplat WHERE idC = ?";
        PreparedStatement ps = con.prepareStatement(req);
        ps.setInt(1, categorie.getIdC());
        ps.executeUpdate();
    }

    @Override
    public Categorieplat getOneById(int idC) throws SQLException {
        String req = "SELECT * FROM Categorieplat WHERE idC = ?";
        PreparedStatement ps = con.prepareStatement(req);
        ps.setInt(1, idC);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new Categorieplat(
                    rs.getInt("idC"),
                    rs.getString("nomC"),
                    rs.getString("descC"),
                    rs.getBoolean("etatC")
            );
        }
        return null;
    }

    @Override
    public List<Categorieplat> getAll() throws SQLException {
        String req = "SELECT * FROM Categorieplat";
        PreparedStatement ps = con.prepareStatement(req);
        ResultSet rs = ps.executeQuery();
        List<Categorieplat> categories = new ArrayList<>();
        while (rs.next()) {
            categories.add(new Categorieplat(
                    rs.getInt("idC"),
                    rs.getString("nomC"),
                    rs.getString("descC"),
                    rs.getBoolean("etatC")
            ));
        }
        return categories;
    }
}