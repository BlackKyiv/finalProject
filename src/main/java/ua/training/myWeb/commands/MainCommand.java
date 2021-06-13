package ua.training.myWeb.commands;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.training.myWeb.Path;
import ua.training.myWeb.Servlet;
import ua.training.myWeb.services.DatabaseService;
import ua.training.myWeb.services.PageFillerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Main page command.
 *
 *
 */
public class MainCommand extends Command {

    private static final Logger logger = LogManager.getLogger(MainCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");
        try {
            PageFillerService pageFillerService = new PageFillerService();
            pageFillerService.fillMainPage(request);
            logger.trace("Filled main page");
        } catch (Exception e) {
            logger.error(e.getMessage());
            request.getSession().setAttribute("errorMessage", "Unexpected error");
            logger.debug("Command ends");
            return "redirect:noCommand";
        }

        logger.debug("Command ends");
        return Path.MAIN_PAGE;
    }
}
