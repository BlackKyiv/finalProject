package ua.training.myWeb.commands;

import ua.training.myWeb.Path;
import ua.training.myWeb.services.DatabaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String forward = Path.REGISTER_PAGE;

        if (request.getParameter("login") != null &&
                request.getParameter("password") != null &&
                request.getParameter("passwordRepeat") != null) {
            forward = "redirect:register";
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            String passwordRepeat = request.getParameter("passwordRepeat");

            if (password.equals(passwordRepeat)) {
                DatabaseService databaseService = new DatabaseService();
                try {
                    if (databaseService.createNewUser(login, password)) {
                        forward = "redirect:login";
                    } else {
                        request.getSession().setAttribute("errorMessage", "This user already exists");
                        forward = "redirect:noCommand";
                    }
                } catch (Exception e) {
                    request.getSession().setAttribute("errorMessage", "Unexpected error");
                    forward = "redirect:noCommand";
                }
            }
        }


        return forward;
    }


}
