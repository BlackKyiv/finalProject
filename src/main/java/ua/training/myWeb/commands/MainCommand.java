package ua.training.myWeb.commands;

import com.google.common.collect.Lists;
import ua.training.myWeb.Path;
import ua.training.myWeb.model.dao.EditionDao;
import ua.training.myWeb.model.dao.ThemeDao;
import ua.training.myWeb.model.dao.impl.JDBCDaoFactory;
import ua.training.myWeb.model.entity.Edition;
import ua.training.myWeb.model.entity.Theme;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class MainCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        try (EditionDao editionDao = JDBCDaoFactory.getInstance().createEditionDao();
             ThemeDao themeDao = JDBCDaoFactory.getInstance().createThemeDao()) {

            List<Theme> themes = themeDao.findAll();

            request.setAttribute("themes", themes);


            long page = 1;
            long recordsPerPage = 6;
            if (request.getParameter("page") != null)
                page = Integer.parseInt(request.getParameter("page"));

            long noOfRecords = 0;

            if (request.getParameter("themeId") != null)
                request.setAttribute("themeId", request.getParameter("themeId"));
            if (request.getParameter("sort") != null) request.setAttribute("sort", request.getParameter("sort"));
            if (request.getParameter("query") != null) request.setAttribute("query", request.getParameter("query"));


            List<List<Edition>> editionList = null;
            if (request.getParameter("themeId") != null && !request.getParameter("themeId").equals("") &&
                    request.getParameter("sort") != null &&
                    request.getParameter("query") != null ) {
                long themeId = Long.parseLong(request.getParameter("themeId"));
                editionList = Lists.partition(editionDao.findWithOffsetLimitActiveQueryGeneration((page - 1) * recordsPerPage, recordsPerPage,
                        themeId, request.getParameter("sort"), request.getParameter("query")), 2);
                noOfRecords = editionDao.countActiveQueryGeneration(themeId, request.getParameter("sort"), request.getParameter("query"));
            } else {
                noOfRecords = editionDao.countActive();
                editionList = Lists.partition(editionDao.findAllWithOffsetLimitActive((page - 1) * recordsPerPage, recordsPerPage), 2);
            }
            request.setAttribute("editionsList", editionList);

            long noOfPages = (long) Math.ceil(noOfRecords * 1.0 / recordsPerPage);


            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);
        } catch (Exception e) {
            e.printStackTrace();
            return Path.ERROR_PAGE;
        }


        return Path.MAIN_PAGE;
    }
}
