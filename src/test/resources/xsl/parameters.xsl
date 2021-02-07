<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:param name="PARAM_01"/>
    <xsl:param name="PARAM_02" select="'p2 default'"/>
    <xsl:template match="/">
PARAM_01:<xsl:value-of select="$PARAM_01"/>
PARAM_02:<xsl:value-of select="$PARAM_02"/>
    </xsl:template>
</xsl:stylesheet>