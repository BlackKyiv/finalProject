package ua.training.myWeb.commands;

import ua.training.myWeb.Path;
import ua.training.myWeb.services.DatabaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        try {
            DatabaseService databaseService = new DatabaseService();
            databaseService.fillMainPage(request);
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Unexpected error");
            return "redirect:noCommand";
        }


        return Path.MAIN_PAGE;
    }
}
