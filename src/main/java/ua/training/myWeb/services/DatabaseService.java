package ua.training.myWeb.services;

import ua.training.myWeb.Path;
import ua.training.myWeb.model.DBManager;
import ua.training.myWeb.model.dao.EditionDao;
import ua.training.myWeb.model.dao.SubscriptionDao;
import ua.training.myWeb.model.dao.ThemeDao;
import ua.training.myWeb.model.dao.UserDao;
import ua.training.myWeb.model.dao.impl.JDBCDaoFactory;
import ua.training.myWeb.model.dao.impl.JDBCEditionDao;
import ua.training.myWeb.model.dao.impl.JDBCSubscriptionDao;
import ua.training.myWeb.model.dao.impl.JDBCUserDao;
import ua.training.myWeb.model.entity.Edition;
import ua.training.myWeb.model.entity.Subscription;
import ua.training.myWeb.model.entity.Theme;
import ua.training.myWeb.model.entity.User;
import ua.training.myWeb.model.entity.enums.EditionStatus;
import ua.training.myWeb.model.entity.enums.Role;
import ua.training.myWeb.model.entity.enums.SubscriptionStatus;
import ua.training.myWeb.model.entity.enums.UserStatus;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * Services for actions associated wit database actions.
 */
public class DatabaseService {

    /**
     * @param request   servlet request with needed data
     * @param editionId id of edition to create subscription with
     * @param forward   string of where to redirect next
     * @return forward
     */
    public String createSubscriptionTransaction(HttpServletRequest request, long editionId, String forward) throws SQLException {

        Connection connection = DBManager.getInstance().getConnection();

        SubscriptionDao subscriptionDao = new JDBCSubscriptionDao(connection);
        UserDao userDao = new JDBCUserDao(connection);
        EditionDao editionDao = new JDBCEditionDao(connection);

        User currentUser = (User) request.getSession().getAttribute("user");

        if (subscriptionDao.findByUserIdAndEditionId(currentUser.getId(), editionId) == null) {
            Edition edition = editionDao.findById(editionId);
            User user = userDao.findById(currentUser.getId());
            double price = edition.getPrice();
            double left = user.getAccount();
            if (left >= price) {
                Subscription subscription = new Subscription();
                subscription.setUser(user);
                subscription.setEdition(edition);
                subscription.setNextPayDay(getNextMonthDate());
                subscription.setStatus(SubscriptionStatus.ACTIVE);
                user.setAccount(left - price);
                subscriptionDao.create(subscription);
                userDao.update(user);
                request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("userRole", user.getRole());
            } else {
                request.getSession().setAttribute("errorMessage", "Not enough money on account");
                forward = Path.ERROR_PAGE;
            }
        } else {
            request.getSession().setAttribute("errorMessage", "You have already subscribed");
            forward = Path.ERROR_PAGE;
        }

        connection.commit();
        connection.close();

        return forward;

    }

    /**
     * @return current date + 30 days
     */
    private Date getNextMonthDate() {

        Calendar c = new GregorianCalendar();
        c.setTime(new Date(new java.util.Date().getTime()));
        c.add(Calendar.DATE, 30);

        return new Date(c.getTime().getTime());
    }

    /**
     * @param editionId      id of edition inside
     * @param subscriptionId id of subscription to delete
     * @param userId         id of user  inside
     */
    public void deleteSubscription(long editionId, long subscriptionId, long userId) throws Exception {
        try (SubscriptionDao subscriptionDao = JDBCDaoFactory.getInstance().createSubscriptionDao()) {
            Subscription subscription = subscriptionDao.findByUserIdAndEditionId(userId, editionId);
            if (subscription != null && subscription.getId() == subscriptionId) {
                subscriptionDao.delete(subscriptionId);
            }
        }
    }

