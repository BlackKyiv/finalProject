package ua.training.myWeb.services;

import ua.training.myWeb.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public class ContextService {
    public Set<String> getLoggedUsersSet(HttpServletRequest request) {
        return (Set<String>) request.getSession().getServletContext().getAttribute("users");
    }

    public boolean isUserLogged(HttpServletRequest request, User user) {
        return (getLoggedUsersSet(request)).contains(user.getLogin());
    }
}
