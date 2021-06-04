package ua.training.myWeb.commands;

import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import ua.training.myWeb.Path;
import ua.training.myWeb.model.dao.SubscriptionDao;
import ua.training.myWeb.model.dao.impl.JDBCDaoFactory;
import ua.training.myWeb.model.entity.Subscription;
import ua.training.myWeb.model.entity.User;
import ua.training.myWeb.model.entity.enums.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ProfileCommand extends Command {

    private static final Logger log = Logger.getLogger(ProfileCommand.class);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        log.debug("Command starts");

        String forward;

        System.out.println();

        Role role = (Role) request.getSession().getAttribute("userRole");
        User user = (User) request.getSession().getAttribute("user");


        forward = Path.USER_PROFILE_PAGE;
        log.info(role.toString().toLowerCase() + " accessed profile");
        try (SubscriptionDao subscriptionDao = JDBCDaoFactory.getInstance().createSubscriptionDao()) {
            long page = 1;
            long recordsPerPage = 4;
            if (request.getParameter("page") != null)
                page = Integer.parseInt(request.getParameter("page"));

            List<List<Subscription>> subscriptionList = Lists.partition(
                    subscriptionDao.findByUserIdOffsetAndLimit(user.getId(), (page - 1) * recordsPerPage, recordsPerPage), 2);

            request.setAttribute("subscriptionList", subscriptionList);

            long noOfRecords = subscriptionDao.countUser(user.getId());
            long noOfPages = (long) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);

        } catch (Exception e) {
            e.printStackTrace();
            forward = Path.ERROR_PAGE;
        }


        log.debug("Command finished");

        return forward;
    }
}
