package ua.training.myWeb.commands;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.training.myWeb.Servlet;
import ua.training.myWeb.services.DatabaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SubscribeCommand extends Command {

    private static final Logger logger = LogManager.getLogger(SubscribeCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");
        String forward = "redirect:profile";
        if (request.getParameter("editionId") != null) {
            long editionId = Long.parseLong(request.getParameter("editionId"));
            try {
                DatabaseService databaseService = new DatabaseService();
                forward = databaseService.createSubscriptionTransaction(request, editionId, forward);
                logger.trace("Created subscription");
            } catch (Exception e) {
                logger.error(e.getMessage());
                request.setAttribute("errorMessage", "DB error");
                forward = "redirect:noCommand";
            }
        }
        logger.debug("Command ends");
        return forward;
    }


}