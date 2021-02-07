package ch.hftm.ggq.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("relation")
public class RelationModel {

    @XStreamAsAttribute
    private String name;
    @XStreamAsAttribute
    private String type;
    @XStreamAsAttribute
    private String mapping;
    @XStreamAsAttribute
    private Boolean required;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RelationModel name(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public RelationModel type(String type) {
        this.type = type;
        return this;
    }

    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    public RelationModel mapping(String mapping) {
        this.mapping = mapping;
        return this;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public RelationModel required(Boolean required) {
        this.required = required;
        return this;
    }

    @Override
    public String toString() {
        return "RelationModel{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", mapping='" + mapping + '\'' +
                ", required=" + required +
                '}';
    }
}
