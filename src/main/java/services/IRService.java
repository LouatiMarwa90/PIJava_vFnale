package services;

import entities.Reclamation;
import entities.Reponse;

import java.sql.SQLException;
import java.util.List;

public interface IRService <T>{

    void insert(T t) throws SQLException;;
    void supprimer(int id) throws SQLException;
    void modifier(T t) throws SQLException;;

    T readById(int id)throws SQLException;;
    public List<T> getAll() throws SQLException;
    void envoyerNotificationEmail(int idRec, String nouvelEtat, String userEmail, String reponse);
    List<Reclamation> readByEtat(String etat);

}
