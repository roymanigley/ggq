package ch.hftm.ggq.cli;

import io.quarkus.picocli.runtime.annotations.TopCommand;
import picocli.CommandLine.Command;

@TopCommand
@Command(mixinStandardHelpOptions = true, subcommands = {CodeGeneratorCommand.class, XslTransformCommand.class}, version = "1.0")
public class MainCommand {

}
