package ua.training.myWeb.model;


import org.apache.log4j.Logger;
import ua.training.myWeb.filters.AuthFilter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * DB manager. Works with MySQL DB.
 * Only the required DAO methods are defined!
 *
 *
 */
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
            dbManager = new DBManager();
            log.debug("Created new DBManager");
        }
        return dbManager;
    }

    /**
     * Returns a DB connection from the Pool Connections. Before using this
     * method you must configure the Date Source and the Connections Pool in your
     * WEB_APP_ROOT/META-INF/context.xml file.
     *
     * @return A DB connection.
     */
    public Connection getConnection() {

        try {
            if(dataSource == null) {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/finaldb","root","root");
                connection.setAutoCommit(false);
                connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                log.debug("Accessed new connection");
                return connection;
            }
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
