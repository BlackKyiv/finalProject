package ua.training.myWeb.services;

import ua.training.myWeb.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * Services for actions associated wit context
 */
public class ContextService {
    /**
     * @param request with data
     * @return Set of users, who are currently logged in
     */
    public Set<String> getLoggedUsersSet(HttpServletRequest request) {
        Object res = request.getSession().getServletContext().getAttribute("users");
        if (res instanceof Set) return (Set) res;
        else return null;
    }

    /**
     * @param request servlet request with data
     * @param user    user to check if logged
     * @return boolean if user is currently logged in
     */
    public boolean isUserLogged(HttpServletRequest request, User user) {
        return (getLoggedUsersSet(request)).contains(user.getLogin());
    }
}
