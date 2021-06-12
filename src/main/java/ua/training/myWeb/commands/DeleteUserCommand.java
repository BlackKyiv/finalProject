package ua.training.myWeb.commands;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.training.myWeb.Servlet;
import ua.training.myWeb.model.dao.UserDao;
import ua.training.myWeb.model.dao.impl.JDBCDaoFactory;
import ua.training.myWeb.model.entity.User;
import ua.training.myWeb.model.entity.enums.UserStatus;
import ua.training.myWeb.services.DatabaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteUserCommand extends Command{
    private static final Logger logger = LogManager.getLogger(DeleteUserCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");
        String forward = "redirect:users";
        if (request.getParameter("userId") != null) {
            long userId = Long.parseLong(request.getParameter("userId"));
            DatabaseService databaseService = new DatabaseService();
            try {
                databaseService.deleteUser(userId);
                logger.trace("Deleted user");
            }
            catch (Exception e) {
                logger.error(e.getMessage());
                request.getSession().setAttribute("errorMessage", "Cannot delete this user");
                forward = "redirect:noCommand";
            }
        } else {
            logger.error("No parameters were entered");
            forward = "redirect:users";
        }
        logger.debug("Command ends");
        return forward;
    }


}
