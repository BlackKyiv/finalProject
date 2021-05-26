package ua.training.myWeb.model.dao.impl;

import ua.training.myWeb.model.dao.UserDao;
import ua.training.myWeb.model.entity.User;

import java.sql.Connection;
import java.util.List;

public class JDBCUserDao implements UserDao {
    private Connection connection;

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void create(User entity) {

    }

    @Override
    public User findById(long id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void close() throws Exception {

    }
}
