package ua.training.myWeb.model.entity;

import ua.training.myWeb.model.entity.enums.EditionStatus;

/**
 * Edition entity.
 *
 *
 */
public class Edition extends Entity {
    private String name;
    private Double price;
    private Theme theme;
    private EditionStatus status;

    public Edition() {
    }

    public Edition(String name, Double price, Theme theme, EditionStatus status) {
        this.name = name;
        this.price = price;
        this.theme = theme;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public EditionStatus getStatus() {
        return status;
    }

    public void setStatus(EditionStatus status) {
        this.status = status;
    }
}
