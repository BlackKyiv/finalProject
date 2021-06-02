package ua.training.myWeb.model.dao;

import ua.training.myWeb.model.entity.Edition;

import java.util.List;

public interface EditionDao extends GenericDao<Edition> {

    List<Edition> findAllWithOffsetLimit(long offset, long limit);
}
