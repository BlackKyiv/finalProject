package ua.training.myWeb.model.dao.mappers;

import ua.training.myWeb.model.entity.Edition;
import ua.training.myWeb.model.entity.Subscription;
import ua.training.myWeb.model.entity.Theme;
import ua.training.myWeb.model.entity.enums.EditionStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class EditionMapper implements ObjectMapper<Edition> {

    @Override
    public Edition extractFromResultSet(ResultSet rs) throws SQLException {
        Edition edition = new Edition();
        edition.setId(rs.getLong("id_edition"));
        edition.setName(rs.getString("name"));
        edition.setPrice(rs.getDouble("price"));
        Theme theme = new Theme();
        theme.setName(rs.getString("theme"));
        theme.setId(rs.getLong("id_theme"));
        edition.setTheme(theme);
        edition.setStatus(EditionStatus.getEditionStatus(rs.getString("edition_status")));

        return edition;
    }
}
