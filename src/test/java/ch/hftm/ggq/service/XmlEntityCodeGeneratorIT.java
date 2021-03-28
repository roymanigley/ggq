package ch.hftm.ggq.service;

import ch.hftm.ggq.enumerations.TemplateType;
import ch.hftm.ggq.model.EntitiesModel;
import ch.hftm.ggq.repository.TemplateRepository;
import ch.hftm.ggq.repository.impl.TemplateRepositoryImpl;
import ch.hftm.ggq.service.impl.XmlEntitiesModelLoaderImpl;
import ch.hftm.ggq.service.impl.XmlEntityCodeGeneratorImpl;
import ch.hftm.ggq.model.ProjectModel;
import ch.hftm.ggq.service.impl.ModelTemplateTransformerImpl;
import ch.hftm.ggq.service.impl.QuarkusProjectInitializerImpl;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

public class XmlEntityCodeGeneratorIT {

    private TemplateRepository templateRepository;
    private XmlEntitiesModelLoaderImpl xmlEntitiesModelLoader;
    private ProjectInitializer quarkusProjectInitializer;
    private ModelTemplateTransformer modelTemplateTransformer;

    private XmlEntityCodeGenerator codeGenerator;

    @BeforeEach
    void beforeEach() {
        templateRepository = new TemplateRepositoryImpl();
        xmlEntitiesModelLoader = new XmlEntitiesModelLoaderImpl();
        xmlEntitiesModelLoader.init();
        quarkusProjectInitializer = new QuarkusProjectInitializerImpl();
        modelTemplateTransformer = new ModelTemplateTransformerImpl(xmlEntitiesModelLoader);

        codeGenerator = new XmlEntityCodeGeneratorImpl(templateRepository, xmlEntitiesModelLoader,
                quarkusProjectInitializer, modelTemplateTransformer);
    }

    @Test
    void testJPA_REST_Templates() throws IOException, URISyntaxException {
        final TemplateType templateType = TemplateType.JPA_REST;
        testTemplate(templateType, true, false);
    }

    @Test
    void testHIBERNATE_PANACHE_REST_Templates() throws IOException, URISyntaxException {
        final TemplateType templateType = TemplateType.HIBERNATE_PANACHE_REST;
        testTemplate(templateType, true, false);
    }

    @Test
    void testJPA_SPRING_REST_Templates() throws IOException, URISyntaxException {
        final TemplateType templateType = TemplateType.JPA_SPRING_REST;
        testTemplate(templateType, true, false);
    }

    @Test
    void testMONGO_PANACHE_REST_Templates() throws IOException, URISyntaxException {
        final TemplateType templateType = TemplateType.MONGO_PANACHE_REST;
        testTemplate(templateType, true, true);
    }

    @Test
    void testMONGO_PANACHE_REST_OIDC_Templates() throws IOException, URISyntaxException {
        final TemplateType templateType = TemplateType.MONGO_PANACHE_REST_OIDC;
        // IT skipped because it neads a running KeyCloak
        // @see https://github.com/quarkusio/quarkus/blob/main/integration-tests/oidc-code-flow/src/test/java/io/quarkus/it/keycloak/KeycloakRealmResourceManager.java
        testTemplate(templateType, true, true);
    }

    @Test
    void testHIBERNATE_PANACHE_REST_KAFKA_Templates() throws IOException, URISyntaxException {
        final TemplateType templateType = TemplateType.HIBERNATE_PANACHE_REST_KAFKA;
        testTemplate(templateType, true, true);
    }

    private void testTemplate(TemplateType templateType, boolean withCleanup, boolean skipIntegrationTests) throws IOException, URISyntaxException {
        // BEFORE
        final Path projectDirPath = Files.createTempDirectory(templateType.toString());
        final Path xmlPathEntities = Path.of(XmlEntityCodeGeneratorIT.class.getClassLoader().getResource("xml/example-extended.xml").toURI());
        final String basePackage = "ch.example";
        final EntitiesModel entitiesModel = xmlEntitiesModelLoader.loadEntitiesModel(xmlPathEntities);
        final ProjectModel projectModel = quarkusProjectInitializer.initProject(projectDirPath, basePackage);

        // WHEN
        codeGenerator.generateFrom(xmlPathEntities, projectDirPath, basePackage, templateType);

        // THEN
        templateRepository.allEntityTemplatesFor(templateType, projectModel.getBasePackagePath()).forEach((path, template) -> {
            entitiesModel.getEntities().forEach(model -> {
                String pathToCheck = path.replace("{EntityName}", model.getName());
                final Path pathToGeneratedFile = projectModel.getProjectDir().resolve(pathToCheck);
                assertThat(Files.exists(pathToGeneratedFile)).isTrue();
                System.out.println("[found]: " + pathToGeneratedFile.toString());
            });
        });

        templateRepository.allNonEntityTemplatesFor(templateType, projectModel.getBasePackagePath()).forEach((path, template) -> {
            final Path pathToGeneratedFile = projectModel.getProjectDir().resolve(path);
            System.out.println(pathToGeneratedFile.toString());
            assertThat(Files.exists(pathToGeneratedFile)).isTrue();
            System.out.println("[found]: " + pathToGeneratedFile.toString());
        });
        checkIfCanBuildWithMaven(projectDirPath, skipIntegrationTests);

        if (withCleanup) {
            Files.walk(projectDirPath)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }

    private void checkIfCanBuildWithMaven(Path projectDirPath, boolean skipIntegrationTests) throws IOException {
        final Runtime runtime = Runtime.getRuntime();
        final String pathToMvnwExecutable = projectDirPath.resolve("mvnw").toAbsolutePath().toString();
        String[] mvnwBuildCommand;
        final String mvnArgs = "clean test" + (skipIntegrationTests ? "" : " -Pintegration-tests");
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            mvnwBuildCommand = new String[]{"cmd", "/c", pathToMvnwExecutable + " -f " + projectDirPath.toAbsolutePath() + " " + mvnArgs};
        } else {
            mvnwBuildCommand = new String[]{"sh", "-c", pathToMvnwExecutable + " -f " + projectDirPath.toAbsolutePath() + " " + mvnArgs};
        }
        final Process exec = runtime.exec(mvnwBuildCommand);
        System.out.println("[+] Building generated code, this may take a while ...");
        try (final InputStream inputStream = exec.getInputStream();) {
            final List<String> strings = IOUtils.readLines(inputStream).stream()
                    .peek(System.out::println)
                    .filter(s -> s.startsWith("[INFO] BUILD FAILURE"))
                    .collect(Collectors.toList());
            if (!strings.isEmpty()) {
                fail("could not build generated code (maven build fail)");
            }
        } catch (IOException e) {
            e.printStackTrace();
            fail("falure during reading of inputstream of maven execution");
        }
    }
}
