package ua.training.myWeb.commands;

import ua.training.myWeb.Path;
import ua.training.myWeb.model.dao.UserDao;
import ua.training.myWeb.model.dao.impl.JDBCDaoFactory;
import ua.training.myWeb.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReplenishCommand extends Command {


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String forward = Path.USER_REPLENISH_PAGE;
        if (request.getParameter("replenish") != null) {
            forward = "redirect:replenish";
            int replenishAmount = Integer.parseInt(request.getParameter("replenish"));
            if (replenishAmount < 0) {
                return Path.ERROR_PAGE;
            }
            try (UserDao userDao = JDBCDaoFactory.getInstance().createUserDao()) {
                User currentUser = (User) request.getSession().getAttribute("user");
                User user = userDao.findById(currentUser.getId());
                user.setAccount(user.getAccount() + replenishAmount);
                System.out.println(user);
                userDao.update(user);
                request.getSession().setAttribute("user", user);
            } catch (Exception e) {
                e.printStackTrace();
                forward = Path.ERROR_PAGE;
            }
        }

        return forward;
    }
}
