<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:ggq="xalan://ch.hftm.ggq.xsl.GGQ">
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>


<xsl:template match="/">package <xsl:value-of select="$BASE_PACKAGE" />.service;

import java.util.List;
import java.util.Optional;
import io.quarkus.panache.common.Page;
import io.smallrye.mutiny.Uni;

import <xsl:value-of select="concat($BASE_PACKAGE, '.service.dto.', entity/@name, 'Dto')" />;

public interface <xsl:value-of select="entity/@name" />Service {

    Uni&lt;List&lt;<xsl:value-of select="entity/@name" />Dto&gt;&gt; findAll(Page page);

    Uni&lt;Optional&lt;<xsl:value-of select="entity/@name" />Dto&gt;&gt; findById(Long id);

    Uni&lt;<xsl:value-of select="entity/@name" />Dto&gt; save(<xsl:value-of select="entity/@name" />Dto <xsl:value-of select="ggq:firstToLowerCase(/entity/@name)"/>);

    Uni&lt;Boolean&gt; delete(Long id);
}
    </xsl:template>
</xsl:stylesheet>
