package ch.hftm.ggq.enumerations;

import ch.hftm.ggq.cli.CliPrinter;
import ch.hftm.ggq.cli.EntitiesXmlWizardCommand;
import ch.hftm.ggq.cli.wizzard.EntityWizzardHelper;
import ch.hftm.ggq.model.EntityModel;
import ch.hftm.ggq.model.RelationModel;
import ch.hftm.ggq.model.VariableModel;

import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public enum EditOperation {

//    ADD_ENTITY(toValidate -> Boolean.TRUE, (scanner, toProcess) -> {
//        proceedEntityWizzard(scanner, toProcess);
//    }),
//    EDIT_ENTITY(toValidate -> Boolean.TRUE, (scanner, toProcess) -> { }),
    ADD_VARIABLE((scanner, toProcess) -> {
        final VariableModel variableModel = new VariableModel();
        proceedVariableWizzard(scanner, variableModel);
        toProcess.addVariable(variableModel);
    }),
    EDIT_VARIABLE((scanner, toProcess) -> {
        final VariableModel variableModel = EntityWizzardHelper.chooseVariable(scanner, toProcess);
        proceedVariableWizzard(scanner, variableModel);
    }),
    REMOVE_VARIABLE((scanner, toProcess) -> {
        final VariableModel variableModel = EntityWizzardHelper.chooseVariable(scanner, toProcess);
        toProcess.getVariables().getVariables().remove(variableModel);
    }),
    ADD_RELATION((scanner, toProcess) -> {
        final RelationModel relationModel = new RelationModel();
        proceedRelationWizzard(scanner, relationModel);
        toProcess.addRelation(relationModel);
    }),
    EDIT_RELATION((scanner, toProcess) -> {
        final RelationModel relationModel = EntityWizzardHelper.chooseRelation(scanner, toProcess);
        proceedRelationWizzard(scanner, relationModel);
    }),
    REMOVE_RELATION((scanner, toProcess) -> {
        final RelationModel relationModel = EntityWizzardHelper.chooseRelation(scanner, toProcess);
        toProcess.getRelations().getRelations().remove(relationModel);
    });

    private final BiConsumer<Scanner, EntityModel> processor;

    public static EditOperation chooseEntitySubOperationFrom(int i) {
        switch (i) {
            case 1: return EditOperation.ADD_VARIABLE;
            case 2: return EditOperation.EDIT_VARIABLE;
            case 3: return EditOperation.REMOVE_VARIABLE;
            case 4: return EditOperation.ADD_RELATION;
            case 5: return EditOperation.EDIT_RELATION;
            case 6: return EditOperation.REMOVE_RELATION;
            default: throw new IllegalArgumentException();
        }
    }

    EditOperation(BiConsumer<Scanner, EntityModel> processor) {
        this.processor = processor;
    }

    public void process(Scanner scanner, EntityModel entityModel) {
        processor.accept(scanner, entityModel);
        CliPrinter.info(this + " done");
    }


    private static void proceedVariableWizzard(Scanner scanner, VariableModel variableModel) {
        String variableName = EntityWizzardHelper.chooseVariableName(scanner);
        String variableType = EntityWizzardHelper.chooseVariableType(scanner);
        boolean isRequired = EntityWizzardHelper.chooseIsRequired(scanner);
        variableModel
                .name(variableName)
                .type(variableType)
                .required(isRequired);
    }

    private static void proceedRelationWizzard(Scanner scanner, RelationModel relationModel) {
        String relationName = EntityWizzardHelper.chooseRelationName(scanner);
        String relationType = EntityWizzardHelper.chooseRelationType(scanner);
        String relationMapping = EntityWizzardHelper.chooseRelationMapping(scanner);
        boolean isRequired = EntityWizzardHelper.chooseIsRequired(scanner);
        relationModel
                .name(relationName)
                .type(relationType)
                .mapping(relationMapping)
                .required(isRequired);
    }
}
