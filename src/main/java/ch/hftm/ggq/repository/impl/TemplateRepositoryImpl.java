package ch.hftm.ggq.repository.impl;

import ch.hftm.ggq.enumerations.TemplateType;
import ch.hftm.ggq.repository.TemplateRepository;

import javax.enterprise.context.Dependent;
import java.util.HashMap;
import java.util.Map;

@Dependent
public class TemplateRepositoryImpl implements TemplateRepository {

    public Map<String, String> allEntityTemplatesFor(TemplateType templateType, String basePackagePath) {
        final Map<String, String> entityTemplates = new HashMap<>();
        templateType.addEntityTemplates(basePackagePath, entityTemplates);
        return entityTemplates;
    }

    public Map<String, String> allNonEntityTemplatesFor(TemplateType templateType, String basePackagePath) {
        final Map<String, String> nonEntityTemplates = new HashMap<>();
        templateType.addNonEntityTemplates(basePackagePath, nonEntityTemplates);
        return nonEntityTemplates;
    }
}
