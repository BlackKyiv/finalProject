package ua.training.myWeb.model.dao.impl;

import ua.training.myWeb.model.dao.UserDao;
import ua.training.myWeb.model.dao.mappers.UserMapper;
import ua.training.myWeb.model.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class JDBCUserDao implements UserDao {
    private Connection connection;

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(User entity) {
        try (PreparedStatement stmt = connection.prepareStatement(
                MySQLCommands.INSERT_USER,
                Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, entity.getLogin());
            stmt.setString(2, entity.getPassword());
            stmt.setString(3, entity.getRole().toString().toLowerCase());
            stmt.setDouble(4, entity.getAccount());
            stmt.setString(5, entity.getStatus().toString().toLowerCase());
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
    public List<User> findAllWithOffsetLimit(long offset, long limit) {
        List<User> users = new ArrayList<>();
        UserMapper userMapper = new UserMapper();
        try {
            try (PreparedStatement stmt = connection.prepareStatement(
                    MySQLCommands.FIND_ALL_USERS_OFFSET_LIMIT)) {
                stmt.setLong(1, offset);
                stmt.setLong(2, limit);
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
    public Long count() {
        Long result = null;
        try {
            try (PreparedStatement stmt = connection.prepareStatement(
                    MySQLCommands.USERS_COUNT)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) result = rs.getLong("number");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public void update(User entity) {
        try (PreparedStatement stmt = connection.prepareStatement(
                MySQLCommands.UPDATE_USER)) {
            stmt.setString(1, entity.getLogin());
            stmt.setString(2, entity.getPassword());
            stmt.setString(3, entity.getRole().toString().toLowerCase());
            stmt.setDouble(4, entity.getAccount());
            stmt.setString(5, entity.getStatus().toString().toLowerCase());
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
