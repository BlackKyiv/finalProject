package ua.training.myWeb.model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBManager {

    public static final String URL = "jdbc:mysql://localhost:3306/finaldb?user=root&password=root";
    private static DBManager dbManager;

    public static DBManager getInstance() {
        if (dbManager == null) dbManager = new DBManager();
        return dbManager;
    }


    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/finaldb", "root", "root");
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }


}
