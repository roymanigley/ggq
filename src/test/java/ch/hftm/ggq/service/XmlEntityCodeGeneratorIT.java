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
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

public class XmlEntityCodeGeneratorIT {

    private TemplateRepository templateRepository;
    private XmlEntitiesModelLoaderImpl xmlEntitiesModelLoader;
    private QuarkusProjectInitializer quarkusProjectInitializer;
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
    void test() throws IOException, URISyntaxException {

        // BEFORE
        final Path projectDirPath = Files.createTempDirectory("DUMMY");
        final Path xmlPathEntities = Path.of(XmlEntityCodeGeneratorIT.class.getClassLoader().getResource("xml/code-gen-extended.xml").toURI());
        final String basePackage = "ch.example";
        final EntitiesModel entitiesModel = xmlEntitiesModelLoader.loadEntitiesModel(xmlPathEntities);
        final ProjectModel projectModel = quarkusProjectInitializer.initProject(projectDirPath, basePackage);

        // WHEN
        codeGenerator.generateFrom(xmlPathEntities, projectDirPath, basePackage, TemplateType.JPA_REST);

        // THEN
        templateRepository.allEntityTemplatesFor(TemplateType.JPA_REST, projectModel.getBasePackagePath()).forEach((path, template) -> {
            entitiesModel.getEntities().forEach(model -> {
                String pathToCheck = path.replace("{EntityName}", model.getName());
                final Path pathToGeneratedFile = Paths.get(projectModel.getProjectDir(), pathToCheck);
                assertThat(Files.exists(pathToGeneratedFile)).isTrue();
                System.out.println("[found]: " + pathToGeneratedFile.toString());
            });
        });

        templateRepository.allNonEntityTemplatesFor(TemplateType.JPA_REST, projectModel.getBasePackagePath()).forEach((path, template) -> {
            entitiesModel.getEntities().forEach(model -> {
                String pathToCheck = path.replace("{EntityName}", model.getName());
                final Path pathToGeneratedFile = Paths.get(projectModel.getProjectDir(), pathToCheck);
                System.out.println(pathToGeneratedFile.toString());
                assertThat(Files.exists(pathToGeneratedFile)).isTrue();
                System.out.println("[found]: " + pathToGeneratedFile.toString());
            });
        });
        checkIfCanBuildWithMaven(projectDirPath);

//        Files.walk(projectDirPath)
//                .sorted(Comparator.reverseOrder())
//                .map(Path::toFile)
//                .forEach(File::delete);
    }

    private void checkIfCanBuildWithMaven(Path projectDirPath) throws IOException {
        final Runtime runtime = Runtime.getRuntime();
        final String pathToMvnwExecutable = projectDirPath.resolve("mvnw").toAbsolutePath().toString();
        String[] mvnwBuildCommand;
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            mvnwBuildCommand = new String[]{"cmd", "/c", pathToMvnwExecutable + " -f " + projectDirPath.toAbsolutePath() + " clean test -Pintegration-tests"};
        } else {
            mvnwBuildCommand = new String[]{"sh", "-c", pathToMvnwExecutable + " -f " + projectDirPath.toAbsolutePath() + " clean test -Pintegration-tests"};
        }
        final Process exec = runtime.exec(mvnwBuildCommand);
        System.out.println("[+] Building generated code, this may takes a while ...");
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
