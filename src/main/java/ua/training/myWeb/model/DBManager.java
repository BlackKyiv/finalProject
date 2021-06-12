package ua.training.myWeb.model;


import org.apache.log4j.Logger;
import ua.training.myWeb.filters.AuthFilter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


public class DBManager {

    private static DataSource dataSource;
    private static final String JNDI_LOOKUP_SERVICE = "java:/comp/env/jdbc/editions";

    private static final Logger log = Logger.getLogger(AuthFilter.class);

    static {
        try {
            Context context = new InitialContext();
            Object lookup = context.lookup(JNDI_LOOKUP_SERVICE);
            if (lookup != null) {
                dataSource = (DataSource) lookup;
            } else {
                log.error("Data source was null!");
            }
        } catch (NamingException e) {
            log.error(e.getMessage());
        }
    }

    private static DBManager dbManager;

    public static DBManager getInstance() {
        if (dbManager == null) {
            log.debug("Created new DBManager");
            dbManager = new DBManager();
        }
        return dbManager;
    }

    public Connection getConnection() {

        try {
            Connection connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            log.debug("Accessed new connection");
            return connection;
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return null;
    }


}
