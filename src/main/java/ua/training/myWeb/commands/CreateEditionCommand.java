package ua.training.myWeb.commands;

import ua.training.myWeb.model.dao.EditionDao;
import ua.training.myWeb.model.dao.impl.JDBCDaoFactory;
import ua.training.myWeb.model.entity.Edition;
import ua.training.myWeb.model.entity.Theme;
import ua.training.myWeb.model.entity.enums.EditionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateEditionCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String forward = "redirect:editions";

        if (request.getParameter("name") != null &&
                request.getParameter("price") != null &&
                request.getParameter("themeId") != null &&
                request.getParameter("status") != null) {

            System.out.println("Creating edition");
            long themeId = Long.parseLong(request.getParameter("themeId"));
            String name = request.getParameter("name");
            double price = Double.parseDouble(request.getParameter("price"));
            EditionStatus status = EditionStatus.getEditionStatus(request.getParameter("status"));

            try (EditionDao editionDao = JDBCDaoFactory.getInstance().createEditionDao()) {
                Edition edition = new Edition();
                edition.setName(name);
                edition.setPrice(price);
                edition.setStatus(status);
                Theme theme = new Theme();
                theme.setId(themeId);
                edition.setTheme(theme);
                editionDao.create(edition);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            forward = "redirect:noCommand";
        }

        return forward;
    }
}
