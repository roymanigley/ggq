package ch.hftm.ggq.service.impl;

import ch.hftm.ggq.model.ProjectModel;
import ch.hftm.ggq.service.QuarkusProjectInitializer;

import javax.enterprise.context.Dependent;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Dependent
public class QuarkusProjectInitializerImpl implements QuarkusProjectInitializer {

    @Override
    public ProjectModel initProject(Path projectDirPath, String basePackage) {
        initMaven(projectDirPath);
        return ProjectModel.from(projectDirPath, basePackage);
    }

    private void initMaven(Path projectDirPath) {
        try (final InputStream mvnw = XmlEntityCodeGeneratorImpl.class.getClassLoader().getResourceAsStream("binary/code-gen-java/mvnw");
             final InputStream mvnWrapperJar = XmlEntityCodeGeneratorImpl.class.getClassLoader().getResourceAsStream("binary/code-gen-java/.mvn/wrapper/maven-wrapper.jar");
             final InputStream mvnWrapperProperties = XmlEntityCodeGeneratorImpl.class.getClassLoader().getResourceAsStream("binary/code-gen-java/.mvn/wrapper/maven-wrapper.properties");
             final InputStream mvnWrapperDownloader = XmlEntityCodeGeneratorImpl.class.getClassLoader().getResourceAsStream("binary/code-gen-java/.mvn/wrapper/MavenWrapperDownloader.java");) {
            Files.createDirectories(projectDirPath);
            final Path mvnwExecutablePath = projectDirPath.resolve("mvnw");
            Files.copy(mvnw, mvnwExecutablePath, StandardCopyOption.REPLACE_EXISTING);
            mvnwExecutablePath.toFile().setExecutable(Boolean.TRUE);
            final Path wrapperPath = projectDirPath.resolve(".mvn").resolve("wrapper");
            Files.createDirectories(wrapperPath);
            Files.copy(mvnWrapperJar, wrapperPath.resolve("maven-wrapper.jar"), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(mvnWrapperProperties, wrapperPath.resolve("maven-wrapper.properties"), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(mvnWrapperDownloader, wrapperPath.resolve("MavenWrapperDownloader.java"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
