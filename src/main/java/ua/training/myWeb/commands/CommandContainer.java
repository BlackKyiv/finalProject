package ua.training.myWeb.commands;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

/**
 * Holder for all commands.<br/>
 *
 *
 */
public class CommandContainer {
    private static final Logger log = Logger.getLogger(CommandContainer.class);

    private static Map<String, Command> commands = new TreeMap<String, Command>();

    static {
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("noCommand", new NoCommand());
        commands.put("profile", new ProfileCommand());
        commands.put("main", new MainCommand());
        commands.put("register", new RegisterCommand());
        commands.put("replenish", new ReplenishCommand());
        commands.put("subscribe", new SubscribeCommand());
        commands.put("cancelSubscription", new CancelSubscriptionCommand());
        commands.put("editions", new EditionsCommand());
        commands.put("createEdition", new CreateEditionCommand());
        commands.put("updateEdition", new UpdateEditionCommand());
        commands.put("deleteEdition", new DeleteEditionCommand());
        commands.put("users", new UsersCommand());
        commands.put("updateUser", new UpdateUserCommand());
        commands.put("deleteUser", new DeleteUserCommand());
        commands.put("themes", new ThemesCommand());
        commands.put("createTheme", new CreateThemeCommand());
        commands.put("updateTheme", new UpdateThemeCommand());
        commands.put("changeLang", new ChangeLangCommand());
        commands.put("deleteTheme", new DeleteThemeCommand());

    }

    /**
     * Returns command object with the given name.
     *
     * @param commandName
     *            Name of the command.
     * @return Command object.
     */
    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            log.trace("Command not found, name --> " + commandName);
            return commands.get("noCommand");
        }

        return commands.get(commandName);
    }
}
