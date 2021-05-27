package ua.training.myWeb.model.dao;

import ua.training.myWeb.model.dao.impl.JDBCDaoFactory;

public abstract class DaoFactory {

    private static DaoFactory daoFactory;

    public abstract UserDao createUserDao();

    public abstract EditionDao createEditionDao();

    public abstract SubscriptionDao createSubscriptionDao();

    public abstract ThemeDao createThemeDao();

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            synchronized (DaoFactory.class) {
                if (daoFactory == null) {
                    DaoFactory temp = new JDBCDaoFactory();
                    daoFactory = temp;
                }
            }
        }
        return daoFactory;
    }


}

