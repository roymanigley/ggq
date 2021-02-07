package ch.hftm.ggq.cli;

import ch.hftm.ggq.cli.wizzard.EntityWizzardHelper;
import ch.hftm.ggq.enumerations.EntityOperation;
import ch.hftm.ggq.model.EntitiesModel;
import ch.hftm.ggq.service.XmlEntitiesModelLoader;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

@Command(name = "xmlWizzard", description = "CLI Wizzard for creating and updating a XML for the Code Generator → better edit the XML  manualy ;)", mixinStandardHelpOptions = true, version = MainCommand.version)
public class EntitiesXmlWizardCommand implements Runnable {

    private final XmlEntitiesModelLoader modelLoader;

    @CommandLine.Option(names = {"--output"}, description = "The output XML file", defaultValue = "entities.xml")
    String ouputXmlFile = "entities.xml";

    @Inject
    public EntitiesXmlWizardCommand(XmlEntitiesModelLoader modelLoader) {
        this.modelLoader = modelLoader;
    }

    @Override
    public void run() {

        EntitiesModel entitiesModel = new EntitiesModel();;
        EntityOperation chosenEntityOperation;
        final Path xmlPath = Paths.get(ouputXmlFile);
        try (final Scanner scanner = new Scanner(System.in);) {

            if (Files.exists(xmlPath) && !Files.isDirectory(xmlPath)) {
                CliPrinter.info("loading model from existing file");
                entitiesModel = modelLoader.loadEntitiesModel(xmlPath);
            } else {
                CliPrinter.info("initialising a new empty model");
                chosenEntityOperation = EntityOperation.ADD_ENTITY;
                chosenEntityOperation.process(scanner,entitiesModel);
            }

            do {
                chosenEntityOperation = EntityWizzardHelper.chooseEntityOperation(scanner);
                chosenEntityOperation.process(scanner, entitiesModel);
            } while (!EntityOperation.SAVE_AND_QUIT.equals(chosenEntityOperation) && !EntityOperation.DONT_SAVE_AND_QUIT.equals(chosenEntityOperation));

            if (EntityOperation.SAVE_AND_QUIT.equals(chosenEntityOperation)) {
                saveToFile(entitiesModel, xmlPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
            CliPrinter.error(e.getMessage());
        }
    }

    private void saveToFile(EntitiesModel entitiesModel, Path xmlPath) throws IOException {
        final String xml = modelLoader.entitiesModelToXml(entitiesModel);
        System.out.println(xml);
        Files.write(xmlPath, xml.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
    }
}
