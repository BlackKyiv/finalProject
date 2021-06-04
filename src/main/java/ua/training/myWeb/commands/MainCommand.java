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
            return Path.ERROR_PAGE;
        }


        return Path.MAIN_PAGE;
    }
}
