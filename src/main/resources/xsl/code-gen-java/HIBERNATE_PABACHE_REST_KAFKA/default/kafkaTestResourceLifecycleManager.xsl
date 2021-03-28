<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:str="xalan://java.lang.String">
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
<xsl:template match="/">package <xsl:value-of select="$BASE_PACKAGE" />.conf;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.KafkaContainer;

import java.util.Collections;
import java.util.Map;

public class KafkaTestResourceLifecycleManager implements QuarkusTestResourceLifecycleManager {

    private final KafkaContainer kafkaContainer = new KafkaContainer();

    @Override
    public Map&lt;String, String&gt; start() {
        kafkaContainer.start();
        return Collections.singletonMap("kafka.bootstrap.servers", kafkaContainer.getBootstrapServers());
    }

    @Override
    public void stop() {
       kafkaContainer.close();
    }
}
    </xsl:template>
</xsl:stylesheet>
