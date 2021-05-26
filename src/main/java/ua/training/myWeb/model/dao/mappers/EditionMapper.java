package ua.training.myWeb.model.dao.mappers;

import ua.training.myWeb.model.entity.Edition;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EditionMapper implements ObjectMapper<Edition> {
    @Override
    public Edition extractFromResultSet(ResultSet rs) throws SQLException {
        return null;
    }
}
