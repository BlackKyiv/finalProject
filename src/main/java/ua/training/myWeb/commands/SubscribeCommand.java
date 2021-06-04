package ua.training.myWeb.commands;


import ua.training.myWeb.Path;
import ua.training.myWeb.services.DatabaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SubscribeCommand extends Command {


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String forward = "redirect:profile";
        if (request.getParameter("editionId") != null) {
            long editionId = Long.parseLong(request.getParameter("editionId"));
            try {
                DatabaseService databaseService = new DatabaseService();
                forward = databaseService.createSubscriptionTransaction(request, editionId, forward);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "DB error");
                forward = Path.ERROR_PAGE;
            }
        }

        return forward;
    }


}