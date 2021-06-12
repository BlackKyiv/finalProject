package ua.training.myWeb.listeners;

import org.apache.log4j.Logger;
import ua.training.myWeb.filters.AuthFilter;
import ua.training.myWeb.model.entity.User;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Set;

public class SessionDestroyerListener implements HttpSessionListener {

    private static final Logger log = Logger.getLogger(SessionDestroyerListener.class);


    public void sessionDestroyed(HttpSessionEvent se) {
        log.debug("Listener started");
        try {
            HttpSession session = se.getSession();
            if(session.getAttribute("user") instanceof User){
                if(session.getServletContext().getAttribute("users") instanceof Set){
                    Set users = (Set) session.getServletContext().getAttribute("users");
                    log.trace("Session ended for user " + ((User) session.getAttribute("user")).getLogin());
                    users.remove(((User) session.getAttribute("user")).getLogin());
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.debug("Listener ended");
    }
}
