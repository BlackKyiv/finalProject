package ua.training.myWeb.model.entity;


import ua.training.myWeb.model.entity.enums.Role;
import ua.training.myWeb.model.entity.enums.UserStatus;


/**
 * User entity.
 *
 *
 */
public class User extends Entity {

    private String login;
    private String password;

    private Double account;

    private Role role;
    private UserStatus status;

    public User() {
    }

    public User(String login, String password, Double account, Role role, UserStatus statusId) {
        this.login = login;
        this.password = password;
        this.account = account;
        this.role = role;
        this.status = statusId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getAccount() {
        return account;
    }

    public void setAccount(Double account) {
        this.account = account;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus statusId) {
        this.status = statusId;
    }

    public String toString(){
        return getId() + " " + getLogin() + " " +  getAccount() + " " +  getRole() + " " +  getStatus();
    }
}

