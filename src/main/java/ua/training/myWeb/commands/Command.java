package ua.training.myWeb.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Main interface for the Command pattern implementation.
 *
 *
 */
public abstract class Command {

    /**
     * Execution method for command.
     * @return Address to go once the command is executed.
     */
    public abstract String execute(HttpServletRequest request, HttpServletResponse response);

    @Override
    public String toString() {
        return this.getClass().getName();
    }
}
