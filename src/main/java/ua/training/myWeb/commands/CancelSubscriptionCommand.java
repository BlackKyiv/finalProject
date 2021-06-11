package ua.training.myWeb.commands;

import ua.training.myWeb.model.dao.SubscriptionDao;
import ua.training.myWeb.model.dao.impl.JDBCDaoFactory;
import ua.training.myWeb.model.entity.Subscription;
import ua.training.myWeb.model.entity.User;
import ua.training.myWeb.services.DatabaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CancelSubscriptionCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String forward = "redirect:profile";
        if (request.getParameter("subscriptionId") != null &&
                request.getParameter("editionId") != null &&
                request.getSession().getAttribute("user") != null) {

            long editionId = Long.parseLong(request.getParameter("editionId"));
            long subscriptionId = Long.parseLong(request.getParameter("subscriptionId"));
            long userId = ((User) request.getSession().getAttribute("user")).getId();

            DatabaseService databaseService = new DatabaseService();
            try {
                databaseService.deleteSubscription(editionId, subscriptionId, userId);
            } catch (Exception e) {
                e.printStackTrace();
                request.getSession().setAttribute("errorMessage", "This subscription doesnt exist");
                forward = "redirect:noCommand";
            }
        } else {
            forward = "redirect:profile";
        }

        return forward;
    }


}
