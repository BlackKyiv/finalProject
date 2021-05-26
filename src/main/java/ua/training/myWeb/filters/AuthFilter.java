package ua.training.myWeb.filters;


import ua.training.myWeb.model.entity.Role;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

public class AuthFilter implements Filter {

    private static Map<Role, List<String>> accessMap = new HashMap<Role, List<String>>();
    private static List<String> commons = new ArrayList<String>();
    private static List<String> outOfControl = new ArrayList<String>();


    public void init(FilterConfig fConfig) throws ServletException {

        accessMap.put(Role.ADMIN, asList(fConfig.getInitParameter("admin")));
        accessMap.put(Role.USER, asList(fConfig.getInitParameter("user")));
        commons = asList(fConfig.getInitParameter("common"));
        outOfControl = asList(fConfig.getInitParameter("out-of-control"));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI();
        req.getSession();

        //TODO do filter
        chain.doFilter(request, response);
    }


    private List<String> asList(String str) {
        List<String> list = new ArrayList<String>();
        if (str != null) {
            StringTokenizer st = new StringTokenizer(str);
            while (st.hasMoreTokens()) list.add(st.nextToken());
        }
        return list;
    }
}
