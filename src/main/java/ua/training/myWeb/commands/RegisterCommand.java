package ua.training.myWeb.commands;

import javax.servlet.http.HttpServletRequest;

public class RegisterCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) {

        //TODO DB manipulation

        return "redirect:/login.jsp";
    }
}
