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

public class ThemesCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        PageFillerService pageFillerService = new PageFillerService();
        try {
            pageFillerService.fillThemesPage(request);
        }catch (Exception e) {
            request.getSession().setAttribute("errorMessage", "Unexpected error");
            return  "redirect:noCommand";
        }

        return Path.THEMES_PAGE;
    }


}
