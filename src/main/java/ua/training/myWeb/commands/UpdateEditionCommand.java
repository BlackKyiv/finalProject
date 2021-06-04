package ua.training.myWeb.commands;

import ua.training.myWeb.model.dao.EditionDao;
import ua.training.myWeb.model.dao.ThemeDao;
import ua.training.myWeb.model.dao.impl.JDBCDaoFactory;
import ua.training.myWeb.model.entity.Edition;
import ua.training.myWeb.model.entity.Theme;
import ua.training.myWeb.model.entity.enums.EditionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateEditionCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String forward = "redirect:editions";


        System.out.println(request.getParameter("editionId") + " " + request.getParameter("name") + " " + request.getParameter("price")  + " " + request.getParameter("themeId") + " " + request.getParameter("status"));


        if (request.getParameter("editionId") != null &&
                request.getParameter("name") != null &&
                request.getParameter("price") != null &&
                request.getParameter("themeId") != null &&
                request.getParameter("status") != null) {

            long editionId = Long.parseLong(request.getParameter("editionId"));
            long themeId = Long.parseLong(request.getParameter("themeId"));
            String name = request.getParameter("name");
            double price = Double.parseDouble(request.getParameter("price"));
            EditionStatus status = EditionStatus.getEditionStatus(request.getParameter("status"));


            try (EditionDao editionDao = JDBCDaoFactory.getInstance().createEditionDao();
                 ThemeDao themeDao = JDBCDaoFactory.getInstance().createThemeDao()) {
                Edition edition = editionDao.findById(editionId);
                Theme theme = themeDao.findById(themeId);
                System.out.println(themeId);
                System.out.println(theme.getId());
                System.out.println(theme.getName());
                if (edition != null && theme != null) {
                    edition.setName(name);
                    edition.setPrice(price);
                    edition.setStatus(status);
                    edition.setTheme(theme);
                    editionDao.update(edition);
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
