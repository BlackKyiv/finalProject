package ua.training.myWeb.commands;

import ua.training.myWeb.model.dao.ThemeDao;
import ua.training.myWeb.model.dao.impl.JDBCDaoFactory;
import ua.training.myWeb.model.entity.Theme;
import ua.training.myWeb.services.DatabaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateThemeCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String forward = "redirect:themes";

        if (request.getParameter("name") != null) {
            String name = request.getParameter("name");
            DatabaseService databaseService = new DatabaseService();
            try {
                databaseService.createTheme(name);
            } catch (Exception e) {
                e.printStackTrace();
                request.getSession().setAttribute("errorMessage", "Error while creating theme");
                forward = "redirect:noCommand";
            }
        } else {
            request.getSession().setAttribute("errorMessage", "Not all fields were filled");
            forward = "redirect:noCommand";
        }

        return forward;
    }


}
