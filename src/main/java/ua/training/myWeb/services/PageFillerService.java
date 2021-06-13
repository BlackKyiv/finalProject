package ua.training.myWeb.services;

import com.google.common.collect.Lists;
import ua.training.myWeb.model.DBManager;
import ua.training.myWeb.model.dao.EditionDao;
import ua.training.myWeb.model.dao.SubscriptionDao;
import ua.training.myWeb.model.dao.ThemeDao;
import ua.training.myWeb.model.dao.UserDao;
import ua.training.myWeb.model.dao.impl.JDBCDaoFactory;
import ua.training.myWeb.model.dao.impl.JDBCEditionDao;
import ua.training.myWeb.model.dao.impl.JDBCThemeDao;
import ua.training.myWeb.model.entity.Edition;
import ua.training.myWeb.model.entity.Subscription;
import ua.training.myWeb.model.entity.Theme;
import ua.training.myWeb.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Services for actions associated with filling pages with data from database
 */
public class PageFillerService {

    public void fillEditionsPage(HttpServletRequest request, long page, long recordsPerPage) throws Exception {
        try (EditionDao editionDao = JDBCDaoFactory.getInstance().createEditionDao();
             ThemeDao themeDao = JDBCDaoFactory.getInstance().createThemeDao()) {
            List<Edition> editionList = editionDao.findAllWithOffsetLimit((page - 1) * recordsPerPage, recordsPerPage);
            List<Theme> themeList = themeDao.findAll();

            request.setAttribute("editions", editionList);
            request.setAttribute("themes", themeList);

            long noOfRecords = editionDao.count();
            long noOfPages = (long) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);

        }
    }

    public void fillProfilePage(HttpServletRequest request, User user) throws Exception {
        try (SubscriptionDao subscriptionDao = JDBCDaoFactory.getInstance().createSubscriptionDao()) {
            long page = 1;
            long recordsPerPage = 4;
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }

            List<List<Subscription>> subscriptionList = Lists.partition(
                    subscriptionDao.findByUserIdOffsetAndLimit(user.getId(),
                            (page - 1) * recordsPerPage, recordsPerPage), 2);

            request.setAttribute("subscriptionList", subscriptionList);
            long noOfRecords = subscriptionDao.countUser(user.getId());
            long noOfPages = (long) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);
        }
    }

    public void fillThemesPage(HttpServletRequest request) throws Exception {
        try (ThemeDao themeDao = JDBCDaoFactory.getInstance().createThemeDao()) {
            List<Theme> themeList = themeDao.findAll();
            request.setAttribute("themes", themeList);
        }
    }

    public void fillUsersPage(HttpServletRequest request, long page, long recordsPerPage) throws Exception {
        try (UserDao userDao = JDBCDaoFactory.getInstance().createUserDao()) {
            List<User> usersList = userDao.findAllWithOffsetLimit((page - 1) * recordsPerPage, recordsPerPage);
            request.setAttribute("users", usersList);

            long noOfRecords = userDao.count();
            long noOfPages = (long) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);

        }
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
}
