package ch.hftm.ggq.cli;

import ch.hftm.ggq.enumerations.TemplateType;
import ch.hftm.ggq.service.XmlEntityCodeGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import javax.inject.Inject;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Command(name = "codeGenerator", description = "Generate boilerplate code for Entities", mixinStandardHelpOptions = true, version = MainCommand.version)
public class CodeGeneratorCommand implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(CodeGeneratorCommand.class);

    @Option(names = {"--projectDir"}, description = "Quarkus project directory", required = true)
    String projectDir;
    @Option(names = {"--input"}, description = "XML File containing entities", required = true)
    String input;
    @Option(names = {"--basePackage"}, description = "Base package", defaultValue = "ch.example")
    String basePackage;
    @Option(names = {"--type"}, required = true, defaultValue = "HIBERNATE_PANACHE_REST", description = "template types (valid values: ${COMPLETION-CANDIDATES})")
    TemplateType templateType;

    XmlEntityCodeGenerator codeGenerator;

    @Inject
    public CodeGeneratorCommand(XmlEntityCodeGenerator codeGenerator) {
        this.codeGenerator = codeGenerator;
    }

    @Override
    public void run() {
        logInput();
        final Path projectDirPath = Paths.get(projectDir);
        final Path inputPath = Paths.get(input).toAbsolutePath();
        LOG.debug("validating input");
        if (validateInput(inputPath)) {
            LOG.debug("input valid");
            codeGenerator.generateFrom(inputPath, projectDirPath, basePackage, templateType);
            CliPrinter.info("generated");
        } else {
            LOG.debug("input not valid");
        }
        LOG.debug("completed");
    }

    private void logInput() {
        LOG.debug("projectDir: {}", projectDir);
        LOG.debug("input    : {}", input);
        LOG.debug("type     : {}", templateType);
    }

    boolean validateInput(Path input) {
        Boolean valid = Boolean.TRUE;
        if (Files.notExists(input)) {
            CliPrinter.error("--input : file does not exist");
            valid = Boolean.FALSE;
        }
        return valid;
    }

}
