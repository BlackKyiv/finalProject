package ua.training.myWeb.model;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


public class DBManager {

    private static DataSource dataSource;
    private static final String JNDI_LOOKUP_SERVICE = "java:/comp/env/jdbc/editions";

    static {
        try {
            Context context = new InitialContext();
            Object lookup = context.lookup(JNDI_LOOKUP_SERVICE);
            if (lookup != null) {
                dataSource = (DataSource) lookup;
            }
        } catch (NamingException e) {

            e.printStackTrace();
        }
    }

    private static DBManager dbManager;

    public static DBManager getInstance() {
        if (dbManager == null) dbManager = new DBManager();
        return dbManager;
    }


    public Connection getConnection() {

        try {
            Connection connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            return connection;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


}
