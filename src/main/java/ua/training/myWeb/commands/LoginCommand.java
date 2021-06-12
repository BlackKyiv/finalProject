package ua.training.myWeb.commands;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.training.myWeb.Path;
import ua.training.myWeb.model.entity.User;
import ua.training.myWeb.model.entity.enums.Role;
import ua.training.myWeb.model.entity.enums.UserStatus;
import ua.training.myWeb.services.ContextService;
import ua.training.myWeb.services.DatabaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.concurrent.ConcurrentHashMap;


public class LoginCommand extends Command {

    private static final Logger logger = LogManager.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");
        HttpSession session = request.getSession();
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        String errorMessage = null;
        String forward = "";

        if (login == null || password == null
                || login.isEmpty() || password.isEmpty()) {
            logger.trace("No parameters were entered");
            logger.debug("Command ends");
            return Path.LOGIN_PAGE;
        }

        User user = null;
        DatabaseService databaseService = new DatabaseService();
        try {
            user = databaseService.findUserByLogin(login);
            logger.trace("Found user in db");
        } catch (Exception e) {
            logger.error(e.getMessage());
            request.getSession().setAttribute("errorMessage", "Error while logging in");
            forward = "redirect:noCommand";
        }

        ContextService contextService = new ContextService();

        if (user == null || !password.equals(user.getPassword())) {
            logger.trace("User entered not existing password or login");
            errorMessage = "Cannot find user with such login/password";
            request.getSession().setAttribute("errorMessage", errorMessage);
            forward = "redirect:noCommand";
        } else if (user.getStatus() == UserStatus.BLOCKED) {
            logger.trace("Not allowed access to blocked user");
            errorMessage = "You were blocked go away!";
            request.getSession().setAttribute("errorMessage", errorMessage);
            forward = "redirect:noCommand";
        } else if (!contextService.isUserLogged(request, user)) {
            logger.trace("Not allowed access to already logged user");
            Role userRole = user.getRole();
            forward = "redirect:profile";
            session.setAttribute("user", user);
            session.setAttribute("userRole", userRole);
            Object localeMap = request.getSession().getServletContext().getAttribute("userAndLocales");
            contextService.getLoggedUsersSet(request).add(user.getLogin());
            if (localeMap instanceof ConcurrentHashMap) {
                ConcurrentHashMap<String, String> userLocales = (ConcurrentHashMap<String, String>) localeMap;
                request.getSession().setAttribute("lang", userLocales.get(user.getLogin()));
                logger.trace("Found user's locale and set" + userLocales.get(user.getLogin()));
            }
        } else if (contextService.isUserLogged(request, user)) {
            System.out.println("Does contain");
            errorMessage = "You ve already logged in!";
            request.getSession().setAttribute("errorMessage", errorMessage);
            forward = "redirect:noCommand";
        }

        logger.debug("Command ends");
        return forward;
    }


}
