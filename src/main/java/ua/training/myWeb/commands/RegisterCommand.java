package ua.training.myWeb.commands;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.training.myWeb.Path;
import ua.training.myWeb.services.DatabaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Register command.
 */
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

            if (login.matches("^(?=.*[A-Za-z0-9]$)[A-Za-z][A-Za-z\\d.-]{1,19}$")) {
                if (password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
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
                } else {
                    logger.trace("Password doesn't match requirements.");
                    request.getSession().setAttribute("errorMessage", "Password doesn't match requirements." +
                            " Must be Minimum eight characters, at least one letter and one number");
                    forward = "redirect:noCommand";
                }
            } else {
                logger.trace("Login doesnt match requirements");
                request.getSession().setAttribute("errorMessage", "Login doesnt match requirements. " +
                        "Must start with letter and ends with letter or number. Min number of symbols 1, max 19 ");
                forward = "redirect:noCommand";
            }
        }

        logger.debug("Command ends");
        return forward;
    }


}
