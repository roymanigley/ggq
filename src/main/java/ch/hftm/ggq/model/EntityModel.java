package ch.hftm.ggq.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.util.ArrayList;

@XStreamAlias("entity")
public class EntityModel {

    @XStreamAsAttribute
    private String name;

    private VariablesModel variables = new VariablesModel();

    private RelationsModel relations = new RelationsModel();


    public void addRelation(RelationModel relationModel) {
        if (relations == null) {
            relations = new RelationsModel();
        } if (relations.getRelations() == null) {
            relations.setRelations(new ArrayList<>());
        }
        relations.getRelations().add(relationModel);
    }

    public void addVariable(VariableModel relationModel) {
        if (variables == null) {
            variables = new VariablesModel();
        } if (variables.getVariables() == null) {
            variables.setVariables(new ArrayList<>());
        }
        variables.getVariables().add(relationModel);
    }

    public boolean hasRelations() {
        return relations != null && relations.getRelations() != null && !relations.getRelations().isEmpty();
    }

    public boolean hasVariables() {
        return variables != null && variables.getVariables() != null && !variables.getVariables().isEmpty();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EntityModel name(String name) {
        this.name = name;
        return this;
    }

    public VariablesModel getVariables() {
        return variables;
    }

    public void setVariables(VariablesModel variables) {
        this.variables = variables;
    }

    public RelationsModel getRelations() {
        return relations;
    }

    public void setRelations(RelationsModel relations) {
        this.relations = relations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityModel that = (EntityModel) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (variables != null ? !variables.equals(that.variables) : that.variables != null) return false;
        return relations != null ? relations.equals(that.relations) : that.relations == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (variables != null ? variables.hashCode() : 0);
        result = 31 * result + (relations != null ? relations.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EntityModel{" +
                "name='" + name + '\'' +
                ", variables=" + variables +
                ", relations=" + relations +
                '}';
    }
}
