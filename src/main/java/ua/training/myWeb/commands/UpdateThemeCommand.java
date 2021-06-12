package ua.training.myWeb.commands;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.training.myWeb.services.DatabaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateThemeCommand extends Command {

    private static final Logger logger = LogManager.getLogger(UpdateThemeCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");
        String forward = "redirect:themes";
        if (request.getParameter("themeId") != null &&
                request.getParameter("name") != null) {

            long themeId = Long.parseLong(request.getParameter("themeId"));
            String name = request.getParameter("name");
            DatabaseService databaseService = new DatabaseService();
            try {
                databaseService.updateTheme(themeId, name);
                logger.trace("Updated theme");
            } catch (Exception e) {
                logger.error(e.getMessage());
                request.getSession().setAttribute("errorMessage", "Unexpected error");
                forward = "redirect:noCommand";
            }
        } else {
            logger.error("Not enough parameters");
            forward = "redirect:noCommand";
        }
        logger.debug("Command ends");
        return forward;
    }


}
