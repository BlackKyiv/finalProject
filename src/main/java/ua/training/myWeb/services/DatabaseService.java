package ua.training.myWeb.services;

import com.google.common.collect.Lists;
import ua.training.myWeb.Path;
import ua.training.myWeb.model.DBManager;
import ua.training.myWeb.model.dao.EditionDao;
import ua.training.myWeb.model.dao.SubscriptionDao;
import ua.training.myWeb.model.dao.ThemeDao;
import ua.training.myWeb.model.dao.UserDao;
import ua.training.myWeb.model.dao.impl.*;
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
import java.util.List;

public class DatabaseService {

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
                request.setAttribute("errorMessage", "Not enough money on account");
                forward = Path.ERROR_PAGE;
            }
        } else {
            request.setAttribute("errorMessage", "You have already subscribed");
            forward = Path.ERROR_PAGE;
        }

        connection.commit();
        connection.close();

        return forward;

    }

    public void fillMainPage(HttpServletRequest request) throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();

        EditionDao editionDao = new JDBCEditionDao(connection);
        ThemeDao themeDao = new JDBCThemeDao(connection);
        List<Theme> themes = themeDao.findAll();
        request.setAttribute("themes", themes);

        long page = 1;
        long recordsPerPage = 6;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        long noOfRecords = 0;

        if (request.getParameter("themeId") != null)
            request.setAttribute("themeId", request.getParameter("themeId"));
        if (request.getParameter("sort") != null) request.setAttribute("sort", request.getParameter("sort"));
        if (request.getParameter("query") != null) request.setAttribute("query", request.getParameter("query"));


        List<List<Edition>> editionList = null;
        if (request.getParameter("themeId") != null && !request.getParameter("themeId").equals("") &&
                request.getParameter("sort") != null &&
                request.getParameter("query") != null) {
            long themeId = Long.parseLong(request.getParameter("themeId"));
            editionList
                    = Lists.partition(
                    editionDao.findWithOffsetLimitActiveQueryGeneration((page - 1) * recordsPerPage, recordsPerPage,
                            themeId, request.getParameter("sort"), request.getParameter("query")), 2);
            noOfRecords
                    = editionDao.countActiveQueryGeneration(themeId, request.getParameter("sort"), request.getParameter("query"));
        } else {
            noOfRecords = editionDao.countActive();
            editionList
                    = Lists.partition(
                    editionDao.findAllWithOffsetLimitActive((page - 1) * recordsPerPage, recordsPerPage), 2);
        }
        request.setAttribute("editionsList", editionList);

        long noOfPages = (long) Math.ceil(noOfRecords * 1.0 / recordsPerPage);


        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        connection.commit();
        connection.close();

    }

    private Date getNextMonthDate() {

        Calendar c = new GregorianCalendar();
        c.setTime(new Date(new java.util.Date().getTime()));
        c.add(Calendar.DATE, 30);

        return new Date(c.getTime().getTime());
    }

    public void deleteSubscription(long editionId, long subscriptionId, long userId) throws Exception {
        try (SubscriptionDao subscriptionDao = JDBCDaoFactory.getInstance().createSubscriptionDao()) {
            Subscription subscription = subscriptionDao.findByUserIdAndEditionId(userId, editionId);
            if (subscription != null && subscription.getId() == subscriptionId) {
                subscriptionDao.delete(subscriptionId);
            }
        }
    }

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

    public void createTheme(String name) throws Exception {
        try (ThemeDao themeDao = JDBCDaoFactory.getInstance().createThemeDao()) {
            Theme theme = new Theme();
            theme.setName(name);
            themeDao.create(theme);
        }
    }

    public void deleteTheme(long themeId) throws Exception {
        try (ThemeDao themeDao = JDBCDaoFactory.getInstance().createThemeDao()) {
            themeDao.delete(themeId);
        }
    }

    public void deleteEdition(long userId) throws Exception {
        try (EditionDao editionDao = JDBCDaoFactory.getInstance().createEditionDao()) {
            editionDao.delete(userId);
        }
    }

    public User findUserByLogin(String login) throws Exception {
        User user;
        try (UserDao userDao = JDBCDaoFactory.getInstance().createUserDao()) {
            user = userDao.findByLogin(login);
        }
        return user;
    }

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

    public void deleteUser(long userId) throws Exception {
        try (UserDao userDao = JDBCDaoFactory.getInstance().createUserDao()) {
            userDao.delete(userId);
        }
    }


}
