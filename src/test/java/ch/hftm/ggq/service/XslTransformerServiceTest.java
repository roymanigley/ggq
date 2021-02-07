package ch.hftm.ggq.service;

import ch.hftm.ggq.service.impl.XslTransformerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

public class XslTransformerServiceTest {

    private static final Logger LOG = LoggerFactory.getLogger(XslTransformerServiceTest.class.getName());

    private XslTransformerService xslTransformerService;

    @BeforeEach
    void beforeEach() {
        initMocks(this);
        xslTransformerService = new XslTransformerServiceImpl();
    }

    @Test
    void testTransformationCdCatalog() throws IOException, URISyntaxException {
        final Path expectedResultPath = Path.of(XslTransformerServiceTest.class.getClassLoader().getResource("result/cd-catalog.html").toURI());
        final String expectedResult = Files.readString(expectedResultPath);

        final Path xmlPath = Path.of(XslTransformerServiceTest.class.getClassLoader().getResource("xml/cd-catalog.xml").toURI());
        final Path xslPath = Path.of(XslTransformerServiceTest.class.getClassLoader().getResource("xsl/cd-catalog.xsl").toURI());
        final Path outputPath = Files.createTempFile("output", ".html");

        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() ->
                xslTransformerService.transform(xmlPath, xslPath, outputPath)
        );
        final String transformed = Files.readString(outputPath);
        LOG.info(transformed);
        assertThat(transformed.replaceAll("\\s", "")).isEqualTo(expectedResult.replaceAll("\\s", ""));

        Files.delete(outputPath);
    }

    @Test
    void testTransformationCdCatalogAndApplyTemplates() throws IOException, URISyntaxException {
        final Path expectedResultPath = Path.of(XslTransformerServiceTest.class.getClassLoader().getResource("result/cd-catalog-apply-templates.html").toURI());
        final String expectedResult = Files.readString(expectedResultPath);

        final Path xmlPath = Path.of(XslTransformerServiceTest.class.getClassLoader().getResource("xml/cd-catalog.xml").toURI());
        final Path xslPath = Path.of(XslTransformerServiceTest.class.getClassLoader().getResource("xsl/cd-catalog-apply-templates.xsl").toURI());
        final Path outputPath = Files.createTempFile("output", ".html");

        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() ->
                xslTransformerService.transform(xmlPath, xslPath, outputPath)
        );
        final String transformed = Files.readString(outputPath);
        LOG.info(transformed);

        assertThat(transformed.replaceAll("\\s", "")).isEqualTo(expectedResult.replaceAll("\\s", ""));

        Files.delete(outputPath);
    }
}
