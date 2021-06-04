package ua.training.myWeb.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeLangCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String lang = request.getParameter("lang");
        if(lang != null){
            request.getSession().setAttribute("lang", lang);
        }
        if(request.getParameter("oldCommand") != null &&
                !request.getParameter("oldCommand").equals("")){
            String oldCommand = request.getParameter("oldCommand");
            return "redirect:"+oldCommand;
        }


        return "redirect:login";
    }
}
