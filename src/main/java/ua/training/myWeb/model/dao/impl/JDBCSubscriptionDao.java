package ua.training.myWeb.model.dao.impl;

import ua.training.myWeb.model.dao.SubscriptionDao;
import ua.training.myWeb.model.entity.Subscription;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class JDBCSubscriptionDao implements SubscriptionDao {

    private final Connection connection;

    public JDBCSubscriptionDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Subscription entity) {
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            //TODO
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public Subscription findById(long id) {
        return null;
    }

    @Override
    public List<Subscription> findAll() {
        return null;
    }

    @Override
    public void update(Subscription entity) {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void close() throws Exception {

    }
}
