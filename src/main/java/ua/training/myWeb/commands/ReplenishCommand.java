package ua.training.myWeb.commands;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.training.myWeb.Path;
import ua.training.myWeb.model.entity.User;
import ua.training.myWeb.services.DatabaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Replenish command.
 *
 *
 */
public class ReplenishCommand extends Command {

    private static final Logger logger = LogManager.getLogger(ReplenishCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");
        String forward = Path.USER_REPLENISH_PAGE;
        if (request.getParameter("replenish") != null) {
            logger.trace("Request has all parameters");
            forward = "redirect:replenish";
            int replenishAmount = Integer.parseInt(request.getParameter("replenish"));
            if (replenishAmount < 0) {
                logger.debug("Command ends");
                return Path.ERROR_PAGE;
            }
            DatabaseService databaseService = new DatabaseService();
            try {
                databaseService.replenishThisUsersAccount((User) request.getSession().getAttribute("user"), replenishAmount);
                logger.trace("Replenished account for user");
            } catch (Exception e) {
                logger.error(e.getMessage());
                request.getSession().setAttribute("errorMessage", "Unexpected error");
                forward = "redirect:noCommand";
            }
        }

        logger.debug("Command ends");
        return forward;
    }


}
