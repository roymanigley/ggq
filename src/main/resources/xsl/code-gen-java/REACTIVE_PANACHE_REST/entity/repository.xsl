<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:ggq="xalan://ch.hftm.ggq.xsl.GGQ">
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>


<xsl:template match="/">package <xsl:value-of select="$BASE_PACKAGE" />.repository;

import java.util.List;
import io.quarkus.panache.common.Page;
import io.smallrye.mutiny.Uni;

import <xsl:value-of select="concat($BASE_PACKAGE, '.domain.', entity/@name)" />;

public interface <xsl:value-of select="entity/@name" />Repository {

    Uni&lt;List&lt;<xsl:value-of select="entity/@name" />&gt;&gt; findAll(Page page);

    Uni&lt;<xsl:value-of select="entity/@name" />&gt; findRecordById(Long id);

    Uni&lt;Void&gt; save(<xsl:value-of select="concat(entity/@name, ' ')" /><xsl:value-of select="ggq:firstToLowerCase(/entity/@name)"/>);

    Uni&lt;Boolean> delete(Long id);
}
    </xsl:template>
</xsl:stylesheet>
