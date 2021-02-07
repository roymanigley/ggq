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

import static org.assertj.core.api.Assertions.assertThat;

public class XslGetterAndSetterTemplateTest {

    private XslTransformerService xslTransformerService;
    private Path xmlPath;

    @BeforeEach
    void beforeEach() throws URISyntaxException {
        xslTransformerService = new XslTransformerServiceImpl();
        xmlPath = Path.of(XslJavaFunctionTest.class.getClassLoader().getResource("xml/entity.xml").toURI());
    }

    @Test
    void testTransformationForGetterAndSetter() throws IOException, URISyntaxException {


        final Path xslPath = Path.of(XslTransformerServiceTest.class.getClassLoader().getResource("xsl/entities-generation-test.xsl").toURI());
        final Path outputPath = Files.createTempFile("output", ".txt");

        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() ->
                xslTransformerService.transform(xmlPath, xslPath, outputPath)
        );
        final String transformed = Files.readString(outputPath);
        assertThat(transformed).isEqualTo(EXPECTED_RESULT);

        Files.delete(outputPath);
    }

    static final String EXPECTED_RESULT =
            "\n" +
            "    public Long getId() {\n" +
            "        return id;\n" +
            "    }\n" +
            "\n" +
            "    public void setId(Long id) {\n" +
            "        this.id = id;\n" +
            "    }\n" +
            "\n" +
            "    public JustAnEntity id(Long id) {\n" +
            "        this.id = id;\n" +
            "        return this;\n" +
            "    }\n" +
            "    \n" +
            "    public String getLastName() {\n" +
            "        return lastName;\n" +
            "    }\n" +
            "\n" +
            "    public void setLastName(String lastName) {\n" +
            "        this.lastName = lastName;\n" +
            "    }\n" +
            "\n" +
            "    public JustAnEntity lastName(String lastName) {\n" +
            "        this.lastName = lastName;\n" +
            "        return this;\n" +
            "    }\n" +
            "    \n" +
            "    public String getFirstName() {\n" +
            "        return firstName;\n" +
            "    }\n" +
            "\n" +
            "    public void setFirstName(String firstName) {\n" +
            "        this.firstName = firstName;\n" +
            "    }\n" +
            "\n" +
            "    public JustAnEntity firstName(String firstName) {\n" +
            "        this.firstName = firstName;\n" +
            "        return this;\n" +
            "    }\n" +
            "    \n" +
            "    public Integer getCounter() {\n" +
            "        return counter;\n" +
            "    }\n" +
            "\n" +
            "    public void setCounter(Integer counter) {\n" +
            "        this.counter = counter;\n" +
            "    }\n" +
            "\n" +
            "    public JustAnEntity counter(Integer counter) {\n" +
            "        this.counter = counter;\n" +
            "        return this;\n" +
            "    }\n" +
            "    \n" +
            "    public Double getPrice() {\n" +
            "        return price;\n" +
            "    }\n" +
            "\n" +
            "    public void setPrice(Double price) {\n" +
            "        this.price = price;\n" +
            "    }\n" +
            "\n" +
            "    public JustAnEntity price(Double price) {\n" +
            "        this.price = price;\n" +
            "        return this;\n" +
            "    }\n" +
            "    \n" +
            "    public Double getRabatt() {\n" +
            "        return rabatt;\n" +
            "    }\n" +
            "\n" +
            "    public void setRabatt(Double rabatt) {\n" +
            "        this.rabatt = rabatt;\n" +
            "    }\n" +
            "\n" +
            "    public JustAnEntity rabatt(Double rabatt) {\n" +
            "        this.rabatt = rabatt;\n" +
            "        return this;\n" +
            "    }\n" +
            "    \n" +
            "    public java.time.LocalDate getDate() {\n" +
            "        return date;\n" +
            "    }\n" +
            "\n" +
            "    public void setDate(java.time.LocalDate date) {\n" +
            "        this.date = date;\n" +
            "    }\n" +
            "\n" +
            "    public JustAnEntity date(java.time.LocalDate date) {\n" +
            "        this.date = date;\n" +
            "        return this;\n" +
            "    }\n" +
            "    \n" +
            "    public java.time.LocalDateTime getTime() {\n" +
            "        return time;\n" +
            "    }\n" +
            "\n" +
            "    public void setTime(java.time.LocalDateTime time) {\n" +
            "        this.time = time;\n" +
            "    }\n" +
            "\n" +
            "    public JustAnEntity time(java.time.LocalDateTime time) {\n" +
            "        this.time = time;\n" +
            "        return this;\n" +
            "    }\n" +
            "    ";
}
