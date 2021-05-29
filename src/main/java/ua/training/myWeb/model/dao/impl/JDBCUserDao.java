package ua.training.myWeb.model.dao.impl;

import ua.training.myWeb.model.dao.UserDao;
import ua.training.myWeb.model.dao.mappers.UserMapper;
import ua.training.myWeb.model.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCUserDao implements UserDao {
    private Connection connection;

    public JDBCUserDao(Connection connection) {
    }

    @Override
    public void create(User entity) {
        try (PreparedStatement stmt = connection.prepareStatement(
                MySQLCommands.INSERT_USER,
                Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, entity.getLogin());
            stmt.setString(2, entity.getPassword());
            stmt.setDouble(3, entity.getAccount());
            stmt.setInt(4, entity.getRole().getValue());
            stmt.setInt(5, entity.getStatus().getValue());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public User findByLogin(String login) {
        User user = null;
        UserMapper userMapper = new UserMapper();
        try {
            try (PreparedStatement stmt = connection.prepareStatement(
                    MySQLCommands.FIND_USER_BY_LOGIN)) {
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
        User user = null;
        UserMapper userMapper = new UserMapper();
        try {
            try (PreparedStatement stmt = connection.prepareStatement(
                    MySQLCommands.FIND_USER_BY_ID)) {
                stmt.setLong(1, id);
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
            try (PreparedStatement stmt = connection.prepareStatement(
                    MySQLCommands.FIND_ALL_USERS)) {
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
        try (PreparedStatement stmt = connection.prepareStatement(
                MySQLCommands.UPDATE_USER)) {
            stmt.setString(1, entity.getLogin());
            stmt.setString(2, entity.getPassword());
            stmt.setDouble(3, entity.getAccount());
            stmt.setInt(4, entity.getRole().getValue());
            stmt.setInt(5, entity.getStatus().getValue());
            stmt.setLong(6, entity.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement stmt = connection.prepareStatement(
                MySQLCommands.DELETE_USER)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        connection.commit();
        connection.close();
    }


}
