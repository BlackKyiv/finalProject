package ua.training.myWeb.model.entity;

/**
 * Theme entity.
 *
 *
 */
public class Theme extends Entity {

    private String name;

    public Theme(){}

    public Theme(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
