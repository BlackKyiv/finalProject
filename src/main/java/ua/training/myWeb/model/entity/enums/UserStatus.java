package ua.training.myWeb.model.entity.enums;


import java.util.Locale;

public enum UserStatus {
    ACTIVE, BLOCKED, DELETED;

    public static UserStatus getUserStatus(String text) {
        switch (text.toLowerCase()) {
            case "active":
                return ACTIVE;
            case "blocked":
                return BLOCKED;
            case "deleted":
                return DELETED;
            default:
                return null;
        }
    }

    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
