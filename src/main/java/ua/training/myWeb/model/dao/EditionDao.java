package ua.training.myWeb.model.dao;

import ua.training.myWeb.model.entity.Edition;

import java.util.List;

public interface EditionDao extends GenericDao<Edition> {

    List<Edition> findAllWithOffsetLimit(long offset, long limit);

    List<Edition> findAllWithOffsetLimitActive(long offset, long limit);

    List<Edition> findWithOffsetLimitActiveQueryGeneration(long offset, long limit, long themeId, String sort, String query);

    Long countActive();
    Long countActiveQueryGeneration(long themeId, String sort, String query);
}
