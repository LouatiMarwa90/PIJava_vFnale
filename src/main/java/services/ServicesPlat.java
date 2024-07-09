package services;

import com.google.protobuf.DoubleValue;
import entities.AvisPlat;
import entities.Categorieplat;
import entities.Plat;
import javafx.scene.control.TableView;
import util.Datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ServicesPlat implements IService<Plat> {


    public static DoubleValue.Builder platList;
    public static DoubleValue.Builder plats;
    public static TableView<Plat> listViewPlat;
    public Object readAll;
    static Connection con = Datasource.getInstance().getCon();

    @Override
    public void ajouter(Plat p) throws SQLException {
        String req = "INSERT INTO `plat`(`nomP`, `prixP`, `descP`, `allergie`,`calories`, idC) VALUES (?, ?, ?, ? , ?, ?)";

        PreparedStatement ps = con.prepareStatement(req);
        ps.setString(1, p.getNomP());
        ps.setFloat(2, p.getPrixP());
        ps.setString(3, p.getDescP());
        ps.setString(4, p.getAlergieP());
        ps.setFloat(5, p.getCalories());
        ps.setInt(6, 1);

        ps.executeUpdate();
        System.out.println("Plat ajouté !");


    }

    @Override
    public void modifier(Plat p) throws SQLException {
        String req =
                "UPDATE plat SET nomP = ?, prixP = ?, descP = ?, allergie = ?, calories = ? , idC =? WHERE idP = ?";
        PreparedStatement ps = con.prepareStatement(req);
        ps.setString(1, p.getNomP());
        ps.setFloat(2, p.getPrixP());
        ps.setString(3, p.getDescP());
        ps.setString(4, p.getAlergieP());

        ps.setInt(5, p.getCalories());
        ps.setInt(6, p.getIdP());
        ps.setInt(7, p.getIdC());
        // ps.set


        int rowsUpdated = ps.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Plat modifie !");
        } else {
            System.out.println("id incorrect");
        }


    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM plat WHERE idP = ?";

        PreparedStatement ps = con.prepareStatement(req);
        ps.setInt(1, id);

        int rowsDeleted = ps.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Plat supprimer !");
            System.out.println("id incorrect");
        }

    }

    @Override
    public void supprimer(Categorieplat categorie) throws SQLException {

    }

    @Override
    public Plat getOneById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Plat> getAll() throws SQLException {
        List<Plat> temp = new ArrayList<>();
        String req = "SELECT * FROM plat";

        PreparedStatement ps = con.prepareStatement(req);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {

            temp.add(new Plat(
                    rs.getInt(1),
                    rs.getString("nomP"),
                    rs.getFloat("prixP"),
                    rs.getString("descP"),
                    rs.getString("allergie"),
                    rs.getInt("etatP") == 1,
                    rs.getFloat("rate"),
                    rs.getInt("calories")
            ));
        }

        return temp;
    }

        public void insert(Plat plat) {
            String req = "INSERT INTO plat (nomP, prixP, descP, allergie, rate, calories, etatP, idC, idU) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try {
                PreparedStatement ps = con.prepareStatement(req);
                ps.setString(1, plat.getNomP());
                ps.setFloat(2, plat.getPrixP());
                ps.setString(3, plat.getDescP());
                ps.setString(4, plat.getAlergieP());
                ps.setFloat(5, plat.getRate());
                ps.setInt(6, plat.getCalories());
                ps.setBoolean(7, plat.getEtatP());
                ps.setInt(8, plat.getIdC());
              //  ps.setInt(9, plat.getIdU());

                ps.executeUpdate();
                System.out.println("Plat ajouté !");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    public List<Plat> rechercherPlat(String recherche) {
        List<Plat> platList = new ArrayList<>();
        String req = "SELECT * FROM plat WHERE nomP LIKE ? OR descP LIKE ?";

        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setString(1, "%" + recherche + "%");
            ps.setString(2, "%" + recherche + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Plat plat = new Plat(
                        rs.getInt("idP"),
                        rs.getString("nomP"),
                        rs.getFloat("prixP"),
                        rs.getString("descP"),
                        rs.getString("allergie"),
                        rs.getInt("etatP") == 1,
                        rs.getFloat("rate"),
                        rs.getInt("calories")
                );
                platList.add(plat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return platList;
    }

        public static List<Plat> trierPlatParPrix() {
            List<Plat> platList = new ArrayList<>();
            String req = "SELECT * FROM plat ORDER BY prixP ";

            try {
                PreparedStatement ps = con.prepareStatement(req);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Plat plat = new Plat(
                            rs.getInt("idP"),
                            rs.getString("nomP"),
                            rs.getFloat("prixP"),
                            rs.getString("descP"),
                            rs.getString("allergie"),
                            rs.getInt("etatP") == 1,
                            rs.getFloat("rate"),
                            rs.getInt("calories")
                    );
                    platList.add(plat);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return platList;
        }
}

  //  public List<Plat> rechercherPlat(String recherche) {
     //   return List.of();
   // }

   // public List<Plat> trierPlatParPrix() {
     //   return List.of();
  //  }

   // public List<Plat> readAll() {

     //   return List.of();
    //}
//}
