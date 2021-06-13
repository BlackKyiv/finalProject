package ua.training.myWeb.model.entity.enums;


import java.util.Locale;

/**
 * User status parameter entity.
 *
 *
 */
public enum UserStatus {
    ACTIVE(1), BLOCKED(2), DELETED(3), UNKNOWN(4);

    private final int value;
    UserStatus(int value) {
        this.value = value;
    }



    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public int getValue() {
        return value;
    }

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
}
