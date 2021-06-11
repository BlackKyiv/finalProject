package ua.training.myWeb.commands;

import ua.training.myWeb.model.dao.ThemeDao;
import ua.training.myWeb.model.dao.UserDao;
import ua.training.myWeb.model.dao.impl.JDBCDaoFactory;
import ua.training.myWeb.services.DatabaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteThemeCommand extends Command{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String forward = "redirect:themes";
        if (request.getParameter("themeId") != null) {
            long themeId = Long.parseLong(request.getParameter("themeId"));
            DatabaseService databaseService = new DatabaseService();
            try {
                databaseService.deleteTheme(themeId);
            } catch (Exception e) {
                request.getSession().setAttribute("errorMessage", "This subscription doesnt exist");
                forward = "redirect:noCommand";
            }
        } else {
            forward = "redirect:themes";
        }

        return forward;
    }


}
