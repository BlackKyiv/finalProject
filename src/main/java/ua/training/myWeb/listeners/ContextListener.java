package ua.training.myWeb.listeners;

import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Context listener.
 *
 *
 */
public class ContextListener implements ServletContextListener {

    private static final Logger log = Logger.getLogger(ContextListener.class);

    public void contextInitialized(ServletContextEvent event) {
        log.debug("Context initializer started");
        ServletContext servletContext = event.getServletContext();
        servletContext.setAttribute("userAndLocales", new ConcurrentHashMap<String, String>());
        servletContext.setAttribute("users", new HashSet<String>());
        initCommandContainer();
        log.debug("Context initializer finished");
    }


    /**
     * Initializes CommandContainer.
     *
     */
    private void initCommandContainer() {
        log.debug("Command container initialization started");

        try {
            Class.forName("ua.training.myWeb.commands.CommandContainer");
        } catch (ClassNotFoundException ex) {
            log.error("Failed to initialize " + "ua.training.myWeb.commands.CommandContainer");
            throw new RuntimeException(ex);
        }

        log.debug("Command container initialization finished");
    }

}
