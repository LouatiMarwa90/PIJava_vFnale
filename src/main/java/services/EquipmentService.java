package services;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import entities.AvisEquipement;
import entities.Equipment;
import entities.EquipmentCategory;
import util.DataSource;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class EquipmentService implements IRService<Equipment> {
    private Connection cnx;
    private Translate translate;

    public EquipmentService() {
        cnx = DataSource.getInstance().getConnexion();
        translate = TranslateOptions.getDefaultInstance().getService(); // Initialize the Translate service
    }

    @Override
    public void insert(Equipment equipment) throws SQLException {
        String query = "INSERT INTO equipment (nomEq, descEq, documentation, imageEq, categorie, noteEq) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, equipment.getNomEq());
            ps.setString(2, equipment.getDescEq());
            ps.setString(3, equipment.getDocumentation());
            ps.setBytes(4, equipment.getImageEq());
            ps.setString(5, equipment.getCategorie() != null ? equipment.getCategorie().name() : null); // Ensure null-safety
            ps.setInt(6, equipment.getNoteEq());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                equipment.setIdEq(rs.getInt(1));
            }
        }
    }

    @Override
    public void supprimer(int idEq) throws SQLException {
        String query = "DELETE FROM equipment WHERE idEq = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, idEq);
            ps.executeUpdate();
        }
    }

    @Override
    public void modifier(Equipment equipment) throws SQLException {
        String query = "UPDATE equipment SET nomEq=?, descEq=?, documentation=?, imageEq=?, categorie=?, noteEq=? WHERE idEq=?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, equipment.getNomEq());
            ps.setString(2, equipment.getDescEq());
            ps.setString(3, equipment.getDocumentation());
            ps.setBytes(4, equipment.getImageEq());
            ps.setString(5, equipment.getCategorie().name());
            ps.setInt(6, equipment.getNoteEq());
            ps.setInt(7, equipment.getIdEq());
            ps.executeUpdate();
        }
    }

    @Override
    public Equipment readById(int idEq) throws SQLException {
        String query = "SELECT * FROM equipment WHERE idEq = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, idEq);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                EquipmentCategory category = rs.getString("categorie") != null ? EquipmentCategory.valueOf(rs.getString("categorie")) : null;
                return new Equipment(
                        rs.getString("nomEq"),
                        rs.getString("descEq"),
                        rs.getString("documentation"),
                        rs.getBytes("imageEq"),
                        category,
                        rs.getInt("noteEq"),
                        rs.getInt("idEq")
                );
            }
        }
        return null;
    }

    @Override
    public List<AvisEquipement> readByEquipmentId(int idEq) throws SQLException {
        return null;
    }

    @Override
    public List<Equipment> getAll() throws SQLException {
        List<Equipment> equipmentList = new ArrayList<>();
        String query = "SELECT * FROM equipment";
        try (Statement statement = cnx.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                equipmentList.add(new Equipment(
                        rs.getString("nomEq"),
                        rs.getString("descEq"),
                        rs.getString("documentation"),
                        rs.getBytes("imageEq"),
                        EquipmentCategory.valueOf(rs.getString("categorie")),
                        rs.getInt("noteEq"),
                        rs.getInt("idEq")
                ));
            }
        }
        return equipmentList;
    }

    @Override
    public AvisEquipement getAvisById(int idA) throws SQLException {
        return null;
    }

    @Override
    public List<Equipment> starTopEqByNote() throws SQLException {
        List<Equipment> topEquipment = new ArrayList<>();
        String query = "SELECT * FROM equipment ORDER BY noteEq DESC LIMIT 10";
        try (Statement statement = cnx.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                topEquipment.add(new Equipment(
                        rs.getString("nomEq"),
                        rs.getString("descEq"),
                        rs.getString("documentation"),
                        rs.getBytes("imageEq"),
                        EquipmentCategory.valueOf(rs.getString("categorie").toUpperCase()),
                        rs.getInt("noteEq"),
                        rs.getInt("idEq")
                ));
            }
        }
        return topEquipment;
    }

    @Override
    public void noterEquipment(int idEq, int note) throws SQLException {
        if (note < 1 || note > 10) {
            throw new IllegalArgumentException("La note doit être entre 1 et 10.");
        }
        String query = "UPDATE equipment SET noteEq = ? WHERE idEq = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, note);
            ps.setInt(2, idEq);
            ps.executeUpdate();
        }
    }

    public void translateDocumentationToEnglish(int idEq) throws SQLException {
        String querySelect = "SELECT documentation FROM equipment WHERE idEq = ?";
        String queryUpdate = "UPDATE equipment SET documentation = ? WHERE idEq = ?";
        try (PreparedStatement selectStmt = cnx.prepareStatement(querySelect);
             PreparedStatement updateStmt = cnx.prepareStatement(queryUpdate)) {
            selectStmt.setInt(1, idEq);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                String documentation = rs.getString("documentation");
                Translation translation = translate.translate(documentation, Translate.TranslateOption.targetLanguage("en"));
                String translatedDocumentation = translation.getTranslatedText();
                updateStmt.setString(1, translatedDocumentation);
                updateStmt.setInt(2, idEq);
                updateStmt.executeUpdate();
            }
        }
    }

    public List<Equipment> findByCategory(String category) throws SQLException {
        List<Equipment> equipmentList = new ArrayList<>();
        String query = "SELECT * FROM equipment WHERE categorie = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, category.toUpperCase());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                equipmentList.add(new Equipment(
                        rs.getString("nomEq"),
                        rs.getString("descEq"),
                        rs.getString("documentation"),
                        rs.getBytes("imageEq"),
                        EquipmentCategory.valueOf(rs.getString("categorie").toUpperCase()),
                        rs.getInt("noteEq"),
                        rs.getInt("idEq")
                ));
            }
        }
        return equipmentList;
    }

    public List<Equipment> sortEquipmentByNote() throws SQLException {
        String query = "SELECT * FROM equipment ORDER BY noteEq DESC";
        List<Equipment> equipmentList = new ArrayList<>();
        try (Statement statement = cnx.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                equipmentList.add(new Equipment(
                        rs.getString("nomEq"),
                        rs.getString("descEq"),
                        rs.getString("documentation"),
                        rs.getBytes("imageEq"),
                        EquipmentCategory.valueOf(rs.getString("categorie").toUpperCase()),
                        rs.getInt("noteEq"),
                        rs.getInt("idEq")
                ));
            }
        }
        return equipmentList;
    }

    public List<Equipment> paginateEquipment(int pageNumber, int pageSize) throws SQLException {
        String query = "SELECT * FROM equipment LIMIT ? OFFSET ?";
        List<Equipment> equipmentList = new ArrayList<>();
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, pageSize);
            ps.setInt(2, (pageNumber - 1) * pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                equipmentList.add(new Equipment(
                        rs.getString("nomEq"),
                        rs.getString("descEq"),
                        rs.getString("documentation"),
                        rs.getBytes("imageEq"),
                        EquipmentCategory.valueOf(rs.getString("categorie").toUpperCase()),
                        rs.getInt("noteEq"),
                        rs.getInt("idEq")
                ));
            }
        }
        return equipmentList;
    }
}