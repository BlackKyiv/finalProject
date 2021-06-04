package ua.training.myWeb.commands;

import ua.training.myWeb.model.dao.ThemeDao;
import ua.training.myWeb.model.dao.UserDao;
import ua.training.myWeb.model.dao.impl.JDBCDaoFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteThemeCommand extends Command{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String forward = "redirect:themes";
        if (request.getParameter("themeId") != null) {
            long themeId = Long.parseLong(request.getParameter("themeId"));
            try (ThemeDao themeDao = JDBCDaoFactory.getInstance().createThemeDao()) {
                themeDao.delete(themeId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            forward = "redirect:noCommand";
        }

        return forward;
    }
}
