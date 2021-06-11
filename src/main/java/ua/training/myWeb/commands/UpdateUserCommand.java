package ua.training.myWeb.commands;

import ua.training.myWeb.model.dao.UserDao;
import ua.training.myWeb.model.dao.impl.JDBCDaoFactory;
import ua.training.myWeb.model.entity.User;
import ua.training.myWeb.model.entity.enums.UserStatus;
import ua.training.myWeb.services.DatabaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateUserCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
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
            } catch (Exception e) {
                request.getSession().setAttribute("errorMessage", "Unexpected error");
                forward = "redirect:noCommand";
            }
        } else {
            forward = "redirect:profile";
        }

        return forward;
    }


}
