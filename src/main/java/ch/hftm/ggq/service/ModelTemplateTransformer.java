package ch.hftm.ggq.service;

import ch.hftm.ggq.model.EntityModel;
import ch.hftm.ggq.model.ProjectModel;

import java.nio.file.Path;
import java.util.Map;

public interface ModelTemplateTransformer {
    void transformEntity(EntityModel model, ProjectModel projectModel, Map<String, String> entityTemplates);

    void transformNonEntity(ProjectModel projectModel, Map<String, String> nonEntityTemplates, Path xmlPath);
}
