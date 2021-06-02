package ua.training.myWeb;

import org.apache.log4j.Logger;
import ua.training.myWeb.commands.Command;
import ua.training.myWeb.commands.CommandContainer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Servlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(Servlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }


    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


        String commandName = request.getParameter("command");
        System.out.println("Servlet " + commandName);
        System.out.println("Attribute " + request.getAttribute("command"));
        System.out.println("Param " + request.getParameter("command"));

        // obtain command object by its name
        Command command = CommandContainer.get(commandName);

        // execute command and get forward address
        String forward = command.execute(request, response);

        System.out.println("Forward " + forward);



        if (forward.contains("redirect:")) {
            System.out.println("Redirect: " + forward);
            response.sendRedirect(request.getContextPath() + "/controller?command=" + forward.replaceAll("redirect:", ""));
        } else {
            System.out.println("Forward: " + commandName);
            RequestDispatcher disp = request.getRequestDispatcher(forward);
            disp.forward(request, response);
        }
    }

}
