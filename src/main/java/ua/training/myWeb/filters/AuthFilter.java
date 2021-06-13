package ua.training.myWeb.filters;


import org.apache.log4j.Logger;
import ua.training.myWeb.Path;
import ua.training.myWeb.model.entity.enums.Role;
import ua.training.myWeb.services.FilterService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Security filter.
 * Restricts access to pages for unauthorized users
 *
 */
public class AuthFilter implements Filter {

    private static final Logger log = Logger.getLogger(AuthFilter.class);

    private static final Map<Role, List<String>> accessMap = new HashMap<>();
    private static List<String> commons = new ArrayList<>();
    private static List<String> outOfControl = new ArrayList<>();

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("Filter started");
        FilterService filterService = new FilterService();

        HttpServletRequest req = (HttpServletRequest) request;

        if (filterService.accessAllowed(request, commons, outOfControl, accessMap)) {
            log.trace("This user is allowed to " + request.getParameter("command"));
            chain.doFilter(request, response);
        } else {
            String errorMessage = "You do not have permission to access the requested resource";
            log.trace("This user is not allowed to " + request.getParameter("command"));
            req.getSession().setAttribute("errorMessage", errorMessage);
            log.trace("Set the request attribute: errorMessage --> " + errorMessage);
            request.getRequestDispatcher(Path.ERROR_PAGE)
                    .forward(request, response);
        }

        log.debug("Filter ended");
    }


    public void init(FilterConfig fConfig) {
        log.debug("Initializing filter");
        FilterService filterService = new FilterService();
        accessMap.put(Role.ADMIN, filterService.asList(fConfig.getInitParameter("admin")));
        accessMap.put(Role.USER, filterService.asList(fConfig.getInitParameter("user")));

        commons = filterService.asList(fConfig.getInitParameter("common"));

        outOfControl = filterService.asList(fConfig.getInitParameter("out-of-control"));
        log.debug("Initialized filter");

    }


}
