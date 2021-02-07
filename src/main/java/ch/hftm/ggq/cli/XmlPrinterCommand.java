package ch.hftm.ggq.cli;

import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

@Command(name = "printXml", description = "Print an example XML for the Code Generator", mixinStandardHelpOptions = true, version = MainCommand.version)
public class XmlPrinterCommand implements Runnable {

    @CommandLine.Option(names = {"--extended"}, description = "Print a more complex example")
    boolean printExtended;

    @Override
    public void run() {
        final String resource = getResource();
        printFromResource(resource);
    }

    private String getResource() {
        return Optional.of(printExtended)
                .filter(Boolean::booleanValue)
                .map(b -> "xml/example-extended.xml")
                .orElse("xml/example.xml");
    }

    private void printFromResource(String resource) {
        try (final InputStream xmlStream = XmlPrinterCommand.class.getClassLoader().getResourceAsStream(resource)) {
            final InputStreamReader xmlReader = new InputStreamReader(xmlStream);
            final BufferedReader bufferedXmlReader = new BufferedReader(xmlReader);
            String line = null;
            while ((line = bufferedXmlReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
