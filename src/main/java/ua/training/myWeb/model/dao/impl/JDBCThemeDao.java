package ua.training.myWeb.model.dao.impl;

import ua.training.myWeb.model.dao.ThemeDao;
import ua.training.myWeb.model.entity.Theme;

import java.sql.Connection;
import java.util.List;

public class JDBCThemeDao implements ThemeDao {
    private final Connection connection;

    public JDBCThemeDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Theme entity) {

    }

    @Override
    public Theme findById(long id) {
        return null;
    }

    @Override
    public List<Theme> findAll() {
        return null;
    }

    @Override
    public void update(Theme entity) {

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
