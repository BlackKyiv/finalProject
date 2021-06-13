package ua.training.myWeb.model.dao;

import ua.training.myWeb.model.entity.User;

import java.util.List;

/**
 * Data access object for user related entities.
 */
public interface UserDao extends GenericDao<User> {

    /**
     * Return user with specific login
     *
     * @param login user's login to find
     * @return entity of user with specific login
     */
    User findByLogin(String login);

    /**
     * Return list of all users
     *
     * @param offset entities to skip
     * @param limit  limit number of entities
     * @return list of entities of users
     */
    List<User> findAllWithOffsetLimit(long offset, long limit);
}
