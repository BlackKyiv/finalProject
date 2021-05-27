package ua.training.myWeb.model.dao;

import ua.training.myWeb.model.dao.GenericDao;
import ua.training.myWeb.model.entity.User;

public interface UserDao extends GenericDao<User> {

    User findByLogin(String login);
}
