package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    private static DataSource instance;
    private final Connection connection;

    private DataSource() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Explicitly load the driver
            String url = "jdbc:mysql://localhost:3306/pijava";
            String username = "root"; // replace with your MySQL username
            String password = ""; // replace with your MySQL password
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        } catch (SQLException e) {
            throw new RuntimeException("Connection to the database failed", e);
        }
    }

    public static DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }
        return instance;
    }

    public Connection getConnexion() {
        return connection;
    }
}
