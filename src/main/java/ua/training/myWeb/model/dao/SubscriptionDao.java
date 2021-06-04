package ua.training.myWeb.model.dao;

import ua.training.myWeb.model.entity.Subscription;

import java.util.List;

public interface SubscriptionDao extends GenericDao<Subscription> {
    Subscription findByUserIdAndEditionId(Long userId, Long editionId);
    List<Subscription> findByUserIdOffsetAndLimit(Long userId, Long offset, Long limit);
    Long countUser(long userId);

}
