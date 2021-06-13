package ua.training.myWeb.commands;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.training.myWeb.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Change language command.
 *
 *
 */
public class ChangeLangCommand extends Command {

    private static final Logger logger = LogManager.getLogger(ChangeLangCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command starts");

        String lang = request.getParameter("lang");
        if (lang != null) {
            request.getSession().setAttribute("lang", lang);
            logger.trace("Set lang attribute to session " + lang);
            Object localeMap = request.getSession().getServletContext().getAttribute("userAndLocales");
            if (localeMap instanceof ConcurrentHashMap) {
                ConcurrentHashMap<String, String> userLocales = (ConcurrentHashMap<String, String>) localeMap;

                Object user = request.getSession().getAttribute("user");
                if (user instanceof User) {
                    logger.trace("Saved user's locale to hashmap");
                    userLocales.put(((User) user).getLogin(), lang);
                }
            }

        }
        if (request.getParameter("oldCommand") != null &&
                !request.getParameter("oldCommand").equals("")) {
            String oldCommand = request.getParameter("oldCommand");
            logger.debug("Command ends");
            return "redirect:" + oldCommand;
        }

        logger.debug("Command ends");
        return "redirect:login";
    }
}
