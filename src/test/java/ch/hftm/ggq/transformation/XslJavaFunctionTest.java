package ch.hftm.ggq.transformation;

import ch.hftm.ggq.service.XslTransformerService;
import ch.hftm.ggq.service.impl.XslTransformerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class XslJavaFunctionTest {

    private XslTransformerService xslTransformerService;
    private static final String PARAM_01_KEY = "PARAM_01";
    private Path xmlPath;
    private Path xslPath;

    @BeforeEach
    void beforeEach() throws URISyntaxException {
        xslTransformerService = new XslTransformerServiceImpl();
        xmlPath = Path.of(XslJavaFunctionTest.class.getClassLoader().getResource("xml/empty.xml").toURI());
        xslPath = Path.of(XslJavaFunctionTest.class.getClassLoader().getResource("xsl/java-functions-test.xsl").toURI());
    }

    @Test
    void testTransformationWithJavaFunction_allLowerCase() throws IOException, URISyntaxException {
        final String expectedResult =
                "ggq:firstToUpperCase=Abcdefg\n" +
                "ggq:firstToLowerCase=abcdefg\n" +
                "ggq:toUpperCase=ABCDEFG\n" +
                "ggq:toLowerCase=abcdefg";

        final Path outputPath = Files.createTempFile("output", ".html");

        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() ->
                xslTransformerService.transform(xmlPath, xslPath, outputPath, Map.of(PARAM_01_KEY, "abcdefg"))
        );

        final String transformed = Files.readString(outputPath);
        assertThat(transformed.replaceAll("\\s*", "")).isEqualTo(expectedResult.replaceAll("\\s*", ""));

        Files.delete(outputPath);
    }

    @Test
    void testTransformationWithJavaFunction_allUpperCase() throws IOException, URISyntaxException {
        final String expectedResult =
                "ggq:firstToUpperCase=ABCDEFG\n" +
                "ggq:firstToLowerCase=aBCDEFG\n" +
                "ggq:toUpperCase=ABCDEFG\n" +
                "ggq:toLowerCase=abcdefg";

        final Path outputPath = Files.createTempFile("output", ".html");

        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() ->
                xslTransformerService.transform(xmlPath, xslPath, outputPath, Map.of(PARAM_01_KEY, "ABCDEFG"))
        );

        final String transformed = Files.readString(outputPath);
        assertThat(transformed.replaceAll("\\s*", "")).isEqualTo(expectedResult.replaceAll("\\s*", ""));

        Files.delete(outputPath);
    }

    @Test
    void testTransformationWithJavaFunction_mixedWithSpecialChars() throws IOException, URISyntaxException {
        final String expectedResult =
                "ggq:firstToUpperCase=A484)/%/&ç%hjk\n" +
                "ggq:firstToLowerCase=a484)/%/&ç%hjk\n" +
                "ggq:toUpperCase=A484)/%/&Ç%HJK\n" +
                "ggq:toLowerCase=a484)/%/&ç%hjk";

        final Path outputPath = Files.createTempFile("output", ".html");

        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() ->
                xslTransformerService.transform(xmlPath, xslPath, outputPath, Map.of(PARAM_01_KEY, "a484)/%/&ç%hjk"))
        );

        final String transformed = Files.readString(outputPath);
        assertThat(transformed.replaceAll("\\s*", "")).isEqualTo(expectedResult.replaceAll("\\s*", ""));

        Files.delete(outputPath);
    }
}
