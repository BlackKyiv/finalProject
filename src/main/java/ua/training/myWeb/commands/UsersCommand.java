package ua.training.myWeb.commands;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.training.myWeb.Path;
import ua.training.myWeb.services.PageFillerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UsersCommand extends Command {

    private static final Logger logger = LogManager.getLogger(UsersCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");
        long page = 1;
        long recordsPerPage = 6;
        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));
        PageFillerService pageFillerService = new PageFillerService();
        try {
            pageFillerService.fillUsersPage(request, page, recordsPerPage);
            logger.trace("Filled page with users");
        } catch (Exception e) {
            logger.error(e.getMessage());
            request.getSession().setAttribute("errorMessage", "Unexpected error");
            logger.debug("Command ends");
            return "redirect:noCommand";
        }
        logger.debug("Command ends");
        return Path.USERS_PAGE;
    }

}
