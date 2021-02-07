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

public class XslParameterTest {

    private XslTransformerService xslTransformerService;

    private Path xmlPath;
    private Path xslPath;

    @BeforeEach
    void beforeEach() throws URISyntaxException {
        xslTransformerService = new XslTransformerServiceImpl();
        xmlPath = Path.of(XslParameterTest.class.getClassLoader().getResource("xml/empty.xml").toURI());
        xslPath = Path.of(XslParameterTest.class.getClassLoader().getResource("xsl/parameters.xsl").toURI());
    }

    @Test
    void testTransformationWithEmptyParams() throws IOException, URISyntaxException {
        final String expectedResult =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "PARAM_01:\n" +
                        "PARAM_02:p2 default";

        final Path outputPath = Files.createTempFile("output", ".html");

        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() ->
                xslTransformerService.transform(xmlPath, xslPath, outputPath)
        );
        final String transformed = Files.readString(outputPath);
        assertThat(transformed.replaceAll("\\s*", "")).isEqualTo(expectedResult.replaceAll("\\s*", ""));

        Files.delete(outputPath);
    }

    @Test
    void testTransformationWithAllParams() throws IOException, URISyntaxException {
        final String PARAM_01_VALUE = "PARAM_235656+565+";
        final String PARAM_02_VALUE = "PARAM_uiroeuwiroeuwqr";
        final String expectedResult =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "PARAM_01:" + PARAM_01_VALUE + "\n" +
                        "PARAM_02:" + PARAM_02_VALUE;

        final Path outputPath = Files.createTempFile("output", ".html");

        final Map<String, String> params = Map.of(
                "PARAM_01", PARAM_01_VALUE,
                "PARAM_02", PARAM_02_VALUE
        );
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() ->
                xslTransformerService.transform(xmlPath, xslPath, outputPath, params)
        );

        final String transformed = Files.readString(outputPath);
        assertThat(transformed.replaceAll("\\s*", "")).isEqualTo(expectedResult.replaceAll("\\s*", ""));

        Files.delete(outputPath);
    }
}
