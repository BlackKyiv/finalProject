package ua.training.myWeb.commands;

import ua.training.myWeb.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return Path.ERROR_PAGE;
    }
}
