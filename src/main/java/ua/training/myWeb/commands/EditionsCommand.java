package ua.training.myWeb.commands;

import ua.training.myWeb.Path;
import ua.training.myWeb.model.dao.EditionDao;
import ua.training.myWeb.model.dao.ThemeDao;
import ua.training.myWeb.model.dao.UserDao;
import ua.training.myWeb.model.dao.impl.JDBCDaoFactory;
import ua.training.myWeb.model.entity.Edition;
import ua.training.myWeb.model.entity.Theme;
import ua.training.myWeb.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class EditionsCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try (EditionDao editionDao = JDBCDaoFactory.getInstance().createEditionDao();
             ThemeDao themeDao = JDBCDaoFactory.getInstance().createThemeDao()) {
            long page = 1;
            long recordsPerPage = 6;
            if(request.getParameter("page") != null)
                page = Integer.parseInt(request.getParameter("page"));

            List<Edition> editionList = editionDao.findAllWithOffsetLimit((page-1)*recordsPerPage, recordsPerPage);
            List<Theme> themeList = themeDao.findAll();

            request.setAttribute("editions", editionList);
            request.setAttribute("themes", themeList);

            long noOfRecords = editionDao.count();
            long noOfPages = (long) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);

        } catch (Exception e) {
            e.printStackTrace();
            return Path.ERROR_PAGE;
        }

        return Path.EDITIONS_PAGE;
    }
}
