package ua.training.myWeb.commands;

import ua.training.myWeb.Path;
import ua.training.myWeb.services.PageFillerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UsersCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        long page = 1;
        long recordsPerPage = 6;
        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));
        PageFillerService pageFillerService = new PageFillerService();
        try {
            pageFillerService.fillUsersPage(request, page, recordsPerPage);
        } catch (Exception e) {
            request.getSession().setAttribute("errorMessage", "Unexpected error");
            return "redirect:noCommand";
        }

        return Path.USERS_PAGE;
    }

}
