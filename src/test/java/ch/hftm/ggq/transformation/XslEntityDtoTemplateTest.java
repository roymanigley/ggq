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

public class XslEntityDtoTemplateTest {

    private XslTransformerService xslTransformerService;
    private Path xmlPath;

    @BeforeEach
    void beforeEach() throws URISyntaxException {
        xslTransformerService = new XslTransformerServiceImpl();
        xmlPath = Path.of(XslJavaFunctionTest.class.getClassLoader().getResource("xml/entity.xml").toURI());
    }

    @Test
    void testTransformationToDTO() throws IOException, URISyntaxException {
        final Path xslPath = Path.of(XslTransformerServiceTest.class.getClassLoader().getResource("xsl/code-gen-java/JPA_REST/entity/entityDto.xsl").toURI());
        final Path outputPath = Files.createTempFile("output", ".java");

        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() ->
                xslTransformerService.transform(xmlPath, xslPath, outputPath)
        );
        final String transformed = Files.readString(outputPath);
        assertThat(transformed).isEqualTo(EXPECTED_RESULT);

        Files.delete(outputPath);
    }

    static final String EXPECTED_RESULT =
            "package ch.example.service.dto;\n" +
            "\n" +
            "import com.fasterxml.jackson.annotation.JsonFormat;\n" +
            "\n" +
            "import javax.validation.constraints.NotNull;\n" +
            "import javax.json.bind.annotation.JsonbDateFormat;\n" +
            "import java.io.Serializable;\n" +
            "import java.time.LocalDate;\n" +
            "import java.io.Serializable;\n" +
            "\n" +
            "public class JustAnEntityDto implements Serializable {\n" +
            "\n" +
            "    private static final long serialVersionUID = 1L;\n" +
            "\n" +
            "    private Long id;\n" +
            "\n" +
            "    @NotNull\n" +
            "    private String lastName;\n" +
            "\n" +
            "    \n" +
            "    private String firstName;\n" +
            "\n" +
            "    \n" +
            "    private Integer counter;\n" +
            "\n" +
            "    @NotNull\n" +
            "    private Double price;\n" +
            "\n" +
            "    \n" +
            "    private Double rabatt;\n" +
            "\n" +
            "    @JsonbDateFormat(\"yyyy-MM-dd\") @JsonFormat(pattern = \"yyyy-MM-dd\")@NotNull\n" +
            "    private java.time.LocalDate date;\n" +
            "\n" +
            "    @JsonbDateFormat(\"yyyy-MM-dd'T'HH:mm:ss\") @JsonFormat(pattern = \"yyyy-MM-dd'T'HH:mm:ss\")@NotNull\n" +
            "    private java.time.LocalDateTime time;\n" +
            "\n" +
            "    @NotNull\n" +
            "    private Long genreId;\n" +
            "\n" +
            "    \n" +
            "    private Long typId;\n" +
            "\n" +
            "    @NotNull\n" +
            "    private Long customerId;\n" +
            "\n" +
            "    \n" +
            "    private Long userId;\n" +
            "\n" +
            "    \n" +
            "    public Long getId() {\n" +
            "        return id;\n" +
            "    }\n" +
            "\n" +
            "    public void setId(Long id) {\n" +
            "        this.id = id;\n" +
            "    }\n" +
            "\n" +
            "    public JustAnEntityDto id(Long id) {\n" +
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
            "    public JustAnEntityDto lastName(String lastName) {\n" +
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
            "    public JustAnEntityDto firstName(String firstName) {\n" +
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
            "    public JustAnEntityDto counter(Integer counter) {\n" +
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
            "    public JustAnEntityDto price(Double price) {\n" +
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
            "    public JustAnEntityDto rabatt(Double rabatt) {\n" +
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
            "    public JustAnEntityDto date(java.time.LocalDate date) {\n" +
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
            "    public JustAnEntityDto time(java.time.LocalDateTime time) {\n" +
            "        this.time = time;\n" +
            "        return this;\n" +
            "    }\n" +
            "    \n" +
            "    public Long getGenreId() {\n" +
            "        return genreId;\n" +
            "    }\n" +
            "\n" +
            "    public void setGenreId(Long genreId) {\n" +
            "        this.genreId = genreId;\n" +
            "    }\n" +
            "\n" +
            "    public JustAnEntityDto genreId(Long genreId) {\n" +
            "        this.genreId = genreId;\n" +
            "        return this;\n" +
            "    }\n" +
            "    \n" +
            "    public Long getTypId() {\n" +
            "        return typId;\n" +
            "    }\n" +
            "\n" +
            "    public void setTypId(Long typId) {\n" +
            "        this.typId = typId;\n" +
            "    }\n" +
            "\n" +
            "    public JustAnEntityDto typId(Long typId) {\n" +
            "        this.typId = typId;\n" +
            "        return this;\n" +
            "    }\n" +
            "    \n" +
            "    public Long getCustomerId() {\n" +
            "        return customerId;\n" +
            "    }\n" +
            "\n" +
            "    public void setCustomerId(Long customerId) {\n" +
            "        this.customerId = customerId;\n" +
            "    }\n" +
            "\n" +
            "    public JustAnEntityDto customerId(Long customerId) {\n" +
            "        this.customerId = customerId;\n" +
            "        return this;\n" +
            "    }\n" +
            "    \n" +
            "    public Long getUserId() {\n" +
            "        return userId;\n" +
            "    }\n" +
            "\n" +
            "    public void setUserId(Long userId) {\n" +
            "        this.userId = userId;\n" +
            "    }\n" +
            "\n" +
            "    public JustAnEntityDto userId(Long userId) {\n" +
            "        this.userId = userId;\n" +
            "        return this;\n" +
            "    }\n" +
            "    \n" +
            "\n" +
            "    @Override\n" +
            "    public String toString() {\n" +
            "        return \"JustAnEntityDto: {\" +\n" +
            "            \"id: \" + id +\n" +
            "            \", lastName: \" + lastName +\n" +
            "            \", firstName: \" + firstName +\n" +
            "            \", counter: \" + counter +\n" +
            "            \", price: \" + price +\n" +
            "            \", rabatt: \" + rabatt +\n" +
            "            \", date: \" + date +\n" +
            "            \", time: \" + time +\n" +
            "            \", genreId: \" + genreId +\n" +
            "            \", typId: \" + typId +\n" +
            "            \", customerId: \" + customerId +\n" +
            "            \", userId: \" + userId +\n" +
            "        \"}\";\n" +
            "    }\n" +
            "}\n";
}
