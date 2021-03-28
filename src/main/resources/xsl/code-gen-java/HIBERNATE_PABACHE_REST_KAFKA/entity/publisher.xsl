<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:ggq="xalan://ch.hftm.ggq.xsl.GGQ">
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>


<xsl:template match="/">package <xsl:value-of select="$BASE_PACKAGE" />.service.publisher;

import <xsl:value-of select="concat($BASE_PACKAGE, '.service.dto.', entity/@name, 'Event')" />;

public interface <xsl:value-of select="entity/@name" />Publisher {

    void publish(<xsl:value-of select="entity/@name" />Event event);
}
    </xsl:template>
</xsl:stylesheet>
