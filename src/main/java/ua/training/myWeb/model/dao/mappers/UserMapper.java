package ua.training.myWeb.model.dao.mappers;


import ua.training.myWeb.model.entity.User;
import ua.training.myWeb.model.entity.enums.Role;
import ua.training.myWeb.model.entity.enums.UserStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Mapper for user
 */
public class UserMapper implements ObjectMapper<User> {

    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id_user"));
        user.setLogin(rs.getString("login"));
        user.setPassword(rs.getString("password"));
        user.setAccount(rs.getDouble("account"));
        user.setRole(Role.getRole(rs.getString("role")));
        user.setStatus(UserStatus.getUserStatus(rs.getString("user_status")));

        return user;
    }

}
