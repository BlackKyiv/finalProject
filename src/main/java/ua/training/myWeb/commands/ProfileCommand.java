package ua.training.myWeb.commands;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.training.myWeb.Path;
import ua.training.myWeb.model.entity.User;
import ua.training.myWeb.services.PageFillerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Profile command.
 *
 *
 */
public class ProfileCommand extends Command {

    private static final Logger logger = LogManager.getLogger(ProfileCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");
        String forward = Path.USER_PROFILE_PAGE;
        User user = (User) request.getSession().getAttribute("user");
        PageFillerService pageFillerService = new PageFillerService();
        try {
            pageFillerService.fillProfilePage(request, user);
            logger.trace("Filled profile page");
        } catch (Exception e) {
            logger.error(e.getMessage());
            request.getSession().setAttribute("errorMessage", "Unexpected error");
            forward = "redirect:noCommand";
        }

        logger.debug("Command ends");
        return forward;
    }


}
