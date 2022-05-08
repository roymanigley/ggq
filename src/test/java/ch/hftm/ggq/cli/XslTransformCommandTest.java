package ch.hftm.ggq.cli;

import ch.hftm.ggq.service.XslTransformerService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.MockitoAnnotations.openMocks;

public class XslTransformCommandTest {

    private static final Logger LOG = LoggerFactory.getLogger(XslTransformCommandTest.class);
    private XslTransformCommand xmlToHtmlDocumentationCommand;

    static Path VALID_XML_PATH;
    static Path VALID_XSL_PATH;
    static Path VALID_OUTPUT_PATH;

    @Mock
    private XslTransformerService xslTransformerService;


    @BeforeAll
    static void beforeAll() {
        try {
            VALID_XML_PATH = Files.createTempFile("test", ".xml");
            VALID_XSL_PATH = Files.createTempFile("test", ".xsl");
            VALID_OUTPUT_PATH = Files.createTempDirectory("test").resolve("test.out");
        } catch (IOException e) {
            LOG.error("Could not prepare for XmlToHtmlDocumentationCommandTest", e);
        }
    }

    @AfterAll
    static void afterAll() {
        try {
            Files.delete(VALID_XML_PATH);
            Files.delete(VALID_XSL_PATH);
            Files.delete(VALID_OUTPUT_PATH);
        } catch (IOException e) {
            LOG.error("Could not cleanup for XmlToHtmlDocumentationCommandTest", e);
        }
    }

    @BeforeEach
    void beforeEach() {
        openMocks(this);
        xmlToHtmlDocumentationCommand = new XslTransformCommand(xslTransformerService);
    }

    @Test
    public void testValidation_notExistingXml_shouldFail() {
        final Path xmlPath = Paths.get("fsdafdsafdsa.xml");
        final boolean isValid = xmlToHtmlDocumentationCommand.validateInput(xmlPath, VALID_XSL_PATH, VALID_OUTPUT_PATH);

        assertThat(isValid).isFalse();
        verifyZeroInteractions(xslTransformerService);
    }

    @Test
    public void testValidation_notExistingXsl_shouldFail() {
        final Path xslPath = Paths.get("fsdafdsafdsa.xml");
        final boolean isValid = xmlToHtmlDocumentationCommand.validateInput(VALID_XML_PATH, xslPath, VALID_OUTPUT_PATH);

        assertThat(isValid).isFalse();
        verifyZeroInteractions(xslTransformerService);
    }

    @Test
    public void testValidation_outputNoParent_shouldFail() {
        final Path outputPath = Paths.get("/123/123/123/123fsdafdsafdsa.xml");
        final boolean isValid = xmlToHtmlDocumentationCommand.validateInput(VALID_XML_PATH, VALID_XSL_PATH, outputPath);

        assertThat(isValid).isFalse();
        verifyZeroInteractions(xslTransformerService);
    }

    @Test
    public void testValidation_existingXmlExistingXslOutputWithParent_shouldNotFail() {
        final boolean isValid = xmlToHtmlDocumentationCommand.validateInput(VALID_XML_PATH, VALID_XSL_PATH, VALID_OUTPUT_PATH);

        assertThat(isValid).isTrue();
        verifyZeroInteractions(xslTransformerService);
    }


    @Test
    public void testRun_notExistingXml_shouldNotTriggerTransformation() {
        xmlToHtmlDocumentationCommand.xml = "fsdafdsafdsa.xml";
        xmlToHtmlDocumentationCommand.xsl = VALID_XSL_PATH.toAbsolutePath().toString();
        xmlToHtmlDocumentationCommand.output = VALID_OUTPUT_PATH.toAbsolutePath().toString();

        xmlToHtmlDocumentationCommand.run();

        verifyZeroInteractions(xslTransformerService);
    }


    @Test
    public void testRun_existingXmlExistingXslOutputWithParent_shouldTriggerTransformation() throws TransformerException, IOException {
        xmlToHtmlDocumentationCommand.xml = VALID_XML_PATH.toAbsolutePath().toString();
        xmlToHtmlDocumentationCommand.xsl = VALID_XSL_PATH.toAbsolutePath().toString();
        xmlToHtmlDocumentationCommand.output = VALID_OUTPUT_PATH.toAbsolutePath().toString();

        xmlToHtmlDocumentationCommand.run();

        verify(xslTransformerService).transform(any(Path.class), any(Path.class), any(Path.class));
    }


}
