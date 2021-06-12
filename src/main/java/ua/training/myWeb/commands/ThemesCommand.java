package ua.training.myWeb.commands;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.training.myWeb.Path;
import ua.training.myWeb.Servlet;
import ua.training.myWeb.model.dao.EditionDao;
import ua.training.myWeb.model.dao.ThemeDao;
import ua.training.myWeb.model.dao.impl.JDBCDaoFactory;
import ua.training.myWeb.model.entity.Edition;
import ua.training.myWeb.model.entity.Theme;
import ua.training.myWeb.services.PageFillerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ThemesCommand extends Command {

    private static final Logger logger = LogManager.getLogger(ThemesCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");
        PageFillerService pageFillerService = new PageFillerService();
        try {
            pageFillerService.fillThemesPage(request);
            logger.trace("Filled themes page");
        }catch (Exception e) {
            logger.error(e.getMessage());
            request.getSession().setAttribute("errorMessage", "Unexpected error");
            return  "redirect:noCommand";
        }
        logger.debug("Command ends");
        return Path.THEMES_PAGE;
    }


}
