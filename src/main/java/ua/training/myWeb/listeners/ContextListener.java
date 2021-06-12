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

public class ContextListener implements ServletContextListener {

    private static final Logger log = Logger.getLogger(ContextListener.class);

    public void contextInitialized(ServletContextEvent event) {
        log.debug("Context initializer started");
        ServletContext servletContext = event.getServletContext();
        servletContext.setAttribute("userAndLocales", new ConcurrentHashMap<String, String>());
        servletContext.setAttribute("users", new HashSet<String>());
        initCommandContainer();
//        initI18N(servletContext);
        log.debug("Context initializer finished");
    }

//    private void initI18N(ServletContext servletContext) {
//        log.debug("I18N subsystem initialization started");
//
//        String localesValue = servletContext.getInitParameter("locales");
//        if (localesValue == null || localesValue.isEmpty()) {
//            log.warn("'locales' init parameter is empty, the default encoding will be used");
//        } else {
//            List<String> locales = new ArrayList<String>();
//            StringTokenizer st = new StringTokenizer(localesValue);
//            while (st.hasMoreTokens()) {
//                String localeName = st.nextToken();
//                locales.add(localeName);
//            }
//
//            log.debug("Application attribute set: locales --> " + locales);
//            servletContext.setAttribute("locales", locales);
//        }
//
//        log.debug("I18N subsystem initialization finished");
//    }

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
