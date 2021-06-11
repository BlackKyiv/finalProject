package ua.training.myWeb.commands;

import ua.training.myWeb.services.DatabaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteEditionCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String forward = "redirect:editions";
        if (request.getParameter("editionId") != null) {
            long userId = Long.parseLong(request.getParameter("editionId"));
            DatabaseService databaseService = new DatabaseService();
            try {
                databaseService.deleteEdition(userId);
            } catch (Exception e) {
                request.getSession().setAttribute("errorMessage", "Cannot delete this edition");
                forward = "redirect:noCommand";
            }
        } else {
            forward = "redirect:editions";
        }

        return forward;
    }


}
