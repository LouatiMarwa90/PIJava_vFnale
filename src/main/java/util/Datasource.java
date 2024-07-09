package util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {

    private Connection connexion;
    private String url="jdbc:mysql://localhost:3306/salle_de_sport";
    private String login="root";
    private String pwd="";

    private static DataSource instance;

    private DataSource(){
        try {
            connexion= DriverManager.getConnection(url,login,pwd);
            System.out.println("Connexion etablie");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnexion() {
        return connexion;
    }

    public static DataSource getInstance(){
        if(instance==null)
            instance=new DataSource();
        return instance;
    }
}
