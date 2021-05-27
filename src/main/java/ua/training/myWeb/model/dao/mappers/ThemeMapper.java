package ua.training.myWeb.model.dao.mappers;

import ua.training.myWeb.model.entity.Theme;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ThemeMapper implements ObjectMapper<Theme> {

    @Override
    public Theme extractFromResultSet(ResultSet rs) throws SQLException {
        Theme theme = new Theme();
        theme.setId(rs.getLong("id"));
        theme.setName(rs.getString("name"));

        return theme;
    }
}
