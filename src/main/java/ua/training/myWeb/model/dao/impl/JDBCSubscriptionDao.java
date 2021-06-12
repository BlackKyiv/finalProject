package ua.training.myWeb.model.dao.impl;

import org.apache.log4j.Logger;
import ua.training.myWeb.model.dao.SubscriptionDao;
import ua.training.myWeb.model.dao.mappers.SubscriptionMapper;
import ua.training.myWeb.model.entity.Subscription;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCSubscriptionDao implements SubscriptionDao {

    private static final Logger log = Logger.getLogger(JDBCSubscriptionDao.class);
    private final Connection connection;

    public JDBCSubscriptionDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Subscription entity) {
        try (PreparedStatement stmt = connection.prepareStatement(
                MySQLCommands.INSERT_SUBSCRIPTION, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, entity.getEdition().getId());
            stmt.setLong(2, entity.getUser().getId());
            stmt.setDate(3, entity.getNextPayDay());
            stmt.setString(4, entity.getStatus().toString());
            stmt.executeUpdate();
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys != null && keys.next()) entity.setId(keys.getLong(1));
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }

    }

    @Override
    public Long count() {
        Long result = null;
        try {
            try (PreparedStatement stmt = connection.prepareStatement(
                    MySQLCommands.SUBSCRIPTIONS_COUNT)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) result = rs.getLong("number");
                }
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
        return result;
    }

    @Override
    public Subscription findById(long id) {
        Subscription subscription = null;
        SubscriptionMapper subscriptionMapper = new SubscriptionMapper();
        try {
            try (PreparedStatement stmt = connection.prepareStatement(
                    MySQLCommands.FIND_SUBSCRIPTION_BY_ID)) {
                stmt.setLong(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) subscription = subscriptionMapper.extractFromResultSet(rs);
                }
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
        return subscription;
    }

    @Override
    public List<Subscription> findAll() {
        List<Subscription> subscriptions = new ArrayList<>();
        SubscriptionMapper subscriptionMapper = new SubscriptionMapper();
        try {
            try (PreparedStatement stmt = connection.prepareStatement(
                    MySQLCommands.FIND_ALL_SUBSCRIPTION)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) subscriptions.add(subscriptionMapper.extractFromResultSet(rs));
                }
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
        return subscriptions;
    }

    @Override
    public void update(Subscription entity) {
        try (PreparedStatement stmt = connection.prepareStatement(
                MySQLCommands.UPDATE_SUBSCRIPTION)) {
            stmt.setLong(1, entity.getEdition().getId());
            stmt.setLong(2, entity.getUser().getId());
            stmt.setDate(3, entity.getNextPayDay());
            stmt.setString(4, entity.getStatus().toString());
            stmt.setLong(5, entity.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement stmt = connection.prepareStatement(
                MySQLCommands.DELETE_SUBSCRIPTION)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
    }

    @Override
    public void close() throws Exception {
        connection.commit();
        connection.close();
    }

    @Override
    public Subscription findByUserIdAndEditionId(Long userId, Long editionId) {
        Subscription subscription = null;
        SubscriptionMapper subscriptionMapper = new SubscriptionMapper();
        try {
            try (PreparedStatement stmt = connection.prepareStatement(
                    MySQLCommands.FIND_SUBSCRIPTION_BY_USER_ID_AND_EDITION_ID)) {
                stmt.setLong(1, userId);
                stmt.setLong(2, editionId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) subscription = subscriptionMapper.extractFromResultSet(rs);
                }
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
        return subscription;
    }

    @Override
    public List<Subscription> findByUserIdOffsetAndLimit(Long userId, Long offset, Long limit) {
        List<Subscription> subscriptions = new ArrayList<>();
        SubscriptionMapper mapper = new SubscriptionMapper();
        try {
            try (PreparedStatement stmt = connection.prepareStatement(
                    MySQLCommands.FIND_SUBSCRIPTION_BY_USER_ID_OFFSET_LIMIT)) {
                stmt.setLong(1, userId);
                stmt.setLong(2, offset);
                stmt.setLong(3, limit);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        subscriptions.add(mapper.extractFromResultSet(rs));
                    }
                }
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage());

        }
        return subscriptions;
    }

    @Override
    public Long countUser(long userId) {
        Long result = null;
        try {
            try (PreparedStatement stmt = connection.prepareStatement(
                    MySQLCommands.SUBSCRIPTIONS_COUNT_USER)) {
                stmt.setLong(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) result = rs.getLong("number");
                }
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
        return result;
    }
}
