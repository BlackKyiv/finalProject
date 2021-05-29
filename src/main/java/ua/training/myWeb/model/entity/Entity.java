package ua.training.myWeb.model.entity;

import java.io.Serializable;


public abstract class Entity implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean equals(Object o) {
        if (o != null && this.getClass() == o.getClass()) {
            Entity e = (Entity) o;
            if(id.equals(e.getId())) return true;
        }
        return false;
    }


}
