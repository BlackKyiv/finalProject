package ua.training.myWeb.commands;

import ua.training.myWeb.model.dao.EditionDao;
import ua.training.myWeb.model.dao.ThemeDao;
import ua.training.myWeb.model.dao.impl.JDBCDaoFactory;
import ua.training.myWeb.model.entity.Edition;
import ua.training.myWeb.model.entity.Theme;
import ua.training.myWeb.model.entity.enums.EditionStatus;
import ua.training.myWeb.services.DatabaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateEditionCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String forward = "redirect:editions";

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
            DatabaseService databaseService = new DatabaseService();
            try {
                databaseService.updateEdition(editionId, themeId, name, price, status);
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
