package ua.training.myWeb.services;

import ua.training.myWeb.model.entity.enums.Role;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class FilterService {
    public List<String> asList(String str) {
        List<String> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(str);
        while (st.hasMoreTokens()) list.add(st.nextToken());
        return list;
    }

    public boolean accessAllowed(ServletRequest request, List<String> commons, List<String> outOfControl, Map<Role, List<String>> accessMap) {
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
}
