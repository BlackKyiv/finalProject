package ua.training.myWeb.model.dao.mappers;

import ua.training.myWeb.model.entity.Edition;
import ua.training.myWeb.model.entity.Subscription;
import ua.training.myWeb.model.entity.Theme;
import ua.training.myWeb.model.entity.User;
import ua.training.myWeb.model.entity.enums.SubscriptionStatus;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapper for subscription
 */
public class SubscriptionMapper implements ObjectMapper<Subscription>{

    @Override
    public Subscription extractFromResultSet(ResultSet rs) throws SQLException {
        Subscription subscription = new Subscription();
        UserMapper userMapper = new UserMapper();
        EditionMapper editionMapper = new EditionMapper();

        User user = userMapper.extractFromResultSet(rs);
        Edition edition = editionMapper.extractFromResultSet(rs);

        subscription.setId(rs.getLong("id_subscription"));
        subscription.setEdition(edition);
        subscription.setUser(user);
        subscription.setNextPayDay(rs.getDate("next_pay_date"));
        subscription.setStatus(SubscriptionStatus.getSubscriptionStatus(rs.getString("subscription_status")));


        return subscription;
    }
}
