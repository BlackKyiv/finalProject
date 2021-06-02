package ua.training.myWeb.commands;

import ua.training.myWeb.Path;
import ua.training.myWeb.model.dao.SubscriptionDao;
import ua.training.myWeb.model.dao.impl.JDBCDaoFactory;
import ua.training.myWeb.model.entity.Subscription;
import ua.training.myWeb.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CancelSubscriptionCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String forward = Path.USER_PROFILE_PAGE;
        System.out.println("canceling " + request.getParameter("subscriptionId"));
        System.out.println("canceling " + request.getSession().getAttribute("user"));
        if (request.getParameter("subscriptionId") != null &&
                request.getParameter("editionId") != null &&
                request.getSession().getAttribute("user") != null) {

            long editionId = Long.parseLong(request.getParameter("editionId"));
            long subscriptionId = Long.parseLong(request.getParameter("subscriptionId"));
            long userId = ((User) request.getSession().getAttribute("user")).getId();
            System.out.println(editionId);

            try (SubscriptionDao subscriptionDao = JDBCDaoFactory.getInstance().createSubscriptionDao()) {
                Subscription subscription = subscriptionDao.findByUserIdAndEditionId(userId, editionId);
                System.out.println(subscription);
                if (subscription != null && subscription.getId() == subscriptionId) {
                    subscriptionDao.delete(subscriptionId);

                } else {
                    forward = "noCommand";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            forward = "profile";
        }

        return forward;
    }
}
