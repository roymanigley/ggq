package ch.hftm.ggq.service;

import ch.hftm.ggq.model.EntitiesModel;
import ch.hftm.ggq.model.EntityModel;

import java.nio.file.Path;

public interface XmlEntitiesModelLoader {
    EntitiesModel loadEntitiesModel(Path xmlPath);

    String entitiesModelToXml(EntityModel entityModel);
}
