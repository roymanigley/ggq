package ch.hftm.ggq.service;

import ch.hftm.ggq.enumerations.TemplateType;

import java.nio.file.Path;

public interface XmlEntityCodeGenerator {
    void generateFrom(Path xmlPath, Path projectDirPath, String basePackage, TemplateType templateType);
}
