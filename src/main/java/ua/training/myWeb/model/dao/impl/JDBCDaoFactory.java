package ua.training.myWeb.model.dao.impl;


import ua.training.myWeb.model.DBManager;
import ua.training.myWeb.model.dao.DaoFactory;
import ua.training.myWeb.model.dao.EditionDao;
import ua.training.myWeb.model.dao.UserDao;

public class JDBCDaoFactory extends DaoFactory {

    @Override
    public UserDao createUserDao() {
        return new JDBCUserDao(DBManager.getInstance().getConnection());
    }

    @Override
    public EditionDao createEditionDao() {
        return new JDBCEditionDao(DBManager.getInstance().getConnection());
    }


}
