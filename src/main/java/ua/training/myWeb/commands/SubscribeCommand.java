package ua.training.myWeb.commands;


import ua.training.myWeb.Path;
import ua.training.myWeb.model.dao.EditionDao;
import ua.training.myWeb.model.dao.SubscriptionDao;
import ua.training.myWeb.model.dao.UserDao;
import ua.training.myWeb.model.dao.impl.JDBCDaoFactory;
import ua.training.myWeb.model.entity.Edition;
import ua.training.myWeb.model.entity.Subscription;
import ua.training.myWeb.model.entity.User;
import ua.training.myWeb.model.entity.enums.SubscriptionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class SubscribeCommand extends Command {



    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String forward = "redirect:profile";
        if (request.getParameter("editionId") != null) {
            long editionId = Long.parseLong(request.getParameter("editionId"));

            SubscriptionDao subscriptionDao = JDBCDaoFactory.getInstance().createSubscriptionDao();


            try (UserDao userDao = JDBCDaoFactory.getInstance().createUserDao();
                 EditionDao editionDao = JDBCDaoFactory.getInstance().createEditionDao();) {
                User currentUser = (User) request.getSession().getAttribute("user");

                if (subscriptionDao.findByUserIdAndEditionId(currentUser.getId(), editionId) == null) {
                    Edition edition = editionDao.findById(editionId);
                    User user = userDao.findById(currentUser.getId());
                    double price = edition.getPrice();
                    double left = user.getAccount();
                    if (left >= price) {
                        Subscription subscription = new Subscription();
                        subscription.setUser(user);
                        subscription.setEdition(edition);
                        subscription.setNextPayDay(getNextMonthDate());
                        subscription.setStatus(SubscriptionStatus.ACTIVE);
                        user.setAccount(left - price);
                        subscriptionDao.create(subscription);
                        subscriptionDao.close();
                        userDao.update(user);
                        request.getSession().setAttribute("user", user);
                        request.getSession().setAttribute("userRole", user.getRole());
                    } else {
                        request.setAttribute("errorMessage", "Not enough money on account");
                        forward = Path.ERROR_PAGE;
                    }
                } else {
                    request.setAttribute("errorMessage", "You have already subscribed");
                    forward = Path.ERROR_PAGE;
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "DB error");
                forward = Path.ERROR_PAGE;
            }
        }

        return forward;
    }


    private Date getNextMonthDate() {

        Calendar c = new GregorianCalendar();
        c.setTime(new Date(new java.util.Date().getTime()));
        c.add(Calendar.DATE, 30);

        return new Date(c.getTime().getTime());
    }

}