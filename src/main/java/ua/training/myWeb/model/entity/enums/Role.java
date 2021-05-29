package ua.training.myWeb.model.entity.enums;

import java.util.Locale;

public enum Role {
    USER(1), ADMIN(2);

    private final int value;
    Role(int value) {
        this.value = value;
    }


    public int getValue() {
        return value;
    }

    public static Role getRole(String text) {
        switch (text.toLowerCase()) {
            case "user":
                return USER;
            case "admin":
                return ADMIN;
            default:
                return null;
        }
    }

    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }

}
