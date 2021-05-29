package ua.training.myWeb.model.dao.impl;

import ua.training.myWeb.model.dao.DaoFactory;
import ua.training.myWeb.model.dao.UserDao;
import ua.training.myWeb.model.entity.User;
import ua.training.myWeb.model.entity.enums.Role;
import ua.training.myWeb.model.entity.enums.UserStatus;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws SQLException {
        try (UserDao dao = JDBCDaoFactory.getInstance().createUserDao()){
            dao.create(new User("login", "1234", 12., Role.USER, UserStatus.ACTIVE));
            System.out.println(dao.findByLogin("login").getLogin());
            System.out.println(dao.findByLogin("login").getStatus());
            System.out.println(dao.findByLogin("login").getPassword());
            System.out.println(dao.findByLogin("login").getAccount());
            System.out.println(dao.findByLogin("login").getId());

        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println(DriverManager.getConnection("jdbc:mysql://localhost:3306/finaldb","root","root"));


    }
}
