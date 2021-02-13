<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:ggq="xalan://ch.hftm.ggq.xsl.GGQ">
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
<xsl:template match="/">package <xsl:value-of select="$BASE_PACKAGE" />.repository;

import java.util.List;
import java.util.Optional;

import <xsl:value-of select="concat($BASE_PACKAGE, '.domain.', entity/@name)" />;

public interface <xsl:value-of select="entity/@name" />Repository {

    List&lt;<xsl:value-of select="entity/@name" />&gt; findAll();

    Optional&lt;<xsl:value-of select="entity/@name" />&gt; findById(Long id);

    <xsl:value-of select="entity/@name" /> save(<xsl:value-of select="concat(entity/@name, ' ')" /><xsl:value-of select="ggq:firstToLowerCase(entity/@name)"/>);

    void delete(Long id);
}
    </xsl:template>
</xsl:stylesheet>