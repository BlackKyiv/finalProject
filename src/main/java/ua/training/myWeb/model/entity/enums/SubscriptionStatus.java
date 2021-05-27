package ua.training.myWeb.model.entity.enums;

import java.util.Locale;

public enum SubscriptionStatus {

    ACTIVE, PAUSED, CANCELED;

    public static SubscriptionStatus getSubscriptionStatus(String text) {
        switch (text.toLowerCase()) {
            case "active":
                return ACTIVE;
            case "paused":
                return PAUSED;
            case "canceled":
                return CANCELED;
            default:
                return null;
        }
    }

    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }

}
