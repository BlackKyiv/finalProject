package ua.training.myWeb.model.dao.impl;


import ua.training.myWeb.model.DBManager;
import ua.training.myWeb.model.dao.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {

    @Override
    public UserDao createUserDao() {
        return new JDBCUserDao(DBManager.getInstance().getConnection());
    }

    @Override
    public EditionDao createEditionDao() {
        return new JDBCEditionDao(DBManager.getInstance().getConnection());
    }

    @Override
    public SubscriptionDao createSubscriptionDao() {
        return new JDBCSubscriptionDao(DBManager.getInstance().getConnection());
    }

    @Override
    public ThemeDao createThemeDao() {
        return new JDBCThemeDao(DBManager.getInstance().getConnection());
    }


}
