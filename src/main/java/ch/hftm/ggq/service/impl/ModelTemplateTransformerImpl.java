package ch.hftm.ggq.service.impl;

import ch.hftm.ggq.model.EntityModel;
import ch.hftm.ggq.model.ProjectModel;
import ch.hftm.ggq.service.ModelTemplateTransformer;
import ch.hftm.ggq.service.XmlEntitiesModelLoader;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Dependent
public class ModelTemplateTransformerImpl implements ModelTemplateTransformer {

    private static final String EMPTY_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root></root>";
    private final XmlEntitiesModelLoader xmlEntitiesModelLoader;

    @Inject
    public ModelTemplateTransformerImpl(XmlEntitiesModelLoader xmlEntitiesModelLoader) {
        this.xmlEntitiesModelLoader = xmlEntitiesModelLoader;
    }

    @Override
    public void transformEntity(EntityModel model, ProjectModel projectModel, Map<String, String> entityTemplates) {
        try {
            final String xml = xmlEntitiesModelLoader.entitiesModelToXml(model);
            System.out.println(xml);
            for (Map.Entry<String, String> entry : entityTemplates.entrySet()) {
                String path = entry.getKey();
                final String template = entry.getValue();
                path = path.replace("{EntityName}", model.getName());
                transformTemplate(projectModel, path, template, xml);
            }
        } catch (IOException | TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void transformNonEntity(ProjectModel projectModel, Map<String, String> nonEntityTemplates) {
        try {
            for (Map.Entry<String, String> entry : nonEntityTemplates.entrySet()) {
                final String path = entry.getKey();
                final String template = entry.getValue();
                transformTemplate(projectModel, path, template, EMPTY_XML);
            }
        } catch (IOException | TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    private void transformTemplate(ProjectModel projectModel, String path, String template, String s) throws IOException, TransformerException {
        final StringReader reader = new StringReader(s);
        Path outputPath = Paths.get(projectModel.getProjectDir(), path);
        Files.createDirectories(outputPath.getParent());
        if (Files.notExists(outputPath)) {
            outputPath = Files.createFile(outputPath);
        }

        try (final InputStream xslStream = XmlEntityCodeGeneratorImpl.class.getClassLoader().getResourceAsStream(template);) {
            final XslTransformerServiceImpl xslTransformerService = new XslTransformerServiceImpl();
            xslTransformerService.transform(reader, xslStream, outputPath, Map.of("BASE_PACKAGE", projectModel.getBasePackage(), "PROJECT_NAME", projectModel.getProjectName()));
        } catch (Exception e) {
            throw e;
        }
    }
}
