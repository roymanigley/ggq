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

public class XslEntityTemplateTest {

    private XslTransformerService xslTransformerService;
    private Path xmlPath;

    @BeforeEach
    void beforeEach() throws URISyntaxException {
        xslTransformerService = new XslTransformerServiceImpl();
        xmlPath = Path.of(XslJavaFunctionTest.class.getClassLoader().getResource("xml/entity.xml").toURI());
    }

    @Test
    void testTransformationToEntity() throws IOException, URISyntaxException {
        final Path xslPath = Path.of(XslTransformerServiceTest.class.getClassLoader().getResource("xsl/code-gen-java/JPA_REST/entity/entity.xsl").toURI());
        final Path outputPath = Files.createTempFile("output", ".java");

        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() ->
                xslTransformerService.transform(xmlPath, xslPath, outputPath)
        );
        final String transformed = Files.readString(outputPath);
        assertThat(transformed).isEqualTo(EXPECTED_RESULT);

        Files.delete(outputPath);
    }

    static final String EXPECTED_RESULT =
            "package ch.example.domain;\n" +
            "\n" +
            "import java.util.*;\n" +
            "import javax.persistence.*;\n" +
            "import javax.validation.constraints.*;\n" +
            "import java.io.Serializable;\n" +
            "\n" +
            "@Entity\n" +
            "public class JustAnEntity implements Serializable {\n" +
            "\n" +
            "    private static final long serialVersionUID = 1L;\n" +
            "\n" +
            "    @Id\n" +
            "    @GeneratedValue(strategy = GenerationType.AUTO)\n" +
            "    private Long id;\n" +
            "    \n" +
            "    @Column(nullable=false) @NotNull\n" +
            "    private String lastName;\n" +
            "    \n" +
            "    @Column(nullable=true) \n" +
            "    private String firstName;\n" +
            "    \n" +
            "    @Column(nullable=true) \n" +
            "    private Integer counter;\n" +
            "    \n" +
            "    @Column(nullable=false) @NotNull\n" +
            "    private Double price;\n" +
            "    \n" +
            "    @Column(nullable=true) \n" +
            "    private Double rabatt;\n" +
            "    \n" +
            "    @Column(nullable=false, columnDefinition = \"DATE\") @NotNull\n" +
            "    private java.time.LocalDate date;\n" +
            "    \n" +
            "    @Column(nullable=false, columnDefinition = \"TIMESTAMP\") @NotNull\n" +
            "    private java.time.LocalDateTime time;\n" +
            "    \n" +
            "    @ManyToOne(optional=false) @NotNull\n" +
            "    private Genre genre;\n" +
            "    \n" +
            "    @ManyToOne(optional=true) \n" +
            "    private Genre typ;\n" +
            "    \n" +
            "    @OneToOne(optional=false) @NotNull\n" +
            "    private Customer customer;\n" +
            "    \n" +
            "    @OneToOne(optional=true) \n" +
            "    private User user;\n" +
            "    \n" +
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
            "    \n" +
            "    public Genre getGenre() {\n" +
            "        return genre;\n" +
            "    }\n" +
            "\n" +
            "    public void setGenre(Genre genre) {\n" +
            "        this.genre = genre;\n" +
            "    }\n" +
            "\n" +
            "    public JustAnEntity genre(Genre genre) {\n" +
            "        this.genre = genre;\n" +
            "        return this;\n" +
            "    }\n" +
            "    \n" +
            "    public Genre getTyp() {\n" +
            "        return typ;\n" +
            "    }\n" +
            "\n" +
            "    public void setTyp(Genre typ) {\n" +
            "        this.typ = typ;\n" +
            "    }\n" +
            "\n" +
            "    public JustAnEntity typ(Genre typ) {\n" +
            "        this.typ = typ;\n" +
            "        return this;\n" +
            "    }\n" +
            "    \n" +
            "    public Customer getCustomer() {\n" +
            "        return customer;\n" +
            "    }\n" +
            "\n" +
            "    public void setCustomer(Customer customer) {\n" +
            "        this.customer = customer;\n" +
            "    }\n" +
            "\n" +
            "    public JustAnEntity customer(Customer customer) {\n" +
            "        this.customer = customer;\n" +
            "        return this;\n" +
            "    }\n" +
            "    \n" +
            "    public User getUser() {\n" +
            "        return user;\n" +
            "    }\n" +
            "\n" +
            "    public void setUser(User user) {\n" +
            "        this.user = user;\n" +
            "    }\n" +
            "\n" +
            "    public JustAnEntity user(User user) {\n" +
            "        this.user = user;\n" +
            "        return this;\n" +
            "    }\n" +
            "    \n" +
            "    @Override\n" +
            "    public boolean equals(Object o) {\n" +
            "        if (this == o) {\n" +
            "            return true;\n" +
            "        }\n" +
            "        if (!(o instanceof JustAnEntity)) {\n" +
            "            return false;\n" +
            "        }\n" +
            "        return id != null && id.equals(((JustAnEntity) o).id);\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public int hashCode() {\n" +
            "        return Objects.hash(JustAnEntity.class, id);\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public String toString() {\n" +
            "        return \"JustAnEntity: {\" +\n" +
            "            \"id: \" + id +\n" +
            "            \", lastName: \" + lastName +\n" +
            "            \", firstName: \" + firstName +\n" +
            "            \", counter: \" + counter +\n" +
            "            \", price: \" + price +\n" +
            "            \", rabatt: \" + rabatt +\n" +
            "            \", date: \" + date +\n" +
            "            \", time: \" + time +\n" +
            "            \", genre: \" + genre +\n" +
            "            \", typ: \" + typ +\n" +
            "            \", customer: \" + customer +\n" +
            "            \", user: \" + user +\n" +
            "        \"}\";\n" +
            "    }\n" +
            "}\n";
}
