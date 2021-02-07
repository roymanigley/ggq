package ch.hftm.ggq.model;

import java.nio.file.Path;

public class ProjectModel {

    private Path projectDir;
    private String projectName;
    private String basePackage = "ch.example";
    private String basePackagePath;

    public static ProjectModel from(Path projectDirPath, String basePackage) {
        final String projectDirString = projectDirPath.toAbsolutePath().toString();
        return new ProjectModel()
                .projectDir(projectDirPath)
                .basePackage(basePackage)
                .basePackagePath(basePackage.replace(".", "/"))
                .projectName(projectDirPath.toFile().getName().replaceAll("\\s", "-"));
    }

    public Path getProjectDir() {
        return projectDir;
    }

    public ProjectModel projectDir(Path projectDir) {
        this.projectDir = projectDir;
        return this;
    }

    public String getProjectName() {
        return projectName;
    }

    public ProjectModel projectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public ProjectModel basePackage(String basePackage) {
        this.basePackage = basePackage;
        return this;
    }

    public String getBasePackagePath() {
        return basePackagePath;
    }

    public ProjectModel basePackagePath(String basePackagePath) {
        this.basePackagePath = basePackagePath;
        return this;
    }

    @Override
    public String toString() {
        return "ProjectModel{" +
                "projectDir='" + projectDir + '\'' +
                ", projectName='" + projectName + '\'' +
                ", basePackage='" + basePackage + '\'' +
                ", basePackagePath='" + basePackagePath + '\'' +
                '}';
    }
}
