package ua.training.myWeb.filters;


import org.apache.log4j.Logger;
import ua.training.myWeb.Path;
import ua.training.myWeb.model.entity.enums.Role;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

public class AuthFilter implements Filter {

    private static final Logger log = Logger.getLogger(AuthFilter.class);

    // commands access
    private static Map<Role, List<String>> accessMap = new HashMap<>();
    private static List<String> commons = new ArrayList<>();
    private static List<String> outOfControl = new ArrayList<>();

    public void destroy() {
//        log.debug("Filter destruction starts");
        // do nothing
//        log.debug("Filter destruction finished");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("Filter starts");

        System.out.println("Filter");
        if (accessAllowed(request)) {
            System.out.println("Access allowed");
            chain.doFilter(request, response);
        } else {
            String errorMessasge = "You do not have permission to access the requested resource";

            System.out.println("Access not allowed");
            request.setAttribute("errorMessage", errorMessasge);
            log.trace("Set the request attribute: errorMessage --> " + errorMessasge);
            request.getRequestDispatcher(Path.ERROR_PAGE)
                    .forward(request, response);
        }
    }

    private boolean accessAllowed(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String commandName = request.getParameter("command");

        System.out.println(commandName);

        if (commandName == null || commandName.isEmpty())
            return false;

        if (outOfControl.contains(commandName))
            return true;

        HttpSession session = httpRequest.getSession(false);
        if (session == null)
            return false;

        Role userRole = (Role) session.getAttribute("userRole");
        if (userRole == null)
            return false;

        return accessMap.get(userRole).contains(commandName)
                || commons.contains(commandName);
    }

    public void init(FilterConfig fConfig) {
//        log.debug("Filter initialization starts");

        // roles
        accessMap.put(Role.ADMIN, asList(fConfig.getInitParameter("admin")));
        accessMap.put(Role.USER, asList(fConfig.getInitParameter("user")));

//        log.trace("Access map --> " + accessMap);

        // commons
        commons = asList(fConfig.getInitParameter("common"));
//        log.trace("Common commands --> " + commons);

        // out of control
        outOfControl = asList(fConfig.getInitParameter("out-of-control"));
//        log.trace("Out of control commands --> " + outOfControl);

//        log.debug("Filter initialization finished");
    }

    private List<String> asList(String str) {
        List<String> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(str);
        while (st.hasMoreTokens()) list.add(st.nextToken());
        return list;
    }
}
