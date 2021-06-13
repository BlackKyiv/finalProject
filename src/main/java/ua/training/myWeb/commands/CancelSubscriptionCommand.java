package ua.training.myWeb.commands;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.training.myWeb.model.entity.User;
import ua.training.myWeb.services.DatabaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cancel subscription command.
 *
 *
 */
public class CancelSubscriptionCommand extends Command {
    private static final Logger logger = LogManager.getLogger(CancelSubscriptionCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");
        String forward = "redirect:profile";
        if (request.getParameter("subscriptionId") != null &&
                request.getParameter("editionId") != null &&
                request.getSession().getAttribute("user") != null) {

            long editionId = Long.parseLong(request.getParameter("editionId"));
            long subscriptionId = Long.parseLong(request.getParameter("subscriptionId"));
            long userId = ((User) request.getSession().getAttribute("user")).getId();

            DatabaseService databaseService = new DatabaseService();
            try {
                databaseService.deleteSubscription(editionId, subscriptionId, userId);
                logger.trace("Deleted subscription with id " + subscriptionId);
            } catch (Exception e) {
                logger.error(e.getMessage());
                request.getSession().setAttribute("errorMessage", "This subscription doesnt exist");
                forward = "redirect:noCommand";
            }
        } else {
            forward = "redirect:profile";
        }

        logger.debug("Command ends");
        return forward;
    }


}
