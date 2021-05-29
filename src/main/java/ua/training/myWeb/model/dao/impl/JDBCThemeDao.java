package ua.training.myWeb.model.dao.impl;

import ua.training.myWeb.model.dao.ThemeDao;
import ua.training.myWeb.model.dao.mappers.ThemeMapper;
import ua.training.myWeb.model.dao.mappers.UserMapper;
import ua.training.myWeb.model.entity.Theme;
import ua.training.myWeb.model.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCThemeDao implements ThemeDao {
    private final Connection connection;

    public JDBCThemeDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Theme entity) {
        try (PreparedStatement stmt = connection.prepareStatement(
                MySQLCommands.INSERT_THEME,
                Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, entity.getName());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Theme findById(long id) {
        Theme theme = null;
        ThemeMapper themeMapper = new ThemeMapper();
        try {
            try (PreparedStatement stmt = connection.prepareStatement(
                    MySQLCommands.FIND_THEME_BY_ID)) {
                stmt.setLong(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) theme = themeMapper.extractFromResultSet(rs);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return theme;
    }

    @Override
    public List<Theme> findAll() {
        List<Theme> themes = new ArrayList<>();
        ThemeMapper themeMapper = new ThemeMapper();
        try {
            try (PreparedStatement stmt = connection.prepareStatement(
                    MySQLCommands.FIND_ALL_THEMES)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) themes.add(themeMapper.extractFromResultSet(rs));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return themes;
    }

    @Override
    public void update(Theme entity) {
        try (PreparedStatement stmt = connection.prepareStatement(
                MySQLCommands.UPDATE_THEME)) {
            stmt.setString(1, entity.getName());
            stmt.setLong(2, entity.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement stmt = connection.prepareStatement(
                MySQLCommands.DELETE_THEME)) {
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
