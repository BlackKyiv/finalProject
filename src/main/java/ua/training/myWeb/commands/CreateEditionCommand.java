package ua.training.myWeb.commands;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.training.myWeb.model.entity.enums.EditionStatus;
import ua.training.myWeb.services.DatabaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Create edition command.
 *
 *
 */
public class CreateEditionCommand extends Command {

    private static final Logger logger = LogManager.getLogger(CreateEditionCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");
        String forward = "redirect:editions";

        if (request.getParameter("name") != null &&
                request.getParameter("price") != null &&
                request.getParameter("themeId") != null &&
                request.getParameter("status") != null) {

            long themeId = Long.parseLong(request.getParameter("themeId"));
            String name = request.getParameter("name");
            double price = Double.parseDouble(request.getParameter("price"));
            EditionStatus status = EditionStatus.getEditionStatus(request.getParameter("status"));

            DatabaseService databaseService = new DatabaseService();
            try {
                databaseService.createEdition(themeId, name, price, status);
                logger.trace("Created new edition");
            } catch (Exception e) {
                logger.error(e.getMessage());
                request.getSession().setAttribute("errorMessage", "Error while creating edition");
                forward = "redirect:noCommand";
            }
        } else {
            logger.error("No parameters were added");
            request.getSession().setAttribute("errorMessage", "Not all fields were filled");
            forward = "redirect:noCommand";
        }
        logger.debug("Command ends");
        return forward;
    }


}
