package ua.training.myWeb.model.entity.enums;

import java.util.Locale;

public enum EditionStatus {
    ACTIVE, HIDDEN, DELETED;

    public static EditionStatus getEditionStatus(String text) {
        switch (text.toLowerCase()) {
            case "active":
                return ACTIVE;
            case "hidden":
                return HIDDEN;
            case "delete":
                return DELETED;
            default:
                return null;
        }
    }

    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
