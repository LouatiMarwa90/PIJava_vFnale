package services;


import entities.Categorieplat;
import util.Datasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
public interface IService <T>{
    static final Connection con = Datasource.getInstance().getCon();

    public void ajouter (T x) throws SQLException;
    public void modifier(T x) throws SQLException;

    public void supprimer(int id) throws SQLException;

    void supprimer(Categorieplat categorie) throws SQLException;

    public T getOneById(int id)throws SQLException;
    public List<T> getAll() throws SQLException;

}