package ua.training.myWeb.model.dao;

import ua.training.myWeb.model.entity.Subscription;

import java.util.List;

/**
 * Data access object for subscription related entities.
 */
public interface SubscriptionDao extends GenericDao<Subscription> {

    /**
     * Return subscription with specific id of user and id of edition
     *
     * @param userId    id of user
     * @param editionId id of edition
     * @return entity of subscription
     */
    Subscription findByUserIdAndEditionId(Long userId, Long editionId);

    /**
     * Return list of subscriptions with specific id of user with limit and offset
     *
     * @param userId id of user
     * @param offset entities to skip
     * @param limit  limit number of entities
     * @return entity of subscription
     */
    List<Subscription> findByUserIdOffsetAndLimit(Long userId, Long offset, Long limit);

    /**
     * Return list of subscriptions with specific id of user with limit and offset
     *
     * @param userId id of user
     * @return number of subscriptions of specific user
     */
    Long countUser(long userId);

}
