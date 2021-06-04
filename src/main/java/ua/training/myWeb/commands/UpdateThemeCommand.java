package ua.training.myWeb.commands;

import ua.training.myWeb.model.dao.ThemeDao;
import ua.training.myWeb.model.dao.impl.JDBCDaoFactory;
import ua.training.myWeb.model.entity.Theme;

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

            try (ThemeDao themeDao = JDBCDaoFactory.getInstance().createThemeDao()) {
                Theme theme = themeDao.findById(themeId);
                if (theme != null) {
                    theme.setName(name);
                    themeDao.update(theme);
                } else {
                    forward = "redirect:noCommand";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            forward = "redirect:noCommand";
        }
        return forward;
    }
}
