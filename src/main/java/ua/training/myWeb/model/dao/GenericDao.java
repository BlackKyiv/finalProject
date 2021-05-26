package ua.training.myWeb.model.dao;

import java.util.List;

public interface GenericDao<T> extends AutoCloseable {

    void create(T entity);


    T findById(long id);

    List<T> findAll();

    void update(T entity);

    void delete(long id);


}
