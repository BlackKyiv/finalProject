package ua.training.myWeb.model.dao.mappers;

import ua.training.myWeb.model.entity.Theme;
import ua.training.myWeb.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class ThemeMapper implements ObjectMapper<Theme> {

    @Override
    public Theme extractFromResultSet(ResultSet rs) throws SQLException {
        Theme theme = new Theme();
        theme.setId(rs.getLong("id_theme"));
        theme.setName(rs.getString("theme"));

        return theme;
    }

}
