package ch.hftm.ggq.cli;

import ch.hftm.ggq.enumerations.TemplateType;
import ch.hftm.ggq.service.XmlEntityCodeGenerator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.MockitoAnnotations.initMocks;

public class XmlCodeGenCommandTest {

    private static final Logger LOG = LoggerFactory.getLogger(XmlCodeGenCommandTest.class);
    private CodeGeneratorCommand codeGeneratorCommand;

    static Path VALID_XML_PATH;

    @Mock
    private XmlEntityCodeGenerator codeGenerator;

    @BeforeAll
    static void beforeAll() {
        try {
            VALID_XML_PATH = Files.createTempFile("test", ".xml");
        } catch (IOException e) {
            LOG.error("Could not prepare for XmlCodeGenCommandTest", e);
        }
    }

    @AfterAll
    static void afterAll() {
        try {
            Files.delete(VALID_XML_PATH);
        } catch (IOException e) {
            LOG.error("Could not cleanup for XmlCodeGenCommandTest", e);
        }
    }

    @BeforeEach
    void beforeEach() {
        initMocks(this);
        codeGeneratorCommand = new CodeGeneratorCommand(codeGenerator);
    }

    @Test
    public void testValidation_notExistingXml_shouldFail() {
        final Path xmlPath = Paths.get("fsdafdsafdsa.xml");
        final boolean isValid = codeGeneratorCommand.validateInput(xmlPath);

        assertThat(isValid).isFalse();
        verifyZeroInteractions(codeGenerator);
    }

    @Test
    public void testValidation_existingXmlExisting_shouldNotFail() {
        final boolean isValid = codeGeneratorCommand.validateInput(VALID_XML_PATH);

        assertThat(isValid).isTrue();
        verifyZeroInteractions(codeGenerator);
    }


    @Test
    public void testRun_notExistingXml_shouldNotTriggerTransformation() {
        codeGeneratorCommand.input = "fsdafdsafdsa.xml";
        codeGeneratorCommand.basePackage = "com.lala.ioio";
        codeGeneratorCommand.projectDir = "/tmp/myProject";

        codeGeneratorCommand.run();

        verifyZeroInteractions(codeGenerator);
    }


    @Test
    public void testRun_existingXmlExistingXslOutputWithParent_shouldTriggerTransformation() {
        codeGeneratorCommand.input = VALID_XML_PATH.toAbsolutePath().toString();
        codeGeneratorCommand.basePackage = "com.lala.ioio";
        codeGeneratorCommand.projectDir = "/tmp/myProject";
        codeGeneratorCommand.templateType = TemplateType.JPA_REST;

        codeGeneratorCommand.run();

        verify(codeGenerator).generateFrom(any(Path.class), any(Path.class), anyString(), any(TemplateType.class));
    }


}
