package ua.training.myWeb.model.dao.impl;

import ua.training.myWeb.model.dao.EditionDao;
import ua.training.myWeb.model.dao.mappers.EditionMapper;
import ua.training.myWeb.model.entity.Edition;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                MySQLCommands.INSERT_EDITION)) {
            stmt.setString(1, entity.getName());
            stmt.setDouble(2, entity.getPrice());
            stmt.setLong(3, entity.getTheme().getId());
            stmt.setString(4, entity.getStatus().toString());
            stmt.executeUpdate();
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
    public List<Edition> findAllWithOffsetLimit(long offset, long limit) {
        List<Edition> editions = new ArrayList<>();
        EditionMapper mapper = new EditionMapper();
        try {
            try (PreparedStatement stmt = connection.prepareStatement(
                    MySQLCommands.FIND_ALL_EDITIONS_OFFSET_LIMIT)) {
                stmt.setLong(1, offset);
                stmt.setLong(2, limit);
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
    public List<Edition> findAllWithOffsetLimitActive(long offset, long limit) {
        List<Edition> editions = new ArrayList<>();
        EditionMapper mapper = new EditionMapper();
        try {
            try (PreparedStatement stmt = connection.prepareStatement(
                    MySQLCommands.FIND_ALL_EDITIONS_OFFSET_LIMIT_ACTIVE)) {
                stmt.setLong(1, offset);
                stmt.setLong(2, limit);
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
    public List<Edition> findWithOffsetLimitActiveQueryGeneration(long offset, long limit, long themeId, String sort, String query) {
        List<Edition> editions = new ArrayList<>();
        EditionMapper mapper = new EditionMapper();

        StringBuilder sb =
                new StringBuilder("SELECT * FROM edition e INNER JOIN theme t ON e.theme_id = t.id_theme WHERE (edition_status = 'active') ");


        if (themeId > 0) {
            sb.append("AND (theme_id = ?) ");
        }
        if (!query.equals("")) {
            sb.append("AND (name LIKE '").append(query).append("%') ");
        }

        if (sort.equals("priceHighest")) {
            sb.append("ORDER BY price ASC ");
        } else if (sort.equals("priceLowest")) {
            sb.append("ORDER BY price DESC ");
        } else {
            sb.append("ORDER BY name ");
        }


        sb.append("LIMIT ?, ? ");
        System.out.println(sb.toString());

        try {
            try (PreparedStatement stmt = connection.prepareStatement(
                    sb.toString())) {
                int i = 1;
                if (themeId > 0) {
                    stmt.setLong(i++, themeId);
                }
                stmt.setLong(i++, offset);
                stmt.setLong(i, limit);
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
    public Long countActive() {
        Long result = null;
        try {
            try (PreparedStatement stmt = connection.prepareStatement(
                    MySQLCommands.EDITIONS_COUNT_ACTIVE)) {
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
    public Long countActiveQueryGeneration(long themeId, String sort, String query) {
        Long result = null;

        StringBuilder sb =
                new StringBuilder("SELECT COUNT(id_edition) AS number FROM edition e INNER JOIN theme t ON e.theme_id = t.id_theme WHERE (edition_status = 'active') ");


        if (themeId > 0) {
            sb.append("AND (theme_id = ?) ");
        }
        if (!query.equals("")) {
            sb.append("AND (name LIKE '").append(query).append("%') ");
        }

        if (sort.equals("priceHighest")) {
            sb.append("ORDER BY price ASC ");
        } else if (sort.equals("priceLowest")) {
            sb.append("ORDER BY price DESC ");
        } else {
            sb.append("ORDER BY name ");
        }
        System.out.println(sb.toString());

        try {
            try (PreparedStatement stmt = connection.prepareStatement(
                    sb.toString())) {
                if (themeId > 0) {
                    stmt.setLong(1, themeId);
                }
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) result = rs.getLong("number");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public Long count() {
        Long result = null;
        try {
            try (PreparedStatement stmt = connection.prepareStatement(
                    MySQLCommands.EDITIONS_COUNT)) {
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
    public void update(Edition entity) {
        try (PreparedStatement stmt = connection.prepareStatement(
                MySQLCommands.UPDATE_EDITION)) {
            stmt.setString(1, entity.getName());
            stmt.setDouble(2, entity.getPrice());
            stmt.setLong(3, entity.getTheme().getId());
            stmt.setString(4, entity.getStatus().toString());
            stmt.setLong(5, entity.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement stmt = connection.prepareStatement(
                MySQLCommands.DELETE_EDITION)) {
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
