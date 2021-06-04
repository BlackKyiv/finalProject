package ua.training.myWeb.commands;

import ua.training.myWeb.model.dao.UserDao;
import ua.training.myWeb.model.dao.impl.JDBCDaoFactory;
import ua.training.myWeb.model.entity.User;
import ua.training.myWeb.model.entity.enums.UserStatus;

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

            try (UserDao userDao = JDBCDaoFactory.getInstance().createUserDao()) {
                User user = userDao.findById(userId);
                if (user != null) {
                    user.setLogin(login);
                    user.setAccount(account);
                    user.setStatus(status);
                    userDao.update(user);
                } else {
                    forward = "redirect:noCommand";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            forward = "redirect:profile";
        }

        return forward;
    }
}
