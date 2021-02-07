<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:param name="PARAM_01"/>
    <xsl:param name="PARAM_02" select="'p2 default'"/>
    <xsl:template match="/">
    <xsl:choose>
        <xsl:when test="$PARAM_01=1">
... the value was 1 ...</xsl:when>
        <xsl:when test="$PARAM_01>2">
... the value was bigger then 2 ...</xsl:when>
        <xsl:otherwise>
... the value is not 1 and not bigget then 2 ....</xsl:otherwise>
    </xsl:choose>
    </xsl:template>
</xsl:stylesheet>