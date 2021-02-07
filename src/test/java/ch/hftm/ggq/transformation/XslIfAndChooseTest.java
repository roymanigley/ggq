package ch.hftm.ggq.transformation;

import ch.hftm.ggq.service.XslTransformerService;
import ch.hftm.ggq.service.XslTransformerServiceTest;
import ch.hftm.ggq.service.impl.XslTransformerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class XslIfAndChooseTest {

    private XslTransformerService xslTransformerService;

    private Path xmlPath;

    @BeforeEach
    void beforeEach() throws URISyntaxException {
        xslTransformerService = new XslTransformerServiceImpl();
        xmlPath = Path.of(XslParameterTest.class.getClassLoader().getResource("xml/empty.xml").toURI());
    }

    @Test
    void testTransformationWithIfTrue() throws IOException, URISyntaxException {
        final String PARAM_01_VALUE = "1";
        final String expectedResult =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "...the expression is true...";

        final Path xslPath = Path.of(XslTransformerServiceTest.class.getClassLoader().getResource("xsl/parameters-if.xsl").toURI());
        final Path outputPath = Files.createTempFile("output", ".html");

        final Map<String, String> params = Map.of(
                "PARAM_01", PARAM_01_VALUE
        );
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() ->
                xslTransformerService.transform(xmlPath, xslPath, outputPath, params)
        );

        final String transformed = Files.readString(outputPath);
        assertThat(transformed).isEqualTo(expectedResult);
        Files.delete(outputPath);
    }

    @Test
    void testTransformationWithIfFalse() throws IOException, URISyntaxException {
        final String PARAM_01_VALUE = "2";
        final String expectedResult =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

        final Path xslPath = Path.of(XslTransformerServiceTest.class.getClassLoader().getResource("xsl/parameters-if.xsl").toURI());
        final Path outputPath = Files.createTempFile("output", ".html");

        final Map<String, String> params = Map.of(
                "PARAM_01", PARAM_01_VALUE
        );
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() ->
                xslTransformerService.transform(xmlPath, xslPath, outputPath, params)
        );

        final String transformed = Files.readString(outputPath);
        assertThat(transformed).isEqualTo(expectedResult);
        Files.delete(outputPath);
    }

    @Test
    void testTransformationWithChooseTestEqual() throws IOException, URISyntaxException {
        final String PARAM_01_VALUE = "1";
        final String expectedResult =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "... the value was 1 ...";

        final Path xslPath = Path.of(XslTransformerServiceTest.class.getClassLoader().getResource("xsl/parameters-choose.xsl").toURI());
        final Path outputPath = Files.createTempFile("output", ".html");

        final Map<String, String> params = Map.of(
                "PARAM_01", PARAM_01_VALUE
        );
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() ->
                xslTransformerService.transform(xmlPath, xslPath, outputPath, params)
        );

        final String transformed = Files.readString(outputPath);
        assertThat(transformed).isEqualTo(expectedResult);
        Files.delete(outputPath);
    }

    @Test
    void testTransformationWithChooseTestGreaterThen() throws IOException, URISyntaxException {
        final String PARAM_01_VALUE = "99";
        final String expectedResult =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "... the value was bigger then 2 ...";

        final Path xslPath = Path.of(XslTransformerServiceTest.class.getClassLoader().getResource("xsl/parameters-choose.xsl").toURI());
        final Path outputPath = Files.createTempFile("output", ".html");

        final Map<String, String> params = Map.of(
                "PARAM_01", PARAM_01_VALUE
        );
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() ->
                xslTransformerService.transform(xmlPath, xslPath, outputPath, params)
        );

        final String transformed = Files.readString(outputPath);
        assertThat(transformed).isEqualTo(expectedResult);
        Files.delete(outputPath);
    }

    @Test
    void testTransformationWithChooseOtherwise() throws IOException, URISyntaxException {
        final String PARAM_01_VALUE = "-9";
        final String expectedResult =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "... the value is not 1 and not bigget then 2 ....";

        final Path xslPath = Path.of(XslTransformerServiceTest.class.getClassLoader().getResource("xsl/parameters-choose.xsl").toURI());
        final Path outputPath = Files.createTempFile("output", ".html");

        final Map<String, String> params = Map.of(
                "PARAM_01", PARAM_01_VALUE
        );
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() ->
                xslTransformerService.transform(xmlPath, xslPath, outputPath, params)
        );

        final String transformed = Files.readString(outputPath);
        assertThat(transformed).isEqualTo(expectedResult);
        Files.delete(outputPath);
    }
}
