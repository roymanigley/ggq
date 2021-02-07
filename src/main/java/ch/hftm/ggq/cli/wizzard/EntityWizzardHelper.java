package ch.hftm.ggq.cli.wizzard;

import ch.hftm.ggq.cli.CliPrinter;
import ch.hftm.ggq.enumerations.EditOperation;
import ch.hftm.ggq.enumerations.EntityOperation;
import ch.hftm.ggq.model.EntitiesModel;
import ch.hftm.ggq.model.EntityModel;
import ch.hftm.ggq.model.RelationModel;
import ch.hftm.ggq.model.VariableModel;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class EntityWizzardHelper {

    public static EntityModel chooseEntity(Scanner scanner, EntitiesModel entitiesModel) {
        int selectedType;

        final AtomicInteger counter = new AtomicInteger(0);
        final String question = entitiesModel.getEntities().stream()
                .map(EntityModel::getName)
                .reduce("select an entity\n------------------------------------", (s, s2) ->
                        s + "\n[" + (counter.incrementAndGet()) + "] " + s2
                );
        CliPrinter.question(question);
        do {
            try {
                System.out.print("=> ");
                selectedType = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                selectedType = -1;
            }
        } while (selectedType > entitiesModel.getEntities().size() || selectedType < 1);
        return entitiesModel.getEntities().get(selectedType - 1);
    }

    public static VariableModel chooseVariable(Scanner scanner, EntityModel entityModel) {
        int selectedVariable;

        final AtomicInteger counter = new AtomicInteger(0);
        final String question = entityModel.getVariables().getVariables().stream()
                .map(VariableModel::getName)
                .reduce("select a variable", (s, s2) ->
                        s + "\n[" + (counter.incrementAndGet()) + "] " + s2
                );
        CliPrinter.question(question);
        do {
            try {
                System.out.print("=> ");
                selectedVariable = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                selectedVariable = -1;
            }
        } while (selectedVariable > entityModel.getVariables().getVariables().size() || selectedVariable < 1);
        return entityModel.getVariables().getVariables().get(selectedVariable - 1);
    }

    public static RelationModel chooseRelation(Scanner scanner, EntityModel entityModel) {
        int selectedRelation;

        final AtomicInteger counter = new AtomicInteger(0);
        final String question = entityModel.getRelations().getRelations().stream()
                .map(RelationModel::getName)
                .reduce("select a relation", (s, s2) ->
                        s + "\n[" + (counter.incrementAndGet()) + "] " + s2
                );
        CliPrinter.question(question);
        do {
            try {
                System.out.print("=> ");
                selectedRelation = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                selectedRelation = -1;
            }
        } while (selectedRelation > entityModel.getRelations().getRelations().size() || selectedRelation < 1);
        return entityModel.getRelations().getRelations().get(selectedRelation - 1);
    }


    public static EntityOperation chooseEntityOperation(Scanner scanner) {
        CliPrinter.question("What do you want to do ?\n" +
                "------------------------------------\n" +
                "[1] add an entity\n" +
                "[2] edit an entity\n" +
                "[3] delete an entity\n" +
                "------------------------------------\n" +
                "[4] save and quit\n" +
                "[5] don't save and quit");
        int selectedOperation;
        do {
            try {
                System.out.print("=> ");
                selectedOperation = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                selectedOperation = -1;
            }
        } while (isEntityOperationNotValid(selectedOperation));
        return EntityOperation.chooseEntityOperationFrom(selectedOperation);
    }


    public static String chooseRelationMapping(Scanner scanner) {
        CliPrinter.question("What mapping do you want for this relation ?\n" +
                "------------------------------------\n" +
                "[1] ManyToOne\n" +
                "[2] OneToOne");
        int selectedOperation;
        do {
            try {
                System.out.print("=> ");
                selectedOperation = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                selectedOperation = -1;
            }
        } while (selectedOperation < 1 || selectedOperation > 2);
        return selectedOperation == 1 ? "ManyToOne" : "OneToOne";
    }

    public static String chooseEntityName(Scanner scanner) {
        CliPrinter.question("enter the name of the entity you want to create");
        String entityName;
        do {
            System.out.print("=> ");
            scanner.reset();
            entityName = scanner.nextLine();
        } while (isEntityNameInvalid(entityName));
        return entityName;
    }

    public static String chooseVariableType(Scanner scanner) {
        int selectedType;
        final AtomicInteger counter = new AtomicInteger(0);
        final String question = allowedDataTypes.stream()
                .reduce("select a variable type\n------------------------------------", (s, s2) ->
                        s + "\n[" + (counter.incrementAndGet()) + "] " + s2
                );
        CliPrinter.question(question);
        do {
            try {
                System.out.print("=> ");
                selectedType = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                selectedType = -1;
            }
        } while (selectedType > allowedDataTypes.size() || selectedType < 1);
        return allowedDataTypes.get(selectedType - 1);
    }

    public static String chooseRelationType(Scanner scanner) {
        CliPrinter.question("enter the type of the relation");
        String relationName;
        do {
            System.out.print("=> ");
            scanner.reset();
            relationName = scanner.nextLine();
        } while (isEntityNameInvalid(relationName));
        return relationName;
    }

    public static String chooseVariableName(Scanner scanner) {
        CliPrinter.question("enter the name of the variable");
        String variableName;
        do {
            System.out.print("=> ");
            scanner.reset();
            variableName = scanner.nextLine();
        } while (isVariableNameInvalid(variableName));
        return variableName;
    }

    public static String chooseRelationName(Scanner scanner) {
        CliPrinter.question("enter the name of the relation");
        String variableName;
        do {
            System.out.print("=> ");
            scanner.reset();
            variableName = scanner.nextLine();
        } while (isVariableNameInvalid(variableName));
        return variableName;
    }


    public static boolean chooseIsRequired(Scanner scanner) {
        CliPrinter.question("should this field be required [y/N]");
        System.out.print("=> ");
        final String line = scanner.nextLine();
        return line.toLowerCase().equals("y");
    }

    public static boolean confirmYesNo(Scanner scanner, String text) {
        CliPrinter.question(text + " [y/N]");
        System.out.print("=> ");
        final String line = scanner.nextLine();
        return line.toLowerCase().equals("y");
    }

    public static EditOperation chooseEntitySubOperation(Scanner scanner, EntityModel entityModel) {
        int selectedOperation;
        CliPrinter.question("What do you want to do ?\n" +
                "------------------------------------\n" +
                "[1] add a variable\n" +
                (!entityModel.hasVariables() ? "" : "[2] edit a variable\n") +
                (!entityModel.hasVariables() ? "" : "[3] remove a variable\n") +
                "------------------------------------\n" +
                "[4] add a relation\n" +
                (!entityModel.hasRelations() ? "" : "[5] edit a relation\n") +
                (!entityModel.hasRelations() ? "" : "[6] remove a relation"));
        do {
            try {
                System.out.print("=> ");
                selectedOperation = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                selectedOperation = -1;
            }
        } while (isEntitySubOperationNotValid(selectedOperation, entityModel));
        return EditOperation.chooseEntitySubOperationFrom(selectedOperation);
    }

    public static boolean isEntityOperationNotValid(Integer selectedOperation) {
        final boolean isEntityOperationNotValid = selectedOperation < 1 || selectedOperation > 5;
        if (isEntityOperationNotValid) {
            CliPrinter.error("invalid operation");
        }
        return isEntityOperationNotValid;
    }

    public static boolean isEntitySubOperationNotValid(Integer selectedOperation, EntityModel entityModel) {
        boolean isEntitySubOperationNotValid = selectedOperation < 1 || selectedOperation > 6;
        isEntitySubOperationNotValid = isEntitySubOperationNotValid
                || (!entityModel.hasVariables() && (selectedOperation == 2 || selectedOperation == 3))
                || (!entityModel.hasRelations() && (selectedOperation == 5 || selectedOperation == 6));

        if (isEntitySubOperationNotValid) {
            CliPrinter.error("invalid operation");
        }
        return isEntitySubOperationNotValid;
    }

    public static final List<String> entityNameBlackList = Arrays.asList(
            "order",
            "position",
            "private",
            "public",
            "protected",
            "like",
            "select",
            "group",
            "type",
            "string",
            "double",
            "float",
            "integer",
            "int",
            "short",
            "byte"
    );

    public static final List<String> allowedDataTypes = Arrays.asList(
            "String",
            "Integer",
            "Double",
            "LocalDate",
            "LocalDateTime"
    );

    public static boolean isEntityNameInvalid(String entityName) {
        boolean isInvalid = entityNameBlackList.contains(entityName.toLowerCase());
        if (isInvalid) {
            CliPrinter.error("the selected entity name is not allowed (reserved name): " + entityName);
            return isInvalid;
        }

        isInvalid = entityName.matches("^.*\\s.*$");
        if (isInvalid) {
            CliPrinter.error("blank spaces not allowed");
            return isInvalid;
        }

        isInvalid = !entityName.matches("^[A-Z_][a-zA-Z_0-9]*$");
        if (isInvalid) {
            CliPrinter.error("Illegal characters for an entity name\n" +
                    "1. the first letter has to be uppercase or a _\n" +
                    "2. from the second character on you can choose a-z A-Z _ 0-9");
            return isInvalid;
        }


        return isInvalid;
    }

    public static boolean isVariableNameInvalid(String entityName) {
        boolean isInvalid = entityNameBlackList.contains(entityName.toLowerCase());
        if (isInvalid) {
            CliPrinter.error("the selected variable name is not allowed (reserved name): " + entityName);
            return isInvalid;
        }

        isInvalid = entityName.matches("^.*\\s.*$");
        if (isInvalid) {
            CliPrinter.error("blank spaces not allowed");
            return isInvalid;
        }

        isInvalid = !entityName.matches("^[a-z_][a-zA-Z_0-9]*$");
        if (isInvalid) {
            CliPrinter.error("Illegal characters for an entity name\n" +
                    "1. the first letter has to be lowercase or a _\n" +
                    "2. from the second character on you can choose a-z A-Z _ 0-9");
            return isInvalid;
        }

        return isInvalid;
    }
}
