package ua.training.myWeb.commands;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.training.myWeb.services.DatabaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Create theme command command.
 *
 *
 */
public class CreateThemeCommand extends Command {

    private static final Logger logger = LogManager.getLogger(CreateThemeCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");
        String forward = "redirect:themes";

        if (request.getParameter("name") != null) {
            String name = request.getParameter("name");
            DatabaseService databaseService = new DatabaseService();
            try {
                databaseService.createTheme(name);
                logger.trace("Created theme " + name);
            } catch (Exception e) {
                logger.error(e.getMessage());
                request.getSession().setAttribute("errorMessage", "Error while creating theme");
                forward = "redirect:noCommand";
            }
        } else {
            logger.error("No parameters were entered!");
            request.getSession().setAttribute("errorMessage", "Not all fields were filled");
            forward = "redirect:noCommand";
        }
        logger.debug("Command ends");
        return forward;
    }


}
