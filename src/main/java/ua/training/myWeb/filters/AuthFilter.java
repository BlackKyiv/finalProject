package ua.training.myWeb.filters;


import org.apache.log4j.Logger;
import ua.training.myWeb.Path;
import ua.training.myWeb.model.entity.User;
import ua.training.myWeb.model.entity.enums.Role;
import ua.training.myWeb.services.DatabaseService;
import ua.training.myWeb.services.FilterService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthFilter implements Filter {

    private static final Logger log = Logger.getLogger(AuthFilter.class);

    // commands access
    private static Map<Role, List<String>> accessMap = new HashMap<>();
    private static List<String> commons = new ArrayList<>();
    private static List<String> outOfControl = new ArrayList<>();

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {



        FilterService filterService = new FilterService();

        if (filterService.accessAllowed(request, commons, outOfControl, accessMap)) {
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


    public void init(FilterConfig fConfig) {
        FilterService filterService = new FilterService();
        accessMap.put(Role.ADMIN, filterService.asList(fConfig.getInitParameter("admin")));
        accessMap.put(Role.USER, filterService.asList(fConfig.getInitParameter("user")));

        commons = filterService.asList(fConfig.getInitParameter("common"));

        outOfControl = filterService.asList(fConfig.getInitParameter("out-of-control"));

    }


}
