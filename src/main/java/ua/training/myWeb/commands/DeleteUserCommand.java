package ua.training.myWeb.commands;

import ua.training.myWeb.model.dao.UserDao;
import ua.training.myWeb.model.dao.impl.JDBCDaoFactory;
import ua.training.myWeb.model.entity.User;
import ua.training.myWeb.model.entity.enums.UserStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteUserCommand extends Command{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String forward = "redirect:users";
        if (request.getParameter("userId") != null) {
            long userId = Long.parseLong(request.getParameter("userId"));
            try (UserDao userDao = JDBCDaoFactory.getInstance().createUserDao()) {
                userDao.delete(userId);
            } catch (Exception e) {
                request.getSession().setAttribute("errorMessage", "Cannot delete this user");
                forward = "redirect:noCommand";
            }
        } else {
            forward = "redirect:users";
        }

        return forward;
    }
}
