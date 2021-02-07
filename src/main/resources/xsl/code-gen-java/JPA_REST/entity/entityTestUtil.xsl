<xsl:stylesheet version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:xalan="http://xml.apache.org/xalan"
        xmlns:str="xalan://java.lang.String">
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
<xsl:template match="/">package <xsl:value-of select="$BASE_PACKAGE" />.util;

import javax.persistence.EntityManager;

import <xsl:value-of select="concat($BASE_PACKAGE, '.domain.*')" />;
import <xsl:value-of select="concat($BASE_PACKAGE, '.service.dto.', entity/@name, 'Dto')" />;
import <xsl:value-of select="concat($BASE_PACKAGE, '.service.mapper.', entity/@name, 'Mapper')" />;
public class <xsl:value-of select="entity/@name" />TestUtil {

    public static <xsl:value-of select="entity/@name" /> createRandomRecord(EntityManager em) {
        <xsl:value-of select="entity/@name" /> entity = new <xsl:value-of select="entity/@name" />()
            <xsl:for-each select="entity/variables/variable[@required = 'true']">.<xsl:value-of select="@name" />(RandomGenerator.getRandom<xsl:value-of select="str:replaceAll(
    str:new(@type),
    '.+\.([a-zA-Z0-9_]+)',
    '$1')"/>())</xsl:for-each>;
        <xsl:if test="entity/relations/relation">
        if (em != null) {
            addRequiredRelations(entity, em);
        }</xsl:if>
        return entity;
    }
    <xsl:if test="entity/relations/relation">
    public static void addRequiredRelations(<xsl:value-of select="entity/@name" /> entity, EntityManager em) {
    <xsl:for-each select="entity/relations/relation[@required = 'true']">
        entity.<xsl:value-of select="@name" />(<xsl:choose>
            <xsl:when test="@mapping = 'OneToOne'" >em.merge(<xsl:value-of select="@type" />TestUtil.createRandomRecord(em))</xsl:when>
            <xsl:otherwise>em.createQuery("from <xsl:value-of select="@type" />", <xsl:value-of select="@type" />.class).setMaxResults(1).getResultList().stream()
                .findAny()
                .orElseGet(() -> em.merge(<xsl:value-of select="@type" />TestUtil.createRandomRecord(em)))</xsl:otherwise>
        </xsl:choose>

        );
        em.flush();
    </xsl:for-each>
    }</xsl:if>

    public static <xsl:value-of select="entity/@name" />Dto createRandomDto(EntityManager em) {
        return <xsl:value-of select="entity/@name" />Mapper.toDto(createRandomRecord(em));
    }
}
    </xsl:template>
</xsl:stylesheet>