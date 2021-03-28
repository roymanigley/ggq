<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:str="xalan://java.lang.String">
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
<xsl:template match="/">package <xsl:value-of select="$BASE_PACKAGE" />.conf;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.MongoDBContainer;

import java.util.Collections;
import java.util.Map;

public class MongoTestResourceLifecycleManager implements QuarkusTestResourceLifecycleManager {

    private final MongoDBContainer mongoDbContainer = new MongoDBContainer("mongo:4.0.10");

    @Override
    public Map&lt;String, String&gt; start() {
        mongoDbContainer.start();
        return Collections.singletonMap("quarkus.mongodb.connection-string", "mongodb://" + mongoDbContainer.getContainerIpAddress() + ":" + mongoDbContainer.getFirstMappedPort());
    }

    @Override
    public void stop() {
        mongoDbContainer.close();
    }
}
    </xsl:template>
</xsl:stylesheet>
