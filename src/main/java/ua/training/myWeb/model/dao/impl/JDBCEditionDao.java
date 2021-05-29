package ua.training.myWeb.model.dao.impl;

import ua.training.myWeb.model.dao.EditionDao;
import ua.training.myWeb.model.dao.mappers.EditionMapper;
import ua.training.myWeb.model.entity.Edition;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCEditionDao implements EditionDao {
    private final Connection connection;

    public JDBCEditionDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Edition entity) {
        try (PreparedStatement stmt = connection.prepareStatement(
                MySQLCommands.INSERT_EDITION,
                Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, entity.getName());
            stmt.setDouble(2, entity.getPrice());
            stmt.setLong(3, entity.getTheme().getId());
            stmt.setString(4, entity.getStatus().toString());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public Edition findById(long id) {
        Edition edition = null;
        EditionMapper editionMapper = new EditionMapper();
        try {
            try (PreparedStatement stmt = connection.prepareStatement(MySQLCommands.FIND_EDITION_BY_ID)) {
                stmt.setLong(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) edition = editionMapper.extractFromResultSet(rs);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return edition;
    }

    @Override
    public List<Edition> findAll() {
        List<Edition> editions = new ArrayList<>();
        EditionMapper mapper = new EditionMapper();
        try {
            try (PreparedStatement stmt = connection.prepareStatement(MySQLCommands.FIND_ALL_EDITIONS)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) editions.add(mapper.extractFromResultSet(rs));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return editions;
    }

    @Override
    public void update(Edition entity) {
        try (PreparedStatement stmt = connection.prepareStatement(
                MySQLCommands.UPDATE_EDITION)) {
            stmt.setString(1, entity.getName());
            stmt.setDouble(2, entity.getPrice());
            stmt.setLong(3, entity.getTheme().getId());
            stmt.setString(4, entity.getStatus().toString());
            stmt.setLong(5, entity.getId());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement stmt = connection.prepareStatement(
                MySQLCommands.DELETE_EDITION)) {
            stmt.setLong(1, id);
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
