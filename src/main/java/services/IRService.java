package services;

import entities.AvisEquipement;

import java.sql.SQLException;
import java.util.List;

public interface IRService <T>{

    void insert(T t) throws SQLException;;
    void supprimer(int id) throws SQLException;
    void modifier(T t) throws SQLException;;

    T readById(int id)throws SQLException;;

    List<AvisEquipement> readByEquipmentId(int idEq) throws SQLException;

    public List<T> getAll() throws SQLException;

    AvisEquipement getAvisById(int idA) throws SQLException;



    List<T> starTopEqByNote() throws SQLException;

    void noterEquipment(int idEq, int note) throws SQLException;
}
