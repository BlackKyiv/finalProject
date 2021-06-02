package ua.training.myWeb.commands;

import ua.training.myWeb.Path;
import ua.training.myWeb.model.dao.UserDao;
import ua.training.myWeb.model.dao.impl.JDBCDaoFactory;
import ua.training.myWeb.model.entity.User;
import ua.training.myWeb.model.entity.enums.Role;
import ua.training.myWeb.model.entity.enums.UserStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String forward = Path.REGISTER_PAGE;

        if (request.getParameter("login") != null &&
                request.getParameter("password") != null &&
                request.getParameter("passwordRepeat") != null) {
            forward = "redirect:register";
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            String passwordRepeat = request.getParameter("passwordRepeat");

            if(password.equals(passwordRepeat)){
                try (UserDao userDao = JDBCDaoFactory.getInstance().createUserDao()){
                    if(userDao.findByLogin(login) == null){
                        User user = new User();
                        user.setLogin(login);
                        user.setPassword(password);
                        user.setAccount(0.);
                        user.setRole(Role.USER);
                        user.setStatus(UserStatus.ACTIVE);
                        userDao.create(user);
                        forward = "redirect:login";
                    } else {
                        //TODO ERROR
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        return forward;
    }
}
