package ua.training.myWeb;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.training.myWeb.commands.Command;
import ua.training.myWeb.commands.CommandContainer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Main servlet controller.
 *
 *
 */
public class Servlet extends HttpServlet {
    final Logger logger = LogManager.getLogger(Servlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }


    /**
     * Main method of this controller.
     */
    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String commandName = request.getParameter("command");
        Command command = CommandContainer.get(commandName);
        logger.trace("Executing command  ----->" + command);
        String forward = command.execute(request, response);

        if (forward.contains("redirect:")) {
            logger.trace("Redirecting to " + request.getContextPath() + "/controller?command=" + forward.replaceAll("redirect:", ""));
            response.sendRedirect(
                    request.getContextPath() + "/controller?command=" + forward.replaceAll("redirect:", ""));
        } else {
            logger.trace("Forward to " + forward);
            RequestDispatcher disp = request.getRequestDispatcher(forward);
            disp.forward(request, response);
        }

        logger.trace("Command executed ----->" + command);
    }

}
