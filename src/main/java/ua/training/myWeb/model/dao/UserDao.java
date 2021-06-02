package ua.training.myWeb.model.dao;

import ua.training.myWeb.model.dao.GenericDao;
import ua.training.myWeb.model.entity.Edition;
import ua.training.myWeb.model.entity.User;

import java.util.List;

public interface UserDao extends GenericDao<User> {

    User findByLogin(String login);

    List<User> findAllWithOffsetLimit(long offset, long limit);
}
