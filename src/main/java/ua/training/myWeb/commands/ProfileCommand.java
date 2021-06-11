package ua.training.myWeb.commands;

import ua.training.myWeb.Path;
import ua.training.myWeb.model.entity.User;
import ua.training.myWeb.services.PageFillerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfileCommand extends Command {


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String forward = Path.USER_PROFILE_PAGE;
        User user = (User) request.getSession().getAttribute("user");
        PageFillerService pageFillerService = new PageFillerService();
        try {
            pageFillerService.fillProfilePage(request, user);
        } catch (Exception e) {
            request.getSession().setAttribute("errorMessage", "Unexpected error");
            forward = "redirect:noCommand";
        }

        return forward;
    }


}
