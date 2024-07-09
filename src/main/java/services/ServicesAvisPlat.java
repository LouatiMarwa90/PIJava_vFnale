package services;
import entities.AvisPlat;
import entities.Categorieplat;
import util.Datasource;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
public class ServicesAvisPlat implements IService<AvisPlat> {
  private Connection con = Datasource.getInstance().getCon();

  @Override
  public void ajouter(AvisPlat avis) throws SQLException {
    String req = "INSERT INTO avisp (commAP, star, idPlat , idAP ) VALUES (?, ?, ?, ?)";
    PreparedStatement ps = con.prepareStatement(req);
    ps.setString(1, avis.getCommAP());
    ps.setInt(2, avis.getStar());
    ps.setInt(3, (int) avis.getIdPlat().getId());
    ps.setInt(4, avis.getIdAP());
    ps.executeUpdate();
    System.out.println("avis ajoute au Plat");

  }


  @Override
  public void supprimer(int idap) throws SQLException {
    String req = "DELETE FROM avisp WHERE idAP = ?";
    PreparedStatement ps = con.prepareStatement(req);
    ps.setInt(1, idap);

    int rowsDeleted = ps.executeUpdate();
    if (rowsDeleted > 0) {
      System.out.println("avis supprime !");
    } else {
      System.out.println("id incorrect");
    }

  }

  @Override
  public void supprimer(Categorieplat categorie) throws SQLException {

  }


  @Override
  public void modifier(AvisPlat avis) throws SQLException {
    String req = "UPDATE avisp SET commAP = ?, star = ? WHERE idAP = ?";

    PreparedStatement ps = con.prepareStatement(req);
    ps.setString(1, avis.getCommAP());
    ps.setInt(2, avis.getStar());
    ps.setInt(3, avis.getIdAP());

    int rowsUpdated = ps.executeUpdate();
    if (rowsUpdated > 0) {
      System.out.println("avis modifie");
    } else {
      System.out.println("ressayez");
    }

  }

  @Override
  public AvisPlat getOneById(int id) {
    AvisPlat avis = null;
    String req = "SELECT * FROM avisp WHERE idAP = ?";
    try {
      PreparedStatement ps = con.prepareStatement(req);
      ps.setInt(1, id);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        int idAP = rs.getInt("idAP");
        String commAP = rs.getString("commAP");
        int star=rs.getInt("star");

        avis = new AvisPlat( idAP, commAP, star);
      }
    } catch (SQLException e) {
      System.out.println("Error retrieving review: " + e.getMessage());
    }
    return avis;
  }

  @Override
  public List<AvisPlat> getAll() {
    List<AvisPlat> avisList = new ArrayList<>();
    String req = "SELECT * FROM avisp";
    try {
      PreparedStatement ps = con.prepareStatement(req);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        int idAP = rs.getInt("idAP");
        String commAP = rs.getString("commAP");
        int star = rs.getInt("star");

        AvisPlat avis = new AvisPlat(idAP, commAP, star);
        avisList.add(avis);
      }
    } catch (SQLException e) {
      System.out.println("Error retrieving reviews: " + e.getMessage());
    }
    return avisList;
  }

  public void deleteByPlatId(int platId) throws SQLException {
    String query = "DELETE FROM avisplat WHERE idPlat = ?";
    try (PreparedStatement statement = con.prepareStatement(query)) {
      statement.setInt(1, platId);
      statement.executeUpdate();
    }
  }


  public Set<AvisPlat> getAllP(int ID) {
    Set<AvisPlat> avisList = new HashSet<>();
    String req = "SELECT * FROM avisp WHERE idPlat = ?";
    try {
      PreparedStatement ps = con.prepareStatement(req);
      ps.setInt(1, ID);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        int idAP = rs.getInt("idAP");
        String commAP = rs.getString("commAP");
        int star = rs.getInt("star");

        AvisPlat avis = new AvisPlat(idAP, commAP, star);
        avisList.add(avis);
      }
    } catch (SQLException e) {
      System.out.println("Error retrieving reviews: " + e.getMessage());
    }
    return avisList;
  }

  public Set<AvisPlat> getAllU(int ID) {
    Set<AvisPlat> avisList = new HashSet<>();
    String req = "SELECT * FROM avisp WHERE iduap = ?";
    try {
      PreparedStatement ps = con.prepareStatement(req);
      ps.setInt(1, ID);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        int idAP = rs.getInt("idAP");
        String commAP = rs.getString("commAP");
        int star = rs.getInt("star");

        AvisPlat avis = new AvisPlat(idAP, commAP, star);
        avisList.add(avis);
      }
    } catch (SQLException e) {
      System.out.println("Error retrieving reviews: " + e.getMessage());
    }
    return avisList;
  }

  public double calculateAverageStarRating(int platId) throws SQLException {
    String query = "SELECT AVG(star) AS averageStarRating FROM avisp WHERE idPlat = ?";
    try (PreparedStatement statement = con.prepareStatement(query)) {
      statement.setInt(1, platId);
      ResultSet rs = statement.executeQuery();
      if (rs.next()) {
        return rs.getDouble("averageStarRating");
      }
    }
    return 0; // Return 0 if no records found or an error occurred

  }

  public Set<AvisPlat> getAllByPlatId(int platId) {

      return Set.of();
  }
}