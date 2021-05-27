package ua.training.myWeb.model.entity;


import ua.training.myWeb.model.entity.enums.SubscriptionStatus;

import java.util.Date;

public class Subscription extends Entity {

    private Edition edition;
    private User user;
    private Date nextPayDay;
    private SubscriptionStatus status;

    public Subscription() {
    }

    public Subscription(Edition edition, User user, Date nextPayDay, SubscriptionStatus status) {
        this.edition = edition;
        this.user = user;
        this.nextPayDay = nextPayDay;
        this.status = status;
    }

    public Edition getEdition() {
        return edition;
    }

    public void setEdition(Edition edition) {
        this.edition = edition;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getNextPayDay() {
        return nextPayDay;
    }

    public void setNextPayDay(Date nextPayDay) {
        this.nextPayDay = nextPayDay;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }

    public void setStatus(SubscriptionStatus status) {
        this.status = status;
    }
}
