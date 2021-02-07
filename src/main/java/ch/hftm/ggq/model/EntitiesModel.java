package ch.hftm.ggq.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;

@XStreamAlias("entities")
public class EntitiesModel {

    private List<EntityModel> entities;

    public List<EntityModel> getEntities() {
        return entities;
    }

    public void setEntities(List<EntityModel> entities) {
        this.entities = entities;
    }

    @Override
    public String toString() {
        return "EntitiesModel{" +
                "entities=" + entities +
                '}';
    }
}
