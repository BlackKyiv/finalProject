package ua.training.myWeb.commands;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.training.myWeb.services.DatabaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteEditionCommand extends Command {

    private static final Logger logger = LogManager.getLogger(DeleteEditionCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");
        String forward = "redirect:editions";
        if (request.getParameter("editionId") != null) {
            long userId = Long.parseLong(request.getParameter("editionId"));
            DatabaseService databaseService = new DatabaseService();
            try {
                databaseService.deleteEdition(userId);
                logger.trace("Deleted edition");
            } catch (Exception e) {
                logger.error(e.getMessage());
                request.getSession().setAttribute("errorMessage", "Cannot delete this edition");
                forward = "redirect:noCommand";
            }
        } else {
            logger.error("No parameters were entered");
            forward = "redirect:editions";
        }
        logger.debug("Command ends");
        return forward;
    }


}
