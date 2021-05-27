package ua.training.myWeb.model.dao.mappers;

import ua.training.myWeb.model.entity.Edition;
import ua.training.myWeb.model.entity.Theme;
import ua.training.myWeb.model.entity.enums.EditionStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EditionMapper implements ObjectMapper<Edition> {

    @Override
    public Edition extractFromResultSet(ResultSet rs) throws SQLException {
        Edition edition = new Edition();
        edition.setName(rs.getString("name"));
        edition.setPrice(rs.getDouble("price"));
        edition.setTheme(new Theme(rs.getString("theme")));
        edition.setStatus(EditionStatus.getEditionStatus(rs.getString("status")));

        return edition;
    }
}
