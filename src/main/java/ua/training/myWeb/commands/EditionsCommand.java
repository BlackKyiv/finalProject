package ua.training.myWeb.commands;

import ua.training.myWeb.Path;
import ua.training.myWeb.model.dao.EditionDao;
import ua.training.myWeb.model.dao.ThemeDao;
import ua.training.myWeb.model.dao.impl.JDBCDaoFactory;
import ua.training.myWeb.model.entity.Edition;
import ua.training.myWeb.model.entity.Theme;
import ua.training.myWeb.services.PageFillerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class EditionsCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        long page = 1;
        long recordsPerPage = 6;

        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));
        PageFillerService pageFillerService = new PageFillerService();
        try {
            pageFillerService.fillEditionsPage(request, page, recordsPerPage);
        } catch (Exception e) {
            request.getSession().setAttribute("errorMessage", "Cannot open this page");
            return "redirect:noCommand";
        }

        return Path.EDITIONS_PAGE;
    }


}
