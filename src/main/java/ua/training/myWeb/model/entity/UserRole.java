package ua.training.myWeb.model.entity;


import java.util.Locale;

public enum UserRole {
    ACTIVE, BLOCKED, DELETED;

    public static UserRole getAccountStatus(User user){
        return null;
    }

    public String toString(){
        return this.name().toLowerCase(Locale.ROOT);
    }
}
