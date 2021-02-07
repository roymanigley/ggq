package ch.hftm.ggq.repository;

import ch.hftm.ggq.enumerations.TemplateType;

import java.util.Map;

public interface TemplateRepository {

    Map<String, String> allEntityTemplatesFor(TemplateType templateType, String basePackagePath);

    Map<String, String> allNonEntityTemplatesFor(TemplateType templateType, String basePackagePath);
}
