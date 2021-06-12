package ua.training.myWeb.commands;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.training.myWeb.Path;
import ua.training.myWeb.Servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoCommand extends Command {

    private static final Logger logger = LogManager.getLogger(Servlet.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");
        logger.error("Redirecting to error page with error message: " + request.getSession().getAttribute("errorMessage"));
        logger.debug("Command ends");
        return Path.ERROR_PAGE;
    }
}
