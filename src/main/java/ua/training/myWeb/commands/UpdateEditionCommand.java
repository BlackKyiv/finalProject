package ua.training.myWeb.commands;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.training.myWeb.model.entity.enums.EditionStatus;
import ua.training.myWeb.services.DatabaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateEditionCommand extends Command {

    private static final Logger logger = LogManager.getLogger(UpdateEditionCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");
        String forward = "redirect:editions";

        if (request.getParameter("editionId") != null &&
                request.getParameter("name") != null &&
                request.getParameter("price") != null &&
                request.getParameter("themeId") != null &&
                request.getParameter("status") != null) {
            logger.trace("Request has all parameters");
            long editionId = Long.parseLong(request.getParameter("editionId"));
            long themeId = Long.parseLong(request.getParameter("themeId"));
            String name = request.getParameter("name");
            double price = Double.parseDouble(request.getParameter("price"));
            EditionStatus status = EditionStatus.getEditionStatus(request.getParameter("status"));
            DatabaseService databaseService = new DatabaseService();
            try {
                databaseService.updateEdition(editionId, themeId, name, price, status);
                logger.trace("Updated edition");
            } catch (Exception e) {
                logger.error(e.getMessage());
                request.getSession().setAttribute("errorMessage", "Unexpected error");
                forward = "redirect:noCommand";
            }
        } else {
            forward = "redirect:noCommand";
        }

        logger.debug("Command ends");
        return forward;
    }


}
