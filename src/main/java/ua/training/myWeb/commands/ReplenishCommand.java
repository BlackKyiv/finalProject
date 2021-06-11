package ua.training.myWeb.commands;

import ua.training.myWeb.Path;
import ua.training.myWeb.model.dao.UserDao;
import ua.training.myWeb.model.dao.impl.JDBCDaoFactory;
import ua.training.myWeb.model.entity.User;
import ua.training.myWeb.services.DatabaseService;

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
            DatabaseService databaseService = new DatabaseService();
            try {
                databaseService.replenishThisUsersAccount((User) request.getSession().getAttribute("user"), replenishAmount);
            } catch (Exception e) {
                request.getSession().setAttribute("errorMessage", "Unexpected error");
                forward = "redirect:noCommand";
            }
        }

        return forward;
    }


}
