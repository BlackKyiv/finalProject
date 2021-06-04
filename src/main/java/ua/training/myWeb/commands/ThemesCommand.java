package ua.training.myWeb.commands;

import ua.training.myWeb.Path;
import ua.training.myWeb.model.dao.EditionDao;
import ua.training.myWeb.model.dao.ThemeDao;
import ua.training.myWeb.model.dao.impl.JDBCDaoFactory;
import ua.training.myWeb.model.entity.Edition;
import ua.training.myWeb.model.entity.Theme;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ThemesCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try (ThemeDao themeDao = JDBCDaoFactory.getInstance().createThemeDao()) {

            List<Theme> themeList = themeDao.findAll();

            request.setAttribute("themes", themeList);
        } catch (Exception e) {
            e.printStackTrace();
            return Path.ERROR_PAGE;
        }

        return Path.THEMES_PAGE;
    }
}
