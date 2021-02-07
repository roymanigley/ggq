<xsl:stylesheet version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:xalan="http://xml.apache.org/xalan"
        xmlns:ggq="xalan://ch.hftm.ggq.xsl.GGQ">
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
<xsl:template match="/">package <xsl:value-of select="$BASE_PACKAGE" />.service.mapper;

import <xsl:value-of select="concat($BASE_PACKAGE, '.domain.*')" />;
import <xsl:value-of select="concat($BASE_PACKAGE, '.service.dto.', entity/@name, 'Dto')" />;

public class <xsl:value-of select="entity/@name" />Mapper {

    private <xsl:value-of select="entity/@name" />Mapper() {
    }

    public static <xsl:value-of select="entity/@name" />Dto toDto(<xsl:value-of select="entity/@name" /> entity) {
        return new <xsl:value-of select="entity/@name" />Dto()
            .id(entity.getId() != null ? entity.getId().toHexString() : null)
            <xsl:for-each select="entity/variables/variable">.<xsl:value-of select="@name" />(entity.get<xsl:value-of select="ggq:firstToUpperCase(@name)" />())
            </xsl:for-each><xsl:for-each select="entity/relations/relation">.<xsl:value-of select="@name" />Id(entity.get<xsl:value-of select="ggq:firstToUpperCase(@name)" />() != null &amp;&amp; entity.get<xsl:value-of select="ggq:firstToUpperCase(@name)" />().getId() != null ? entity.get<xsl:value-of select="ggq:firstToUpperCase(@name)" />().getId().toHexString() : null)
            </xsl:for-each>;
    }

    public static <xsl:value-of select="entity/@name" /> toEntity(<xsl:value-of select="entity/@name" />Dto dto) {
        return new <xsl:value-of select="entity/@name" />()
            .id(dto.getId())
            <xsl:for-each select="entity/variables/variable">.<xsl:value-of select="@name" />(dto.get<xsl:value-of select="ggq:firstToUpperCase(@name)" />())
            </xsl:for-each><xsl:for-each select="entity/relations/relation">.<xsl:value-of select="@name" />(dto.get<xsl:value-of select="ggq:firstToUpperCase(concat(@name, 'Id'))" />() != null ? new <xsl:value-of select="@type"/>().id(dto.get<xsl:value-of select="ggq:firstToUpperCase(concat(@name, 'Id'))" />()) : null)
            </xsl:for-each>;
    }



    public static void mergeEntities(<xsl:value-of select="entity/@name" /> managed, <xsl:value-of select="entity/@name" /> fromDto) {
        managed
            <xsl:for-each select="entity/variables/variable">.<xsl:value-of select="@name" />(fromDto.get<xsl:value-of select="ggq:firstToUpperCase(@name)" />())
            </xsl:for-each><xsl:for-each select="entity/relations/relation">.<xsl:value-of select="@name" />(fromDto.get<xsl:value-of select="ggq:firstToUpperCase(@name)" />())
            </xsl:for-each>;
    }
}
    </xsl:template>
</xsl:stylesheet>
