package ch.hftm.ggq.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("relations")
public class RelationsModel {

    private List<RelationModel> relations = new ArrayList<>();

    public List<RelationModel> getRelations() {
        return relations;
    }

    public void setRelations(List<RelationModel> relations) {
        this.relations = relations;
    }

    @Override
    public String toString() {
        return "RelationsModel{" +
                "variables=" + relations +
                '}';
    }
}
