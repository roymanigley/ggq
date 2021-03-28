<xsl:stylesheet version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
<xsl:template match="/">package <xsl:value-of select="$BASE_PACKAGE" />.service.publisher.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import <xsl:value-of select="concat($BASE_PACKAGE, '.service.dto.', entity/@name, 'Event')" />;
import <xsl:value-of select="concat($BASE_PACKAGE, '.service.publisher.', entity/@name, 'Publisher')" />;

@ApplicationScoped
public class <xsl:value-of select="entity/@name" />PublisherImpl implements <xsl:value-of select="entity/@name" />Publisher {

    private static final Logger LOG = LoggerFactory.getLogger(<xsl:value-of select="entity/@name" />PublisherImpl.class);

    private final Emitter&lt;<xsl:value-of select="entity/@name" />Event&gt; emitter;

    @Inject
    public <xsl:value-of select="entity/@name" />PublisherImpl(@Channel("<xsl:value-of select="entity/@name" />-out") Emitter&lt;<xsl:value-of select="entity/@name" />Event&gt; emitter) {
        this.emitter = emitter;
    }

    @Override
    public void publish(<xsl:value-of select="entity/@name" />Event event) {
        LOG.info("publishing to <xsl:value-of select="entity/@name" />-out: {}", event);
        emitter.send(event);
    }
}
    </xsl:template>
</xsl:stylesheet>
