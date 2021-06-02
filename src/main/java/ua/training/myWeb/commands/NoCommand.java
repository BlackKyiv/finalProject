package ua.training.myWeb.commands;

import org.apache.log4j.Logger;
import ua.training.myWeb.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NoCommand extends Command{
    private static final Logger log = Logger.getLogger(NoCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) {
        log.debug("Command starts");

        log.debug("Command finished");

        return Path.ERROR_PAGE;
    }
}
