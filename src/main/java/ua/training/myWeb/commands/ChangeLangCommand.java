package ua.training.myWeb.commands;

import ua.training.myWeb.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ConcurrentHashMap;

public class ChangeLangCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String lang = request.getParameter("lang");
        if (lang != null) {
            request.getSession().setAttribute("lang", lang);
            Object localeMap = request.getSession().getServletContext().getAttribute("userAndLocales");
            if (localeMap instanceof ConcurrentHashMap) {
                ConcurrentHashMap<String, String> userLocales = (ConcurrentHashMap<String, String>) localeMap;

                Object user = request.getSession().getAttribute("user");
                if (user instanceof User) {
                    userLocales.put(((User) user).getLogin(), lang);
                }
                System.out.println(request.getSession().getServletContext().getAttribute("userAndLocales"));
            }

        }
        if (request.getParameter("oldCommand") != null &&
                !request.getParameter("oldCommand").equals("")) {
            String oldCommand = request.getParameter("oldCommand");
            return "redirect:" + oldCommand;
        }


        return "redirect:login";
    }
}
