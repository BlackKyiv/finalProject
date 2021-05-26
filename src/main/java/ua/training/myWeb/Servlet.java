package ua.training.myWeb;

import ua.training.myWeb.commands.Command;
import ua.training.myWeb.commands.LoginCommand;
import ua.training.myWeb.commands.LogoutCommand;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class Servlet extends HttpServlet {
    HashMap<String, Command> commands = new HashMap<>();


    @Override
    public void init(ServletConfig servletConfig){
        servletConfig.getServletContext()
                .setAttribute("loggedUsers", new HashSet<String>());

        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }


    private void process(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String path = request.getRequestURI();
        path = path.replaceAll(".*/myWeb_war_exploded/" , "");
        System.out.println(path);
        Command command = commands.getOrDefault(
                path,
                (r)->"/index.jsp");

        System.out.println(command.getClass().getName());
        String page = command.execute(request);
        if(page.contains("redirect:")){
            response.sendRedirect(page.replace("redirect:", "/myWeb_war_exploded"));
        }else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}
