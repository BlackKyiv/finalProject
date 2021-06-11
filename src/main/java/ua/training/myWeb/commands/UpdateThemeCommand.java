package ua.training.myWeb.commands;

import ua.training.myWeb.model.dao.ThemeDao;
import ua.training.myWeb.model.dao.impl.JDBCDaoFactory;
import ua.training.myWeb.model.entity.Theme;
import ua.training.myWeb.services.DatabaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateThemeCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String forward = "redirect:themes";
        if (request.getParameter("themeId") != null &&
                request.getParameter("name") != null) {

            long themeId = Long.parseLong(request.getParameter("themeId"));
            String name = request.getParameter("name");
            DatabaseService databaseService = new DatabaseService();
            try {
                databaseService.updateTheme(themeId, name);
            } catch (Exception e) {
                request.getSession().setAttribute("errorMessage", "Unexpected error");
                forward = "redirect:noCommand";
            }
        } else {
            forward = "redirect:noCommand";
        }
        return forward;
    }


}
