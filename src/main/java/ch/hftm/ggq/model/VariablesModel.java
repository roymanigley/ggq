package ch.hftm.ggq.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;

@XStreamAlias("variables")
public class VariablesModel {

    private List<VariableModel> variables;

    public List<VariableModel> getVariables() {
        return variables;
    }

    public void setVariables(List<VariableModel> variables) {
        this.variables = variables;
    }

    @Override
    public String toString() {
        return "VariablesModel{" +
                "variables=" + variables +
                '}';
    }
}
