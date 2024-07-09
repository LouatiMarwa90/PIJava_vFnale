package util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

    public class Datasource {
        private Connection cnx;
        private String url="jdbc:mysql://localhost:3306/salle de sport";
        private String login="root";
        private String pws="";

        private static Datasource instance;
        public static Datasource getInstance() {
            if (instance == null) {
                instance = new Datasource();

            }
            return instance;
        }


        public Connection getCon() {
            return cnx;
        }
        public void setCon(Connection con) {
            this.cnx = con;
        }

        public Datasource(){
            try {
                cnx= DriverManager.getConnection(url,login,pws);
                System.out.println("Connection etabli");
            }
            catch (SQLException e) {
                throw new RuntimeException(e);

            }
        }
    }
