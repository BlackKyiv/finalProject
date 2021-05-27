package ua.training.myWeb.model.dao.impl;

import ua.training.myWeb.model.dao.EditionDao;
import ua.training.myWeb.model.entity.Edition;

import java.sql.Connection;
import java.util.List;

public class JDBCEditionDao implements EditionDao {
    private final Connection connection;

    public JDBCEditionDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Edition entity) {

    }

    @Override
    public Edition findById(long id) {
        return null;
    }

    @Override
    public List<Edition> findAll() {
        return null;
    }

    @Override
    public void update(Edition entity) {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void close() throws Exception {
        connection.commit();
        connection.close();
    }
}
