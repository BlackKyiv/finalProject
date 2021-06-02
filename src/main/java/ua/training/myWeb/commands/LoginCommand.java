package ua.training.myWeb.commands;

import org.apache.log4j.Logger;
import ua.training.myWeb.Path;
import ua.training.myWeb.model.dao.UserDao;
import ua.training.myWeb.model.dao.impl.JDBCDaoFactory;
import ua.training.myWeb.model.entity.User;
import ua.training.myWeb.model.entity.enums.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LoginCommand extends Command {


    private static final Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Command starts");

        HttpSession session = request.getSession();

        // obtain login and password from the request
        String login = request.getParameter("login");
        log.trace("Request parameter: loging --> " + login);
        System.out.println("Request parameter: loging --> " + login);

        String password = request.getParameter("password");
        System.out.println("Request parameter: password --> " + password);

        String errorMessage = null;
        String forward = Path.LOGIN_PAGE;

        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            errorMessage = "Login/password cannot be empty";
            request.setAttribute("errorMessage", errorMessage);
            System.err.println("errorMessage --> " + errorMessage);
            return forward;
        }

        UserDao userDao = JDBCDaoFactory.getInstance().createUserDao();

        User user = userDao.findByLogin(login);

        try {
            userDao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Found in DB: user --> " + user);

        if (user == null || !password.equals(user.getPassword())) {
            errorMessage = "Cannot find user with such login/password";

            request.setAttribute("errorMessage", errorMessage);

            return forward;
        } else {
            Role userRole = user.getRole();
            log.trace("userRole --> " + userRole);
            forward = "redirect:profile";

            session.setAttribute("user", user);
            System.out.println("Set the session attribute: user --> " + user);

            System.out.println("Fucking command " + request.getAttribute("command"));

            session.setAttribute("userRole", userRole);
            System.out.println("Set the session attribute: userRole --> " + userRole);

            //forward = CommandContainer.get("profile").execute(request, response);
            System.out.println("User " + user + " logged as " + userRole.toString().toLowerCase());


            // work with i18n
            //TODO locale
//            String userLocaleName = user.getLocaleName();
//            log.trace("userLocalName --> " + userLocaleName);
//
//            if (userLocaleName != null && !userLocaleName.isEmpty()) {
//                Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", userLocaleName);
//
//                session.setAttribute("defaultLocale", userLocaleName);
//
//
//                log.trace("Set the session attribute: defaultLocaleName --> " + userLocaleName);
//                log.info("Locale for user: defaultLocale --> " + userLocaleName);
//            }

        }

        System.out.println("Fucking command " + request.getAttribute("command"));
        return forward;
    }
}
