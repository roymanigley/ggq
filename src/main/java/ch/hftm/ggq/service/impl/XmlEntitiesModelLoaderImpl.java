package ch.hftm.ggq.service.impl;

import ch.hftm.ggq.model.*;
import ch.hftm.ggq.service.XmlEntitiesModelLoader;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import java.nio.file.Path;

@Dependent
public class XmlEntitiesModelLoaderImpl implements XmlEntitiesModelLoader {

    private final XStream xStream = new XStream();

    @PostConstruct
    public void init() {

        xStream.addPermission(NoTypePermission.NONE);
        xStream.addPermission(NoTypePermission.NONE);
        xStream.addPermission(NullPermission.NULL);
        xStream.addPermission(PrimitiveTypePermission.PRIMITIVES);
        xStream.allowTypes(new Class[] { EntitiesModel.class, EntityModel.class, VariablesModel.class, VariableModel.class, RelationsModel.class, RelationModel.class });
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
    public String entityModelToXml(EntityModel entityModel) {
        return xStream.toXML(entityModel);
    }

    @Override
    public String entitiesModelToXml(EntitiesModel entitiesModel) {
        return xStream.toXML(entitiesModel);
    }
}
