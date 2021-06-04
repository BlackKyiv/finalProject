package ua.training.myWeb.commands;

import ua.training.myWeb.model.dao.ThemeDao;
import ua.training.myWeb.model.dao.impl.JDBCDaoFactory;
import ua.training.myWeb.model.entity.Theme;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateThemeCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String forward = "redirect:themes";

        if (request.getParameter("name") != null) {
            String name = request.getParameter("name");

            try (ThemeDao themeDao = JDBCDaoFactory.getInstance().createThemeDao()) {
                Theme theme = new Theme();
                theme.setName(name);
                themeDao.create(theme);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            forward = "redirect:noCommand";
        }

        return forward;
    }
}
