package ua.training.myWeb.model.dao.mappers;

import ua.training.myWeb.model.entity.Subscription;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubscriptionMapper implements ObjectMapper<Subscription>{

    @Override
    public Subscription extractFromResultSet(ResultSet rs) throws SQLException {
        return null;
    }
}
