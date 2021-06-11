package ua.training.myWeb.listeners;

import ua.training.myWeb.model.entity.User;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Set;

public class SessionDestroyerListener implements HttpSessionListener {
    public void sessionDestroyed(HttpSessionEvent se) {
        try {
            HttpSession session = se.getSession();
            if(session.getAttribute("user") instanceof User){
                if(session.getServletContext().getAttribute("users") instanceof Set){
                    Set users = (Set) session.getServletContext().getAttribute("users");
                    users.remove(((User) session.getAttribute("user")).getLogin());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
