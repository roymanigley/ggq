package ch.hftm.ggq.cli;

import io.quarkus.picocli.runtime.annotations.TopCommand;
import picocli.CommandLine.Command;

@TopCommand
@Command(mixinStandardHelpOptions = true, subcommands = {CodeGeneratorCommand.class, XmlPrinterCommand.class,XslTransformCommand.class, EntitiesXmlWizardCommand.class}, version = MainCommand.version)
public class MainCommand {

    public static final String version = "1.0";

}
