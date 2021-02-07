package ch.hftm.ggq.service.impl;

import ch.hftm.ggq.model.EntitiesModel;
import ch.hftm.ggq.model.EntityModel;
import ch.hftm.ggq.model.RelationsModel;
import ch.hftm.ggq.model.VariablesModel;
import ch.hftm.ggq.service.XmlEntitiesModelLoader;
import com.thoughtworks.xstream.XStream;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import java.nio.file.Path;

@Dependent
public class XmlEntitiesModelLoaderImpl implements XmlEntitiesModelLoader {

    private final XStream xStream = new XStream();

    @PostConstruct
    public void init() {
        xStream.processAnnotations(new Class[] {EntitiesModel.class});
        xStream.addImplicitCollection(EntitiesModel.class, "entities");
        xStream.addImplicitCollection(VariablesModel.class, "variables");
        xStream.addImplicitCollection(RelationsModel.class, "relations");
    }

    @Override
    public EntitiesModel loadEntitiesModel(Path xmlPath) {
        return (EntitiesModel) xStream.fromXML(xmlPath.toFile());
    }

    @Override
    public String entitiesModelToXml(EntityModel entityModel) {
        return xStream.toXML(entityModel);
    }
}
