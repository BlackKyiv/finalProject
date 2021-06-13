package ua.training.myWeb.commands;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Logout command.
 *
 *
 */
public class LogoutCommand extends Command {

    private static final Logger logger = LogManager.getLogger(LogoutCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        } else {
            logger.error("Session was already null");
        }

        logger.debug("Command ends");
        return "redirect:login";
    }
}
