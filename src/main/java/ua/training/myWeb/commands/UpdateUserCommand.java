package ua.training.myWeb.commands;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.training.myWeb.Servlet;
import ua.training.myWeb.model.entity.enums.UserStatus;
import ua.training.myWeb.services.DatabaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateUserCommand extends Command {

    private static final Logger logger = LogManager.getLogger(UpdateUserCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");
        String forward = "redirect:users";
        if (request.getParameter("userId") != null &&
                request.getParameter("account") != null &&
                request.getParameter("status") != null) {

            long userId = Long.parseLong(request.getParameter("userId"));
            String login = request.getParameter("login");
            double account = Double.parseDouble(request.getParameter("account"));
            UserStatus status = UserStatus.getUserStatus(request.getParameter("status"));
            DatabaseService databaseService = new DatabaseService();
            try {
                databaseService.updateUser(userId, login, account, status);
                logger.trace("Updated user");
            } catch (Exception e) {
                logger.error(e.getMessage());
                request.getSession().setAttribute("errorMessage", "Unexpected error");
                forward = "redirect:noCommand";
            }
        } else {
            logger.error("Not enough parameters");
            forward = "redirect:profile";
        }
        logger.debug("Command ends");
        return forward;
    }


}
