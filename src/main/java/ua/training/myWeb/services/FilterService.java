package ua.training.myWeb.services;

import ua.training.myWeb.model.entity.enums.Role;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;


/**
 * Services for actions associated with filters
 */
public class FilterService {
    /**
     * @param str String to split and create list
     * @return List of string spliced from str
     */
    public List<String> asList(String str) {
        List<String> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(str);
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
        return list;
    }


    /**
     * @param request      with data
     * @param commons      list of common commands
     * @param outOfControl list of free to use commands
     * @param accessMap    map of user allowances
     * @return boolean if accessed of this user allowed
     */
    public boolean accessAllowed(ServletRequest request, List<String> commons,
                                 List<String> outOfControl,
                                 Map<Role, List<String>> accessMap) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String commandName = request.getParameter("command");


        if (commandName == null || commandName.isEmpty()) {
            return false;
        }
        if (outOfControl.contains(commandName)) {
            return true;
        }
        HttpSession session = httpRequest.getSession(false);
        if (session == null) {
            return false;
        }
        Role userRole = (Role) session.getAttribute("userRole");
        if (userRole == null) {
            return false;
        }
        return accessMap.get(userRole).contains(commandName)
                || commons.contains(commandName);
    }
}
