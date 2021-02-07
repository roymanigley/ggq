package ch.hftm.ggq.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("variable")
public class VariableModel {

    @XStreamAsAttribute
    private String name;
    @XStreamAsAttribute
    private String type;
    @XStreamAsAttribute
    private Boolean required;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    @Override
    public String toString() {
        return "VariableModel{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", required=" + required +
                '}';
    }
}
