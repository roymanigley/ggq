package ch.hftm.ggq.cli;

import ch.hftm.ggq.service.XslTransformerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import javax.inject.Inject;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Command(name = "xslTransformer", description = "Transform XML by using a XSL stylesheet")
public class XslTransformCommand implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(XslTransformCommand.class);

    @Option(names = {"--xml"}, description = "Path to xml file", required = true)
    String xml;
    @Option(names = {"--xsl"}, description = "Path to xsl file", required = true)
    String xsl;
    @Option(names = {"--output"}, description = "Path for the transformed output file", required = true)
    String output;

    XslTransformerService greetingService;

    @Inject
    public XslTransformCommand(XslTransformerService greetingService) {
        this.greetingService = greetingService;
    }

    @Override
    public void run() {
        logInput();
        final Path xmlPath = Paths.get(xml);
        final Path xslPath = Paths.get(xsl);
        final Path outputPath = Paths.get(output).toAbsolutePath();
        LOG.debug("validating input");
        if (validateInput(xmlPath, xslPath, outputPath)) {
            LOG.debug("input valid");
            try {
                greetingService.transform(xmlPath, xslPath, outputPath);
                CliPrinter.info("transformed");
            } catch (TransformerException | IOException e) {
                CliPrinter.error("transformation failed: " + e.getMessage());
            }
        } else {
            LOG.debug("input not valid");
        }
        LOG.debug("completed");
    }

    private void logInput() {
        LOG.debug("xml    : {}", xml);
        LOG.debug("xsl    : {}", xsl);
        LOG.debug("output : {}", output);
    }

    boolean validateInput(Path xmlPath, Path xslPath, Path outputPath) {
        Boolean valid = Boolean.TRUE;
        if (Files.notExists(xmlPath)) {
            CliPrinter.error("--xml : path does not exist");
            valid = Boolean.FALSE;
        } if (Files.notExists(xslPath )) {
            CliPrinter.error("--xsl : path does not exist");
            valid = Boolean.FALSE;
        } if (outputPath.getParent() == null || Files.notExists(outputPath.getParent())) {
            CliPrinter.error("--output : parent path does not exist");
            valid = Boolean.FALSE;
        }
        return valid;
    }

}
