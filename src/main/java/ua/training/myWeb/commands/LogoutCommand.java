package ua.training.myWeb.commands;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) {

        System.out.println("Session cleared");
        return "redirect:/index.jsp";
    }
}
