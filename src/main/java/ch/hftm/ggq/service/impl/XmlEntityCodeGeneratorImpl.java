package ch.hftm.ggq.service.impl;

import ch.hftm.ggq.enumerations.TemplateType;
import ch.hftm.ggq.model.EntitiesModel;
import ch.hftm.ggq.service.ProjectInitializer;
import ch.hftm.ggq.service.XmlEntitiesModelLoader;
import ch.hftm.ggq.model.ProjectModel;
import ch.hftm.ggq.repository.TemplateRepository;
import ch.hftm.ggq.service.ModelTemplateTransformer;
import ch.hftm.ggq.service.XmlEntityCodeGenerator;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.nio.file.Path;
import java.util.Map;

@Dependent
public class XmlEntityCodeGeneratorImpl implements XmlEntityCodeGenerator {

    private final TemplateRepository templateRepository;
    private final XmlEntitiesModelLoader xmlEntitiesModelLoader;
    private final ProjectInitializer projectInitializer;
    private final ModelTemplateTransformer modelTemplateTransformer;

    @Inject
    public XmlEntityCodeGeneratorImpl(TemplateRepository templateRepository,
                                      XmlEntitiesModelLoader xmlEntitiesModelLoader,
                                      ProjectInitializer projectInitializer,
                                      ModelTemplateTransformer modelTemplateTransformer) {
        this.templateRepository = templateRepository;
        this.xmlEntitiesModelLoader = xmlEntitiesModelLoader;
        this.projectInitializer = projectInitializer;
        this.modelTemplateTransformer = modelTemplateTransformer;
    }

    @Override
    public void generateFrom(Path xmlPath, Path projectDirPath, String basePackage, TemplateType templateType) {
        final ProjectModel projectModel = projectInitializer.initProject(projectDirPath, basePackage);
        final EntitiesModel entities = xmlEntitiesModelLoader.loadEntitiesModel(xmlPath);
        final Map<String, String> entityTemplates = templateRepository.allEntityTemplatesFor(templateType, projectModel.getBasePackagePath());
        final Map<String, String> defaultTemplates = templateRepository.allNonEntityTemplatesFor(templateType, projectModel.getBasePackagePath());

        entities.getEntities().stream()
                .peek(e -> modelTemplateTransformer.transformEntity(e, projectModel, entityTemplates))
                .forEach(System.out::println);

        modelTemplateTransformer.transformNonEntity(projectModel, defaultTemplates, xmlPath);
    }

}
