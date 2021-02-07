package ch.hftm.ggq.enumerations;

import ch.hftm.ggq.cli.wizzard.EntityWizzardHelper;
import ch.hftm.ggq.model.EntitiesModel;
import ch.hftm.ggq.model.EntityModel;

import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public enum EntityOperation {

    ADD_ENTITY(toValidate -> Boolean.TRUE, (scanner, entitiesModel) -> {
        EntityModel entityModel = new EntityModel();
        proceedEntityWizzard(scanner, entityModel);
        entitiesModel.getEntities().add(entityModel);
        final EditOperation editOperation = EntityWizzardHelper.chooseEntitySubOperation(scanner, entityModel);
        editOperation.process(scanner, entityModel);
        return entityModel;
    }),
    EDIT_ENTITY(toValidate -> Boolean.TRUE, (scanner, entitiesModel) -> {
        EntityModel entityModel = EntityWizzardHelper.chooseEntity(scanner, entitiesModel);
        final EditOperation editOperation = EntityWizzardHelper.chooseEntitySubOperation(scanner, entityModel);
        editOperation.process(scanner, entityModel);
        return entityModel;
    }),
    REMOVE_ENTITY(toValidate -> Boolean.TRUE, (scanner, entitiesModel) -> {
        EntityModel entityModel = EntityWizzardHelper.chooseEntity(scanner, entitiesModel);
        entitiesModel.getEntities().remove(entityModel);
        entitiesModel.getEntities()
                .forEach(entity -> {
                    if (entity.getRelations() != null && entity.getRelations().getRelations() != null) {
                        entity.getRelations().getRelations()
                                .removeIf(relationModel -> relationModel.getType().equals(entityModel.getName()));
                    }
                });
        return entityModel;
    }),
    SAVE_AND_QUIT(toValidate -> Boolean.TRUE, (scanner, entitiesModel) -> null),
    DONT_SAVE_AND_QUIT(toValidate -> Boolean.TRUE, (scanner, entitiesModel) -> null);


    private final Predicate validator;
    private final BiFunction<Scanner, EntitiesModel, EntityModel> processor;

    EntityOperation(Predicate validator, BiFunction<Scanner, EntitiesModel, EntityModel> processor) {
        this.validator = validator;
        this.processor = processor;
    }

    public static EntityOperation chooseEntityOperationFrom(int i) {
        switch (i) {
            case 1:
                return ADD_ENTITY;
            case 2:
                return EDIT_ENTITY;
            case 3:
                return REMOVE_ENTITY;
            case 4:
                return SAVE_AND_QUIT;
            case 5:
                return DONT_SAVE_AND_QUIT;
            default:
                throw new IllegalArgumentException();
        }
    }

    public EntityModel process(Scanner scanner, EntitiesModel entitiesModel) {
        return processor.apply(scanner, entitiesModel);
    }

    private static void proceedEntityWizzard(Scanner scanner, EntityModel toProcess) {
        String entityName = EntityWizzardHelper.chooseEntityName(scanner);
        toProcess.name(entityName);
    }
}