    /**
     * @param themeId id of theme to create edition
     * @param name    name of edition
     * @param price   price of edition
     * @param status  status of edition
     */
    public void createEdition(long themeId, String name, double price, EditionStatus status) throws Exception {
        try (EditionDao editionDao = JDBCDaoFactory.getInstance().createEditionDao()) {
            Edition edition = new Edition();
            edition.setName(name);
            edition.setPrice(price);
            edition.setStatus(status);
            Theme theme = new Theme();
            theme.setId(themeId);
            edition.setTheme(theme);
            editionDao.create(edition);
        }
    }

    /**
     * @param name name of theme to create
     */
    public void createTheme(String name) throws Exception {
        try (ThemeDao themeDao = JDBCDaoFactory.getInstance().createThemeDao()) {
            Theme theme = new Theme();
            theme.setName(name);
            themeDao.create(theme);
        }
    }

    /**
     * @param themeId id of theme to delete
     */
    public void deleteTheme(long themeId) throws Exception {
        try (ThemeDao themeDao = JDBCDaoFactory.getInstance().createThemeDao()) {
            themeDao.delete(themeId);
        }
    }

    /**
     * @param editionId id of edition to delete
     */
    public void deleteEdition(long editionId) throws Exception {
        try (EditionDao editionDao = JDBCDaoFactory.getInstance().createEditionDao()) {
            editionDao.delete(editionId);
        }
    }

    /**
     * @param login of user
     * @return entity of user with this login
     */
    public User findUserByLogin(String login) throws Exception {
        User user;
        try (UserDao userDao = JDBCDaoFactory.getInstance().createUserDao()) {
            user = userDao.findByLogin(login);
        }
        return user;
    }

    /**
     * @param login    login of new user
     * @param password password of new user
     * @return boolean if creation is successful
     */
    public boolean createNewUser(String login, String password) throws Exception {
        boolean res = true;
        try (UserDao userDao = JDBCDaoFactory.getInstance().createUserDao()) {
            if (userDao.findByLogin(login) == null) {
                User user = new User();
                user.setLogin(login);
                user.setPassword(password);
                user.setAccount(0.);
                user.setRole(Role.USER);
                user.setStatus(UserStatus.ACTIVE);
                userDao.create(user);
            } else {
                res = false;
            }
        }
        return res;
    }

    /**
     * @param currentUser     user to replenish money
     * @param replenishAmount amount to replenish
     */
    public void replenishThisUsersAccount(User currentUser, int replenishAmount) throws Exception {
        try (UserDao userDao = JDBCDaoFactory.getInstance().createUserDao()) {
            User user = userDao.findById(currentUser.getId());
            user.setAccount(user.getAccount() + replenishAmount);
            userDao.update(user);
            currentUser.setAccount(user.getAccount());
        }
    }

    public void updateEdition(long editionId, long themeId, String name, double price, EditionStatus status) throws Exception {
        try (EditionDao editionDao = JDBCDaoFactory.getInstance().createEditionDao();
             ThemeDao themeDao = JDBCDaoFactory.getInstance().createThemeDao()) {
            Edition edition = editionDao.findById(editionId);
            Theme theme = themeDao.findById(themeId);

            if (edition != null && theme != null) {
                edition.setName(name);
                edition.setPrice(price);
                edition.setStatus(status);
                edition.setTheme(theme);
                editionDao.update(edition);
            }
        }
    }

    public void updateTheme(long themeId, String name) throws Exception {
        try (ThemeDao themeDao = JDBCDaoFactory.getInstance().createThemeDao()) {
            Theme theme = themeDao.findById(themeId);
            if (theme != null) {
                theme.setName(name);
                themeDao.update(theme);
            }
        }
    }

    public void updateUser(long userId, String login, double account, UserStatus status) throws Exception {
        try (UserDao userDao = JDBCDaoFactory.getInstance().createUserDao()) {
            User user = userDao.findById(userId);
            if (user != null) {
                user.setLogin(login);
                user.setAccount(account);
                user.setStatus(status);
                userDao.update(user);
            }
        }
    }

    /**
     * @param userId to delete
     */
    public void deleteUser(long userId) throws Exception {
        try (UserDao userDao = JDBCDaoFactory.getInstance().createUserDao()) {
            userDao.delete(userId);
        }
    }
}
