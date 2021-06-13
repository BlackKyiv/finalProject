package ua.training.myWeb.commands;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.training.myWeb.services.DatabaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Delete theme command.
 *
 *
 */
public class DeleteThemeCommand extends Command {

    private static final Logger logger = LogManager.getLogger(DeleteThemeCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");
        String forward = "redirect:themes";
        if (request.getParameter("themeId") != null) {
            long themeId = Long.parseLong(request.getParameter("themeId"));
            DatabaseService databaseService = new DatabaseService();
            try {
                databaseService.deleteTheme(themeId);
                logger.trace("Deleted theme");
            } catch (Exception e) {
                logger.error(e.getMessage());
                request.getSession().setAttribute("errorMessage", "This subscription doesnt exist");
                forward = "redirect:noCommand";
            }
        } else {
            logger.error("No parameters were entered");
            forward = "redirect:themes";
        }
        logger.debug("Command ends");
        return forward;
    }


}
