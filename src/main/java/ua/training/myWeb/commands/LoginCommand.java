package ua.training.myWeb.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class LoginCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {

        String login = request.getParameter("login");
        String password = request.getParameter("pass");

        if(login == null || password == null || login.equals("") || password.equals("")){
            return "/login.jsp";
        }

        //TODO add db connection

        HttpSession session = request.getSession();
        session.setAttribute("login", login);
        session.setAttribute("pass", password);

        System.out.println("login: "+session.getAttribute("login"));
        System.out.println("logged");
        return "redirect:/user/profile.jsp";
    }
}
