package ua.training.myWeb.commands;

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


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        String errorMessage = null;
        String forward = "";

        if (login == null || password == null
                || login.isEmpty() || password.isEmpty()) {
            return Path.LOGIN_PAGE;
        }

        User user = null;
        DatabaseService databaseService = new DatabaseService();
        try {
            user = databaseService.findUserByLogin(login);
        } catch (Exception e) {
            request.getSession().setAttribute("errorMessage", "Error while logging in");
            forward = "redirect:noCommand";
        }

        ContextService contextService = new ContextService();

        if (user == null || !password.equals(user.getPassword())) {
            errorMessage = "Cannot find user with such login/password";
            request.getSession().setAttribute("errorMessage", errorMessage);
            forward = "redirect:noCommand";
        } else if (user.getStatus() == UserStatus.BLOCKED) {
            errorMessage = "You were blocked go away!";
            request.getSession().setAttribute("errorMessage", errorMessage);
            forward = "redirect:noCommand";
        } else if (!contextService.isUserLogged(request, user)) {
            Role userRole = user.getRole();
            forward = "redirect:profile";
            session.setAttribute("user", user);
            session.setAttribute("userRole", userRole);
            Object localeMap = request.getSession().getServletContext().getAttribute("userAndLocales");
            contextService.getLoggedUsersSet(request).add(user.getLogin());
            if (localeMap instanceof ConcurrentHashMap) {
                ConcurrentHashMap<String, String> userLocales = (ConcurrentHashMap<String, String>) localeMap;
                request.getSession().setAttribute("lang", userLocales.get(user.getLogin()));
            }
        } else if (contextService.isUserLogged(request, user)) {
            System.out.println("Does contain");
            errorMessage = "You ve already logged in!";
            request.getSession().setAttribute("errorMessage", errorMessage);
            forward = "redirect:noCommand";
        }

        return forward;
    }


}
