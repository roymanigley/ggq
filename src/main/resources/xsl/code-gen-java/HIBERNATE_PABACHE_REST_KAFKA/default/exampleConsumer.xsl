<xsl:stylesheet version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
<xsl:template match="/">package <xsl:value-of select="$BASE_PACKAGE" />.service.consumer;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

<xsl:for-each select="/entities/entity">
import <xsl:value-of select="concat($BASE_PACKAGE, '.service.dto.', @name, 'Event')" />;
</xsl:for-each>
@ApplicationScoped
public class ExampleConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(ExampleConsumer.class);

    <xsl:for-each select="/entities/entity">
    @Incoming("<xsl:value-of select="@name" />-in")
    public void receive(<xsl:value-of select="@name" />Event event) {
        LOG.info("message from <xsl:value-of select="@name" />-in: {}", event);
    }
    </xsl:for-each>
}
    </xsl:template>
</xsl:stylesheet>
