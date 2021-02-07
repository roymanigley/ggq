<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:param name="PARAM_01"/>
    <xsl:param name="PARAM_02" select="'p2 default'"/>
    <xsl:template match="/">
<xsl:if test="$PARAM_01=1">
...the expression is true...</xsl:if>
    </xsl:template>
</xsl:stylesheet>