<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:import href="helper.xsl" />
    <xsl:output method="text" omit-xml-declaration="yes" />
    <xsl:template match="/">
        <xsl:call-template name="getterAndSetter">
            <xsl:with-param name="name" select="'id'" />
            <xsl:with-param name="type" select="'Long'" />
            <xsl:with-param name="entityType" select="/entity/@name" />
        </xsl:call-template>
        <xsl:for-each select="entity/variables/variable">
            <xsl:call-template name="getterAndSetter">
                <xsl:with-param name="name" select="@name" />
                <xsl:with-param name="type" select="@type" />
                <xsl:with-param name="entityType" select="/entity/@name" />
            </xsl:call-template>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>