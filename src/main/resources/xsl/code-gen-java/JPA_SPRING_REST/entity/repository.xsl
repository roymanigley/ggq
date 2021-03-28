<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:ggq="xalan://ch.hftm.ggq.xsl.GGQ">
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>


<xsl:template match="/">package <xsl:value-of select="$BASE_PACKAGE" />.repository;

import <xsl:value-of select="concat($BASE_PACKAGE, '.domain.', entity/@name)" />;

import org.springframework.data.jpa.repository.JpaRepository;

public interface <xsl:value-of select="entity/@name" />Repository extends JpaRepository&lt;<xsl:value-of select="entity/@name" />, Long&gt; {

}
    </xsl:template>
</xsl:stylesheet>
