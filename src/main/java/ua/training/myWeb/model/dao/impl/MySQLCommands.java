package ua.training.myWeb.model.dao.impl;

public class MySQLCommands {


    //User sql


    public static final String UPDATE_USER
            = "UPDATE user SET login = ?, password = ?, role = ?, account = ?, user_status = ? WHERE id_user = ?";

    public static final String FIND_USER_BY_ID
            = "SELECT * FROM user WHERE id_user = ? LIMIT 1 ";

    public static final String FIND_USER_BY_LOGIN
            = "SELECT * FROM user WHERE login = ? LIMIT 1";

    public static final String INSERT_USER
            = "INSERT INTO user (login, password, role, account, role, user_status) VALUES (?, ?, ?, ?, ?, ?)";

    public static final String DELETE_USER
            = "DELETE user WHERE id_user = ?";

    public static final String FIND_ALL_USERS
            = "SELECT * FROM user";


    //Edition sql


    public static final String UPDATE_EDITION
            = "UPDATE edition SET name = ?, price = ?, theme_id = ?, edition_status = ? WHERE id_edition = ?";

    public static final String FIND_EDITION_BY_ID
            = "SELECT * FROM edition e INNER JOIN theme t ON e.theme_id = t.id_theme WHERE id_edition = ? LIMIT 1";

    public static final String INSERT_EDITION
            = "INSERT INTO edition (name, price, account, theme_id, edition_status) VALUES (?,?,?,?,?)";

    public static final String DELETE_EDITION
            = "DELETE edition WHERE id_edition = ?";

    public static final String FIND_ALL_EDITIONS
            = "SELECT * FROM edition e INNER JOIN theme t ON e.theme_id = t.id_theme";

    public static final String FIND_ALL_SPECIFIC_STATUS_EDITIONS
            = "SELECT * FROM edition e INNER JOIN theme t ON e.theme_id = t.id_theme WHERE edition_status = ?";

    public static final String FIND_ALL_SPECIFIC_THEME_EDITIONS
            = "SELECT * FROM edition e INNER JOIN theme t ON e.theme_id = t.id_theme WHERE theme = ?";


    //Subscription sql


    public static final String UPDATE_SUBSCRIPTION
            = "UPDATE subscription SET edition_id = ?, user_id = ?, next_pay_date = ?, subscription_status = ? WHERE subscription_id = ?";

    public static final String FIND_SUBSCRIPTION_BY_ID
            = "SELECT * FROM subscription s INNER JOIN edition e ON s.edition_id = e.id_edition" +
            " INNER JOIN user u ON s.user_id = u.id_user" +
            " INNER JOIN theme t ON e.theme_id = t.id_theme" +
            " WHERE id_subscription = ? LIMIT 1";

    public static final String INSERT_SUBSCRIPTION
            = "INSERT INTO subscription (edition_id, user_id, next_pay_date, subscription_status) VALUES (?, ?, ?, ?, ?, ?)";

    public static final String DELETE_SUBSCRIPTION
            = "DELETE user WHERE id_subscription = ?";

    public static final String FIND_ALL_SUBSCRIPTION
            = "SELECT * FROM subscription s INNER JOIN edition e ON s.edition_id = e.id_edition" +
            " INNER JOIN user u ON s.user_id = u.id_user" +
            " INNER JOIN theme t ON e.theme_id = t.id_theme";


    //Theme sql

    public static final String INSERT_THEME = "INSERT INTO theme (theme) VALUES (?)";

    public static final String FIND_THEME_BY_ID = "SELECT * FROM theme WHERE id_theme = ?";

    public static final String FIND_ALL_THEMES = "SELECT * FROM theme";

    public static final String UPDATE_THEME = "UPDATE theme SET theme = ? WHERE id_theme = ?";

    public static final String DELETE_THEME = "DELETE theme WHERE id_theme = ?";
}
