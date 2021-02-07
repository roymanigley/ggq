package ch.hftm.ggq.service;

import ch.hftm.ggq.model.ProjectModel;

import java.nio.file.Path;

public interface ProjectInitializer {
    ProjectModel initProject(Path projectDirPath, String basePackage);
}
