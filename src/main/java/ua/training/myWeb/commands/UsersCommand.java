package ua.training.myWeb.commands;

import com.google.common.collect.Lists;
import ua.training.myWeb.Path;
import ua.training.myWeb.model.dao.EditionDao;
import ua.training.myWeb.model.dao.UserDao;
import ua.training.myWeb.model.dao.impl.JDBCDaoFactory;
import ua.training.myWeb.model.entity.Edition;
import ua.training.myWeb.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class UsersCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try (UserDao userDao = JDBCDaoFactory.getInstance().createUserDao()) {
            long page = 1;
            long recordsPerPage = 6;
            if(request.getParameter("page") != null)
                page = Integer.parseInt(request.getParameter("page"));

            List<User> usersList = userDao.findAllWithOffsetLimit((page-1)*recordsPerPage, recordsPerPage);

            request.setAttribute("users", usersList);

            long noOfRecords = userDao.count();
            long noOfPages = (long) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);

        } catch (Exception e) {
            e.printStackTrace();
            return Path.ERROR_PAGE;
        }

        return Path.USERS_PAGE;
    }
}
