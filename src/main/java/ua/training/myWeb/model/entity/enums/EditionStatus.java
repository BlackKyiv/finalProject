package ua.training.myWeb.model.entity.enums;

import java.util.Locale;

/**
 * Edition status parameter entity.
 *
 *
 */
public enum EditionStatus {
    ACTIVE(1), HIDDEN(2), DELETED(3);


    private final int value;
    EditionStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

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
