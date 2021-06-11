package ua.training.myWeb.services;

import com.google.common.collect.Lists;
import ua.training.myWeb.model.dao.EditionDao;
import ua.training.myWeb.model.dao.SubscriptionDao;
import ua.training.myWeb.model.dao.ThemeDao;
import ua.training.myWeb.model.dao.UserDao;
import ua.training.myWeb.model.dao.impl.JDBCDaoFactory;
import ua.training.myWeb.model.entity.Edition;
import ua.training.myWeb.model.entity.Subscription;
import ua.training.myWeb.model.entity.Theme;
import ua.training.myWeb.model.entity.User;
import ua.training.myWeb.model.entity.enums.EditionStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
            if (request.getParameter("page") != null)
                page = Integer.parseInt(request.getParameter("page"));

            List<List<Subscription>> subscriptionList = Lists.partition(
                    subscriptionDao.findByUserIdOffsetAndLimit(user.getId(), (page - 1) * recordsPerPage, recordsPerPage), 2);

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


}
