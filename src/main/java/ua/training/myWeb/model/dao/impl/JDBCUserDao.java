package ua.training.myWeb.model.dao.impl;

import ua.training.myWeb.model.dao.UserDao;
import ua.training.myWeb.model.dao.mappers.UserMapper;
import ua.training.myWeb.model.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCUserDao implements UserDao {
    private final Connection connection;

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(User entity) {

    }

    @Override
    public User findByLogin(String login) {
        User user = new User();
        UserMapper userMapper = new UserMapper();
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM user WHERE login = ? LIMIT 1")) {
                stmt.setString(1, login);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) user = userMapper.extractFromResultSet(rs);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }

    @Override
    public User findById(long id) {
        User user = new User();
        UserMapper userMapper = new UserMapper();
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM user WHERE id = ? LIMIT 1")) {
                stmt.setString(1, String.valueOf(id));
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) user = userMapper.extractFromResultSet(rs);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        UserMapper userMapper = new UserMapper();
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM user")) {

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) users.add(userMapper.extractFromResultSet(rs));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return users;
    }

    @Override
    public void update(User entity) {

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
