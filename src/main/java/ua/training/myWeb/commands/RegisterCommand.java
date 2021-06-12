package ua.training.myWeb.commands;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.training.myWeb.Path;
import ua.training.myWeb.Servlet;
import ua.training.myWeb.services.DatabaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterCommand extends Command {

    private static final Logger logger = LogManager.getLogger(RegisterCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");
        String forward = Path.REGISTER_PAGE;

        if (request.getParameter("login") != null &&
                request.getParameter("password") != null &&
                request.getParameter("passwordRepeat") != null) {
            logger.trace("All parameters found");
            forward = "redirect:register";
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            String passwordRepeat = request.getParameter("passwordRepeat");

            if (password.equals(passwordRepeat)) {
                DatabaseService databaseService = new DatabaseService();
                try {
                    if (databaseService.createNewUser(login, password)) {
                        logger.trace("Successfully created new user");
                        forward = "redirect:login";
                    } else {
                        logger.trace("This user already exists");
                        request.getSession().setAttribute("errorMessage", "This user already exists");
                        forward = "redirect:noCommand";
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    request.getSession().setAttribute("errorMessage", "Unexpected error");
                    forward = "redirect:noCommand";
                }
            }
        }

        logger.debug("Command ends");
        return forward;
    }


}
