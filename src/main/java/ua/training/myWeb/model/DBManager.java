package ua.training.myWeb.model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBManager {

    public static final String URL = "jdbc\\:h2\\:~/test;user\\=root;password\\=root";
    private static DBManager dbManager;

    public static DBManager getInstance() {
        if (dbManager == null) dbManager = new DBManager();
        return dbManager;
    }


    public Connection getConnection(){
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public Connection getConnection(String connectionUrl) throws SQLException {
        return DriverManager.getConnection(connectionUrl);
    }



}
